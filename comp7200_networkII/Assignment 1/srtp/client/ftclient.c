/*FTCLIENT.C : Implements the necessary methods at the FTP layer. The main function is to
accept the commands, parse and store it in the FT_CLIENT_TYPE structure and execute transfer by invoking the  commad by invoking the R layer r_connect, r_send and r_close API */

#include"r_client.c"

/*The function checks whether the file exists on its file path. 
Returns true if file is in the directory, otherwise returns false.
Arguments:
@ char *filename - name of the file*/
int file_exist(char *filename)
{
  struct stat buffer;   
  return (stat (filename, &buffer) == 0);
}

/*The function parses the command line and initializes the FT_CLIENT if no error has occured. 
Otherwise prompts with the error detected.
Arguments:
@ int isDebug - flag if the application is running in the debug mode, possible values are 0 or 1
@ unsigned int port - port number
@ char filename[MAX_STRING] - name of the file
*/
void parse_command(int isDebug, char server[MAX_STRING], unsigned int port, char filename[MAX_STRING]) {
	//if we are in the debug mode, set the debug flag to TRUE globally
	if (isDebug == TRUE)
		ISDEBUG = TRUE;	
	//checking if the port number is within the permitted range
	if (port < 1024 || port > 65535) {
		fprintf(stderr, "PERMITTED PORT NUMBERS RANGE : 1024 - 65535 \n");
		exit(1);
	}
	else
		FT_CLIENT.port = port;
	//if file exists, before starting the transmission
	if (file_exist(filename) ) 
		strcpy(FT_CLIENT.filename, filename);
	else {
		fprintf(stderr, "File %s could not be accessed \n",filename);
		exit(1);
	}
	strcpy(FT_CLIENT.server, server);
	if (ISDEBUG)
		fprintf(stdout, "FT_CLIENT initailized \n");
}

/* execute_transfer: The function initiates a connection, transfers the file and terminates the connection*/
int execute_transfer()
{
	//create an ftp packet
	struct FTP_PACKET_TYPE *ftp_connect_packet = (struct FTP_PACKET_TYPE *) calloc(1, sizeof(struct FTP_PACKET_TYPE));
	//initialise the packet with type and size values
	ftp_connect_packet->packet_type = htonl(FT_NAME);
	memcpy(ftp_connect_packet->buffer, FT_CLIENT.filename, sizeof(FT_CLIENT.filename));
	ftp_connect_packet->buffer_size = htonl(sizeof(FT_CLIENT.filename));

	//connect to the server
	FT_CLIENT.r_client = rp_connect(FT_CLIENT.server, FT_CLIENT.port, ftp_connect_packet);
	rp_send(FT_CLIENT);
	rp_close(FT_CLIENT);
	printf("\nFILE TRANSFER COMPLETED\n");
	free(ftp_connect_packet);
	return 0;
}

/*The method restricts the maximum length of string arguments.
Arguments:
@ char *argServer - the server address 
@ char *argFilename - the file name*/
int check_length(char *argServer, char *argFilename) 
{
	if(strlen(argServer) >= MAX_STRING || strlen(argFilename) >= MAX_STRING)
	{
	fprintf(stderr,"Maximum argument length: %d\n", MAX_STRING);
	exit(1);
	}
return 0;
}

/*API reponsible to connect to FTP server*/
int ftclient(int isDebug, char server[MAX_STRING], unsigned int port, char filename[MAX_STRING]) {

	parse_command(isDebug, server, port, filename);
	execute_transfer();	
	return 0;
}


/*Main: Entry poing for the ftclient*/
int main(int argc, char *argv[])
{
int isDebug = FALSE;
	if (argc != 5 && argc != 4) {
		fprintf(stderr,"ftclient [-d] <server> <port> <filename> \n");
		return -1;
	}
	else {
		if (argc == 5 && strcmp(argv[1],"-d") == 0)
			isDebug = TRUE;
		else if (argc == 5) {
			fprintf(stderr,"ftclient [-d] <server> <port> <filename> \n");
			return -1;
		}

		/*Call FTClient API */
		if (argc == 4) {
			check_length(argv[1], argv[3]);
			return ftclient(isDebug, argv[1], atoi(argv[2]), argv[3]);
		}
		else {
			check_length(argv[2],argv[4]);
			return ftclient(isDebug, argv[2], atoi(argv[3]), argv[4]);
		}
	}
	return 0;
}
