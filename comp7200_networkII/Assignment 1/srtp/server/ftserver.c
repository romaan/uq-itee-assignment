/*FTServer.c: Implements the FTP server functionality. By accepting the command, parsing it and start listening to the user defined port. Also responsible to read the file data and save it on disk*/
#include"r_server.c"

/*Will be used to check if file exist before accepting the file*/
int file_exist(char *filename)
{
  struct stat buffer;   
  return (stat (filename, &buffer) == 0);
}

/*API: called to read the content of the file*/
short ft_read(unsigned char *recv_buf, unsigned char *send_buf, size_t recv_size, size_t *send_size) 
{
	*send_size = 0;
	FILE *f = fopen(FT_SERVER.filename,"ab");
	fwrite(recv_buf, recv_size,  1, f );
	fclose(f);
	 return 0;
}

/*API : called by r_accept when a new connection is detected*/
short ft_accept(struct FTP_PACKET_TYPE ftp_packet, struct FTP_PACKET_TYPE *ftp_send)
{
int i;
	memset(FT_SERVER.filename, 0, sizeof(FT_SERVER.filename));
	for (i = 0; i < ntohl(ftp_packet.buffer_size) && i < sizeof(FT_SERVER.filename); i++)
		FT_SERVER.filename[i] = ftp_packet.buffer[i];
	FT_SERVER.filename[i] = '\0';

	if (file_exist((char *)ftp_packet.buffer) == TRUE) {
		ftp_send->packet_type = htonl(0);
		return FALSE;
	}
	ftp_send->packet_type = htonl(FT_OK);
	return TRUE;	

}

/*Parse the command line, prompt if any error, else initialize the FT_SERVER */
void parse_command_line_input(int isDebug, unsigned int port) 
{
	//Debug flag
	if (isDebug == TRUE)
		ISDEBUG = TRUE;	
	//Port Number
	if (port < 1024 || port > 65535) {
		fprintf(stderr, "PERMITTED PORT NUMBERS RANGE : 1024 - 65535 \n");
		exit(1);
	}
	else
		FT_SERVER.port = port;
	
	if (ISDEBUG)
		fprintf(stdout, "FT_SERVER initailized \n");
}

/* Accept any incoming connection, create a new child*/
int execute_listen_and_create_response_stream()
{
	struct R_SERVER_TYPE *r_server = r_initialize(&ft_read, &ft_accept, FT_SERVER.port);
	while (TRUE) 
	{
	r_server->parent->ack_number = 0;
	r_listen(r_server);
	}
	free(r_server);
}

/*API reponsible to connect start FTP server*/
int ftserver(int isDebug, unsigned int port) {

	parse_command_line_input(isDebug, port);
	execute_listen_and_create_response_stream();	
	return 0;	
}

int main(int argc, char *argv[]) 
{
int isDebug = FALSE;
         if (argc != 3 && argc != 2) {
                 fprintf(stderr,"ftserver [-d] <port> \n");
                 return -1;
         }
         else {
                 if (argc == 3 && strcmp(argv[1],"-d") == 0)
                         isDebug = TRUE;
                 else if (argc == 3) {
                         fprintf(stderr,"ftserver [-d] <port> \n");
                         return -1;
                 }
 
                 /*Call FTServer API */
                 if (argc == 2) {
                         return ftserver(isDebug, atoi(argv[1]));
                 }
                 else {
                         return ftserver(isDebug, atoi(argv[2]));
                 }
         }
         return 0;
}
