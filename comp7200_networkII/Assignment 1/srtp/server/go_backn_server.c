/* GO_BACKN_SERVER.C: Implements the go_back_n protocol on server side. When ever there is an incoming data, the data is received and packed into GO_BACK_Packet, the bits are converted from
network to host order. The received sequence number is checked to see if it has received the expected seq number. 

If received the server ack back to client and then passes the buffer which represents the R layer packet to call back function. The call back function is registered at the time of initializing the go_backn_server. The call back function is basically a R layer which then deals with the data . 

Else the packet is not passed to data but simply acked back to the client*/

#include "srtp.h"

/* Debug function: Prints the GO PACKET received or sending ones */
void print_packet(struct GO_PACKET_TYPE *t, char *msg) 
{
printf("\n%s -> ",msg);
printf(" Seq Num: %" PRIu32 ,ntohl(t->seq_number));
printf(" CRC Num: %" PRIu32 ,ntohl(t->crc));
printf(" Buf Size: %" PRIu32 "\n",ntohl(t->buffer_size));
}

/* Function called by R layer for initial connection only*/
void receive_go_backn_receiver(struct GO_BACKN_RECEIVER_TYPE *go_receiver)
{
	int result;
	fd_set readset;
	do {
   	FD_ZERO(&readset);
	FD_SET(go_receiver->socketId, &readset);
	result = select(go_receiver->socketId + 1, &readset, NULL, NULL, 0);
	} while (result == -1 && errno == EINTR);
	if (result > 0 && FD_ISSET(go_receiver->socketId, &readset))
	{
	//Receive from client
	struct GO_PACKET_TYPE *go_packet = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	struct GO_PACKET_TYPE *go_packet_ack = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	unsigned char *buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	size_t size = sizeof(go_receiver->clientAddr);	
	recvfrom(go_receiver->socketId, buf, sizeof(struct GO_PACKET_TYPE) , 0 , (struct sockaddr *)&(go_receiver->clientAddr), (socklen_t *) &size );
	memcpy(go_packet, buf, sizeof(struct GO_PACKET_TYPE));
	if (ISDEBUG)
		print_packet(go_packet, "RECEIVED PACKED" );
	if (( crc32b(go_packet->buffer) +  crc32(go_packet->buffer_size) + crc32(go_packet->seq_number)) == ntohl(go_packet->crc) && ntohl(go_packet->seq_number)  == (go_receiver->ack_number + 1))	
	{
		if (ISDEBUG)
			printf("Accepted PACKET\n");
		go_receiver->ack_number = (go_packet->seq_number) == MAX_SEQ ? 0 : go_receiver->ack_number + 1;
		go_packet->buffer_size = ntohl(go_packet->buffer_size);
		go_receiver->callback(go_packet->buffer, go_packet_ack, (go_packet->buffer_size) , go_receiver );
	}
	else
	{
		if (ISDEBUG)
			printf("REJECTED PACKET\n");
	}
		
	free(buf);
	free(go_packet);
	free(go_packet_ack);
	}

	
}


//Receive 2nd, 3rd and so on packets requested by R layer and send ACK
void child_go_backn_receiver(struct GO_BACKN_RECEIVER_TYPE *go_receiver)
{
	int result;
	fd_set readset;
	do {
   	FD_ZERO(&readset);
	FD_SET(go_receiver->child_socketId, &readset);
	result = select(go_receiver->child_socketId + 1, &readset, NULL, NULL, 0);
	} while (result == -1 && errno == EINTR);
	if (result > 0 && FD_ISSET(go_receiver->child_socketId, &readset))
	{
	//Receive from client
	struct GO_PACKET_TYPE *go_packet = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	struct GO_PACKET_TYPE *go_packet_ack = (struct GO_PACKET_TYPE *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	unsigned char *buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	unsigned char *ack_buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	size_t size = sizeof(go_receiver->childClient);	
	recvfrom(go_receiver->child_socketId, buf, sizeof(struct GO_PACKET_TYPE) , 0 , (struct sockaddr *)&(go_receiver->childClient), (socklen_t *)&size );
	memcpy(go_packet, buf, sizeof(struct GO_PACKET_TYPE));
	if (ISDEBUG)
		print_packet(go_packet, "RECEIVED PACKET" );
	if (( crc32b(go_packet->buffer) +  crc32(go_packet->buffer_size) + crc32(go_packet->seq_number)) == ntohl(go_packet->crc) && ntohl(go_packet->seq_number)  == (go_receiver->ack_number + 1))	
	{
		if (ISDEBUG)
			printf("Accepted PACKET\n");
		go_receiver->ack_number = (go_packet->seq_number) == MAX_SEQ ? 0 : go_receiver->ack_number + 1;
		go_packet->buffer_size = ntohl(go_packet->buffer_size);
		go_receiver->callback(go_packet->buffer, go_packet_ack, (go_packet->buffer_size) , go_receiver );
	}
	else if (crc32b(go_packet->buffer) +  crc32(go_packet->buffer_size) + crc32(go_packet->seq_number) != ntohl(go_packet->crc)) 
	{
		if (ISDEBUG)
			printf("CORRUPT PACKET DISCARDED NO ACK\n");
		return;
	}
	else
	{
		if (ISDEBUG)
			printf("REJECTED PACKET BUT ACK SENT\n");
	}
	go_packet_ack->buffer_size = htonl(sizeof(go_packet_ack->buffer));
	go_packet_ack->seq_number = go_packet->seq_number;
	go_packet_ack->buffer_size = go_packet_ack->buffer_size;
	go_packet_ack->crc = htonl(crc32b(go_packet_ack->buffer) + crc32(go_packet_ack->buffer_size) + crc32(go_packet_ack->seq_number)); 
	memcpy(ack_buf, go_packet_ack, sizeof(struct GO_PACKET_TYPE));
	if (ISDEBUG)
		print_packet(go_packet_ack, "SENDING ACK PACKET");
	sendto(go_receiver->child_socketId, ack_buf, sizeof(struct GO_PACKET_TYPE), 0, (struct sockaddr *)&(go_receiver->childClient), sizeof(go_receiver->childClient));
		if (SET_EXIT)
		{
			close(go_receiver->child_socketId);
			free(go_receiver);
			exit(0);
		}
	free(buf);
	free(go_packet);
	free(go_packet_ack);
	}	
}

//initialize go backn child receiver
unsigned int init_go_backn_child(struct GO_BACKN_RECEIVER_TYPE* go_receiver)
{
	go_receiver->child_socketId = socket(AF_INET, SOCK_DGRAM, 0);
	memset(&(go_receiver->childServer),0,sizeof(go_receiver->childServer));
	memset(&(go_receiver->childClient),0,sizeof(go_receiver->childClient));

	go_receiver->child_socketId = socket(AF_INET,SOCK_DGRAM,0);  
	if (go_receiver->child_socketId == -1 ) {  
        	fprintf(stderr, "Could open the socket");
		exit(1);  
	}

	(go_receiver->childServer).sin_family = AF_INET;
	(go_receiver->childServer).sin_port = 0;
	(go_receiver->childServer).sin_addr.s_addr = htonl(INADDR_ANY);

	//Associate process with port
	int port_bind_status = bind(go_receiver->child_socketId, (struct sockaddr *)&(go_receiver->childServer), sizeof(go_receiver->childServer));
	if (port_bind_status < 0)
	{
	printf("Cannot bind port number: %d",port_bind_status);
	exit(1);
	}
	/* Now bound, get the address */
	size_t slen = sizeof(go_receiver->childServer);
	if (getsockname(go_receiver->child_socketId, (struct sockaddr *)&(go_receiver->childServer),(socklen_t *) &slen) == -1)
	{
	fprintf(stderr,"Error getting socketname");
	exit(1);
	}
	
	unsigned int rand_port = ntohs((go_receiver->childServer).sin_port);
	if (ISDEBUG)
		printf("\nNewly bound socket:%u", rand_port);
	return 0;
}

//initialize go backn receiver
struct GO_BACKN_RECEIVER_TYPE* init_go_backn_receiver(short (*cback)(unsigned char *recv_buf, struct GO_PACKET_TYPE *send,  size_t recv_size, struct GO_BACKN_RECEIVER_TYPE *go_receiver), int serverPort) 
{
	struct GO_BACKN_RECEIVER_TYPE* go_receiver = (struct GO_BACKN_RECEIVER_TYPE*) calloc(1, sizeof(struct GO_BACKN_RECEIVER_TYPE));
	int port_bind_status;
	//Register callback
	go_receiver->callback = cback;
	go_receiver->socketId = socket(AF_INET, SOCK_DGRAM, 0);
	memset(&(go_receiver->serverAddr),0,sizeof(go_receiver->serverAddr));
	memset(&(go_receiver->clientAddr),0,sizeof(go_receiver->clientAddr));

	go_receiver->socketId = socket(AF_INET,SOCK_DGRAM,0);  
	if (go_receiver->socketId == -1 ) {  
        	fprintf(stderr, "Could open the socket");
		exit(1);  
	}

	(go_receiver->serverAddr).sin_family = AF_INET;
	(go_receiver->serverAddr).sin_port = htons(serverPort);
	(go_receiver->serverAddr).sin_addr.s_addr = htonl(INADDR_ANY);

	//Associate process with port
	port_bind_status = bind(go_receiver->socketId, (struct sockaddr *)&(go_receiver->serverAddr), sizeof(go_receiver->serverAddr));
	if (port_bind_status < 0)
	{
	printf("Cannot bind port number ");
	exit(1);
	}
	return go_receiver;

}

//Send a packet with ftp data with new port
void go_backn_send(struct GO_BACKN_RECEIVER_TYPE *go_receiver, struct GO_PACKET_TYPE *send)
{
	unsigned char *ack_buf = (unsigned char *) calloc(1, sizeof(struct GO_PACKET_TYPE));
	send->seq_number =  htonl(1);
	send->buffer_size = htonl(sizeof(send->buffer));
	memcpy(ack_buf, send, sizeof(*send));
	struct GO_PACKET_TYPE t1;
	memcpy(&t1, send, sizeof(t1));
	if (ISDEBUG)
		print_packet(&t1, "First ACK with port number");
	sendto(go_receiver->socketId, ack_buf, sizeof(struct GO_PACKET_TYPE), 0, (struct sockaddr *)&(go_receiver->clientAddr), sizeof(go_receiver->clientAddr));
	free(ack_buf);
	
}
