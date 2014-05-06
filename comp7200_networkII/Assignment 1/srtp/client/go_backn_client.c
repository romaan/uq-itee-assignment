#include "srtp.h"
/*GO_BACKN_CLIENT: Responsible for implementing the go_backn protocol on client's side.
Basically receives data from R layers, runs two thread one to receive any incoming go_packets and other to transmit.
The thread responsible for transmitting:
* prepares the data by inserting the data into circular queue data structure and moves the Right pointer (also 
increases queue length variable value) that acts as sliding window. 
* then tranverse the window and transmit available packages. 
* then it waits for ACK to be received by other thread.
* moves the left pointer of the circular queue removing the acked packets.
and process continues till the given buffer is empty*/

/* Print the sliding window (circular queue) state */
void print_status(struct GO_BACKN_SENDER_TYPE *p, char *msg)
{ 
	if (ISDEBUG) {
	int i;
	printf("\nSender: %s",msg);
	printf("\nLeft Ptr: %d", p->left_ptr);
	printf("\tRight Ptr: %d", p->right_ptr);
	printf("\tBuffer Count: %d", p->buffer_count);
	printf("\nInx_number:");
	for (i = 0 ; i < MAX_WINDOW; i++)
		printf("%d ",i);
	printf("\nSeq_number:");
	for (i = 0 ; i < MAX_WINDOW; i++)
		printf("%ld ", p->seq_number[i]);
	printf("\nAck_number:");
	for (i = 0 ; i < MAX_WINDOW; i++)
		printf("%ld ", p->ack_number[i]);
	printf("\nSeg Number:");
	for (i = 0 ; i < MAX_WINDOW; i++)
		printf("%ld ", p->segment_number[i]);
	printf("\nSeg Size  :");
	for (i = 0 ; i < MAX_WINDOW; i++)
		printf("%ld ", p->segment_size[i]);
	printf("\n");
	}
}

/* Print the packets transmitted or received packet*/
void print_transmit(struct GO_PACKET_TYPE *t, char *msg) 
{
	if (ISDEBUG)
	{
		printf("\n%s -> ",msg);
		printf(" Seq/Ack Num: %" PRIu32 ,ntohl(t->seq_number));
		printf(" CRC Num: %" PRIu32 ,ntohl(t->crc));
		printf(" Buf Size: %" PRIu32 ,ntohl(t->buffer_size));
	}
}

/* Delivers the received control packet messages to upper layer i.e. R layer*/
size_t *go_receive(struct GO_BACKN_SENDER_TYPE* go_backn_sender, unsigned char *buf) 
{
	memcpy(buf, go_backn_sender->received_buffer, sizeof(struct GO_PACKET_TYPE));
	return &go_backn_sender->received_buffer_size;
}

/* Receiver Thread listens for incoming packets and marks the packet as received in sliding window */
void *ack_receiver_start(void *param) 
{
	int result;
	fd_set readset;
	struct timeval tv;
	// Initialize time out struct
	tv.tv_sec = 0;
	tv.tv_usec = TIMEOUT;
	struct GO_BACKN_SENDER_TYPE *go_backn_sender = (struct GO_BACKN_SENDER_TYPE *) param;
	while(go_backn_sender->receiver_run == TRUE)
	{

	 do {
   	FD_ZERO(&readset);
	FD_SET(go_backn_sender->socketId, &readset);
	result = select(go_backn_sender->socketId + 1, &readset, NULL, NULL, &tv);
	} while (result == -1 && errno == EINTR);
	
	if (result > 0 && FD_ISSET(go_backn_sender->socketId, &readset))
	{
	struct GO_PACKET_TYPE *go_packet = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	unsigned char *buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));	
	size_t size = sizeof(go_backn_sender->serverAddr);
	result = recvfrom(go_backn_sender->socketId, buf, sizeof(struct GO_PACKET_TYPE), 0 , (struct sockaddr *)&(go_backn_sender->serverAddr),(socklen_t *) &size );
	if (result == 0) {
		fprintf(stderr, "\nServer side of the socket is closed\n");
		exit(1);
	}
	memcpy(go_packet, buf, sizeof(struct GO_PACKET_TYPE));
	print_transmit(go_packet, "Receiver Reading ");
	int i;
	for (i = 0; i < MAX_WINDOW; i++)
	if (go_backn_sender->seq_number[i] == ntohl(go_packet->seq_number))
	{
		go_backn_sender->ack_number[i] = ntohl(go_packet->seq_number);
		go_backn_sender->segment_number[i] = 0;
		go_backn_sender->segment_size[i] = 0;
		memcpy(go_backn_sender->received_buffer, go_packet->buffer, sizeof(go_backn_sender->received_buffer));
		go_backn_sender->received_buffer_size =  ntohl(go_packet->buffer_size);
	}
	free(go_packet);
	free(buf);
	}
	else if (result < 0)
		fprintf(stderr, "\nError on select()\n");
	}
	return NULL;
}

//Wait for timeout or successful ack of left most packet
void wait_for_timeout_or_successful_ack(struct GO_BACKN_SENDER_TYPE* go_backn_sender) 
{ 
  	time_t now;
  	time(&now);
	int flag = FALSE;
	while (time(NULL) - now < TIMEOUT) {

		if (go_backn_sender->ack_number[(go_backn_sender->left_ptr) % MAX_WINDOW] == go_backn_sender->seq_number[(go_backn_sender->left_ptr) % MAX_WINDOW]) {
			go_backn_sender->retry = 0;
			flag = TRUE;
	     }
	}
	if (ISDEBUG)
		printf("\nWAIT FOR TIMEOUT OR ACK: RETRY %d\n",go_backn_sender->retry);
	if (flag == TRUE)
		return;

	if (go_backn_sender->retry >= MAX_RETRY)
	{
		fprintf(stderr, "Maximum retries, could not connected to server\n");
		exit(0);
	}
	go_backn_sender->retry++;
	printf("\nNot received or timed out\n");
}

//Determine the block size
long getBlockSize(size_t start_ptr, size_t buffer_size)
{
	if ((start_ptr + sizeof(struct R_PACKET_TYPE)) < buffer_size)
		return sizeof(struct R_PACKET_TYPE);
	return  buffer_size - start_ptr;
}

//Get the data into sliding window by inserting into circular queue and increase the queue length
void prepare_transmit(struct GO_BACKN_SENDER_TYPE* go_backn_sender, size_t *start_ptr, unsigned char *buffer, size_t buffer_size) 
{
	print_status(go_backn_sender, "BEFORE PREPARE_TRANSMIT");
	while(go_backn_sender->seq_number[go_backn_sender->right_ptr] == go_backn_sender->ack_number[go_backn_sender->right_ptr] && (*start_ptr) < buffer_size && go_backn_sender->buffer_count < MAX_WINDOW)
	{
		go_backn_sender->seq_number[go_backn_sender->right_ptr] = get_next_seq();
		go_backn_sender->segment_number[go_backn_sender->right_ptr] = (*start_ptr) / MAX_BUFFER;
		go_backn_sender->segment_size[go_backn_sender->right_ptr] = getBlockSize(*start_ptr, buffer_size);
		(*start_ptr) += sizeof(struct R_PACKET_TYPE);		
		go_backn_sender->right_ptr = (go_backn_sender->right_ptr + 1) % MAX_WINDOW;
		go_backn_sender->buffer_count++;
	}

	print_status(go_backn_sender, "AFTER PREPARE_TRANSMIT");
}

//Transmit available data from sliding window
void transmit(struct GO_BACKN_SENDER_TYPE* go_backn_sender, unsigned char *buffer) 
{
int i;
struct GO_PACKET_TYPE *go_packet;
unsigned char *buf;
int count = 0;
	for (i = go_backn_sender->left_ptr; count < go_backn_sender->buffer_count; i = (i + 1) % MAX_WINDOW)
	{
		go_packet = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
		buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));
		go_packet->seq_number = htonl(go_backn_sender->seq_number[i]);
		go_packet->buffer_size = htonl(go_backn_sender->segment_size[i]);
		memcpy(go_packet->buffer, buffer + (go_backn_sender->segment_number[i] * MAX_BUFFER),  go_backn_sender->segment_size[i]);
				
		go_packet->crc = htonl(crc32b(go_packet->buffer) + crc32(go_packet->buffer_size) + crc32(go_packet->seq_number));
	
		print_transmit(go_packet,"Sender: TRANSMIT");
		
		memcpy(buf, go_packet, sizeof(struct GO_PACKET_TYPE));
		
		int sendStatus = sendto((int)go_backn_sender->socketId, buf, sizeof(struct GO_PACKET_TYPE) , 0, (struct sockaddr *)&go_backn_sender->serverAddr, sizeof(struct sockaddr_in));
		if (sendStatus < 0) {
			printf("\nCould not send message\n");
			exit(0);
		}
		count++;
		free(buf);
		free(go_packet);
	}
}

//Remove the acked packets and move the left pointer of circular queue and decrease the queue length
void move_window_ptr(struct GO_BACKN_SENDER_TYPE* go_backn_sender) 
{
	print_status(go_backn_sender,"BEFORE LEFT MOVE");
	while(go_backn_sender->seq_number[go_backn_sender->left_ptr] != 0 && go_backn_sender->seq_number[go_backn_sender->left_ptr] == go_backn_sender->ack_number[go_backn_sender->left_ptr] && go_backn_sender->buffer_count > 0)
	{
		go_backn_sender->left_ptr = (go_backn_sender->left_ptr + 1) % MAX_WINDOW;
		go_backn_sender->buffer_count--;
	}
	print_status(go_backn_sender,"AFTER LEFT MOVE");
}

//Check if data is available in buffer given by R_layer or if any data is remaining in sliding window to transmit
int finished(struct GO_BACKN_SENDER_TYPE* go_backn_sender)
{
	if (go_backn_sender->seq_number[go_backn_sender->left_ptr] == go_backn_sender->ack_number[go_backn_sender->left_ptr] && go_backn_sender->buffer_count == 0)
	{
		return TRUE;
	}
return FALSE;
}

/* MAIN METHOD that accepts data from above layer */
void go_sender(struct GO_BACKN_SENDER_TYPE* go_backn_sender, unsigned char *buffer, size_t buffer_size)
{
	size_t start_ptr = 0;
	pthread_t receiver;
	pthread_create(&receiver,NULL,ack_receiver_start,go_backn_sender);
	go_backn_sender->receiver_run = TRUE;
	while ((start_ptr < buffer_size) || !finished(go_backn_sender)) 
	{
		//Get the data into sliding window by inserting into circular queue and increase the queue length
		prepare_transmit(go_backn_sender, &start_ptr, buffer, buffer_size);
		//Transmit available data from sliding window
		transmit(go_backn_sender, buffer);
		//Wait for timeout or successful ack of left most packet
		wait_for_timeout_or_successful_ack(go_backn_sender);
		//Remove the acked packets and move the left pointer of circular queue and decrease the queue length
		move_window_ptr(go_backn_sender);
	}
		
	//Signal receiver thread to stop
	go_backn_sender->receiver_run = FALSE;
	pthread_join(receiver,NULL);
	
}

/* Initialize the go_back in layer functionality. All the communication takes place at this layer*/
struct GO_BACKN_SENDER_TYPE* go_connect(char *host_name, int port)
{

	struct GO_BACKN_SENDER_TYPE* go_backn_sender = (struct GO_BACKN_SENDER_TYPE*) calloc(1, sizeof(struct GO_BACKN_SENDER_TYPE));
	go_backn_sender->host = gethostbyname(host_name);
	if (go_backn_sender->host == NULL)
		{
		fprintf(stderr, "\n%s: unknown host\n", host_name);
		exit(1);
		}	
	(go_backn_sender->serverAddr).sin_family = go_backn_sender->host->h_addrtype;
	memcpy((char*)&(go_backn_sender->serverAddr.sin_addr.s_addr),go_backn_sender->host->h_addr_list[0], go_backn_sender->host->h_length);
	(go_backn_sender->serverAddr).sin_port = htons(port);

	go_backn_sender->socketId = socket(AF_INET, SOCK_DGRAM, 0);
	if (go_backn_sender->socketId < 0) 
	{
		printf("cannot open socket\n");
		exit(1);
	}
	go_backn_sender->left_ptr = 0;
	go_backn_sender->right_ptr = 0;
	go_backn_sender->receiver_run = TRUE;
	return go_backn_sender;
}

