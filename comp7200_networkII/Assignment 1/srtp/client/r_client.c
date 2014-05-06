/*R_CLIENT : Maintains the state of the connection. The CONNECT is a two way handshake on simplex communication. Once the server replies R_OK, the R layer reads the 
file data and calls the go_sender to send the data*/
#include "go_backn_client.c"

struct R_CLIENT_TYPE *rp_connect(char *hostname, int port, struct FTP_PACKET_TYPE *connect_packet) 
{
	struct R_CLIENT_TYPE *r_client = (struct R_CLIENT_TYPE *) calloc(1, sizeof(struct R_CLIENT_TYPE));
	struct R_PACKET_TYPE r_send_connect;
	r_send_connect.packet_type = htonl((u_int32_t) RT_CONNECT);
	r_send_connect.ftp_packet = *connect_packet;
	r_send_connect.buffer_size = htonl(sizeof(*connect_packet));
	unsigned char send_buffer[sizeof(struct R_PACKET_TYPE)];
	unsigned char receive_buffer[sizeof(struct R_PACKET_TYPE)];
	memcpy(send_buffer, &r_send_connect, sizeof(r_send_connect));
	r_client->go_sender = go_connect(hostname,port);
	go_sender(r_client->go_sender, send_buffer, sizeof(r_send_connect));
	go_receive(r_client->go_sender, receive_buffer);
	struct R_PACKET_TYPE r_received;
	memcpy(&r_received, receive_buffer, sizeof(struct R_PACKET_TYPE));

	if (ISDEBUG)	
		printf("\nConnected to new port: %u", ntohl(r_received.ftp_packet.buffer_size));

	if (RT_OK == ntohl(r_received.packet_type) && FT_OK == ntohl(r_received.ftp_packet.packet_type) ) {
		
		 r_client->go_sender = go_connect(hostname, (int)ntohl( r_received.ftp_packet.buffer_size));
		
		return r_client;
	}
	else
	{
		printf("\nConnection could not be established as the file may exist or server is not ready to accept the connection\n");
		exit(1);
	}
	return r_client;
}

/* Send data to server's r_receive */
int rp_send(struct FT_CLIENT_TYPE ftclient)
{
	unsigned char *buffer = (unsigned char *) calloc(3000, sizeof(unsigned char *));
	int file = open(ftclient.filename,O_RDONLY);
	int bytes;

	while ((bytes = read(file, buffer, 3000)) != 0) {
		go_sender(ftclient.r_client->go_sender, buffer, bytes);
	}
	return 0;
}

/* Close the connection */
int rp_close(struct FT_CLIENT_TYPE ftclient)
{
	struct R_PACKET_TYPE r_packet_close;
	r_packet_close.packet_type = htonl((u_int32_t) RT_FIN);
	r_packet_close.buffer_size = htonl(sizeof(struct FTP_PACKET_TYPE));
	unsigned char *r_buffer = (unsigned char *) calloc(1, sizeof(struct R_PACKET_TYPE));
	
	/*Marking the end of connection*/
	memcpy(r_buffer, &r_packet_close, sizeof(struct R_PACKET_TYPE));
	go_sender(ftclient.r_client->go_sender, r_buffer, sizeof(struct R_PACKET_TYPE));
	return 0;	
}
