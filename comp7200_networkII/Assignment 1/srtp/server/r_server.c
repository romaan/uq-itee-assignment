/*R_SERVER.C: Responsbile for maintaining the state and providing concurrency */
#include "go_backn_server.c"

/* Close any existing connection */
int r_close(struct GO_BACKN_RECEIVER_TYPE *go_receiver)
{
	if (ISDEBUG)
		printf("\nFIN packet received\n");
	SET_EXIT = TRUE;
	return 0;
}

/* Forward declaration of ft_read */
short ft_read(unsigned char *recv_buf, unsigned char *send_buf, size_t recv_size, size_t *send_size);

/* Receive any file data and pass to ft_read api to save it to the file */
short r_receive(unsigned char *recv_buf, struct GO_PACKET_TYPE *send,  size_t recv_size, struct GO_BACKN_RECEIVER_TYPE *go_receiver)
{
	
	struct R_PACKET_TYPE *r_ack = (struct R_PACKET_TYPE *) calloc(1, sizeof(struct R_PACKET_TYPE));
	struct R_PACKET_TYPE *r_packet_recv= (struct R_PACKET_TYPE *) calloc(1, sizeof(struct R_PACKET_TYPE));
	memcpy(r_packet_recv, recv_buf, recv_size);
	
	if (ntohl(r_packet_recv->packet_type) == RT_FIN)
	{
		return r_close(go_receiver);
	}


	ft_read(recv_buf, (r_ack->ftp_packet).buffer, recv_size, (size_t *) &(r_ack->ftp_packet).buffer_size);

	r_ack->packet_type = RT_OK;
	r_ack->buffer_size = sizeof(struct FTP_PACKET_TYPE);
	r_ack->ftp_packet.packet_type = FT_OK;
	memcpy(send->buffer, r_ack, sizeof(struct R_PACKET_TYPE));
	return 0;


}

/*API: Listen for any incoming connections: Implement concurrency by invoking method from go_back n that binds to a random port and sends the reply to the client*/
short r_accept(unsigned char *recv_buf, struct GO_PACKET_TYPE *send,  size_t recv_size, struct GO_BACKN_RECEIVER_TYPE *go_receiver)
{
	struct R_PACKET_TYPE *r_packet_recv= (struct R_PACKET_TYPE *) calloc(1, sizeof(struct R_PACKET_TYPE));
	struct R_PACKET_TYPE *r_packet_send= (struct R_PACKET_TYPE *) calloc(1, sizeof(struct R_PACKET_TYPE));
	memcpy(r_packet_recv, recv_buf, recv_size);
	go_receiver->r_server->cbaccept(r_packet_recv->ftp_packet, &(r_packet_send->ftp_packet));
	if (ntohl(r_packet_recv->packet_type) == RT_CONNECT) {
		if (fork()==0)
		{
			init_go_backn_child(go_receiver);
			r_packet_send->packet_type = htonl(RT_OK);	
			r_packet_send->ftp_packet.buffer_size = htonl(ntohs((go_receiver->childServer).sin_port));
			r_packet_send->buffer_size = htonl(sizeof(r_packet_send->ftp_packet));				
			memcpy(send->buffer, r_packet_send, sizeof(struct R_PACKET_TYPE));	
			go_backn_send(go_receiver, send);
			close(go_receiver->socketId);
			go_receiver->callback = &r_receive;
			do 
			{
				child_go_backn_receiver(go_receiver);
			} while (TRUE);
		}
	}
return 0;
}

/* Initailize reliable server */
struct R_SERVER_TYPE* r_initialize(short (*cfback)(unsigned char *recv_buf, unsigned char *send_buf, size_t recv_size, size_t *send_size), short (*cbaccept)(struct FTP_PACKET_TYPE, struct FTP_PACKET_TYPE *), int port)
{
	struct R_SERVER_TYPE *r_server = (struct R_SERVER_TYPE *) calloc(1, sizeof(struct R_SERVER_TYPE));
	r_server->parent = init_go_backn_receiver(&r_accept,port);
	r_server->cfback = cfback;
	r_server->cbaccept = cbaccept;
	r_server->parent->r_server = r_server;
	return r_server;
}

/* Accept any incoming connections */
int r_listen(struct R_SERVER_TYPE *r_server)
{
	receive_go_backn_receiver(r_server->parent);
	return 0;
}
