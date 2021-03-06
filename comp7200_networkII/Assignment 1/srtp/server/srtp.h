/* SRTP.h : Contains all the definitions and data structures for FTServer */
#ifndef FTSERVER_H
#define FTSERVER_H

#include <arpa/inet.h> 
#include <fcntl.h>
#include <inttypes.h>
#include <netinet/in.h>
#include <netdb.h>
#include <pthread.h> 
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>
#include <errno.h>

#define TRUE 1
#define FALSE !TRUE
#define MAX_BUFFER 458
#define MAX_STRING 256
#define MIN_SEQ 1
#define MAX_SEQ 4096
#define TIMEOUT 2

int ISDEBUG = FALSE;
int SET_EXIT = FALSE;
enum FR_TYPE { FT_NAME = 1, FT_DATA, FT_OK,  RT_CONNECT, RT_DATA, RT_FIN, RT_OK};

/* FTP Client - Sends FT_NAME or FT_DATA while server sends FT_OK or 0 to indicate the success*/
struct FTP_PACKET_TYPE {
	u_int32_t buffer_size;
	u_int32_t packet_type;
	unsigned char buffer[MAX_BUFFER];
	}__attribute__ ((packed));

/* FTP server layers remebers the following*/
struct FT_SERVER_TYPE {
	unsigned int port;
	char server[MAX_STRING];
	char filename[MAX_STRING];
	} FT_SERVER;

/* R layer - responsible to maintain the state and provide concurrency on server side. R client sends R_CONNECT, R_DATA while R server replies with R_OK or 0 packet */
struct R_PACKET_TYPE {
	u_int32_t buffer_size;
	u_int32_t packet_type;
	struct FTP_PACKET_TYPE ftp_packet;
	}__attribute((packed));

/* R layers server remember the following*/
struct R_SERVER_TYPE {
	struct sockaddr_in server_address, client_address;
	int listen_fd;
	struct GO_BACKN_RECEIVER_TYPE *parent, *child;
	short (*cfback)(unsigned char *recv_buf, unsigned char *send_buf,  size_t recv_size, size_t *send_size);
	short (*cbaccept)(struct FTP_PACKET_TYPE, struct FTP_PACKET_TYPE *);
	};

/* GO layer implements the go back n protocol with the help of seq, crc and buffer size*/
struct GO_PACKET_TYPE {
	u_int32_t seq_number;
	u_int32_t crc;
	u_int32_t buffer_size;
	unsigned char buffer[sizeof(struct R_PACKET_TYPE)];
}__attribute__ ((packed));

/*GO BACK layer remembers the following: In other words elements required for sliding window protocol on server side*/
struct GO_BACKN_RECEIVER_TYPE {
	int socketId;
	int child_socketId;
	struct sockaddr_in childServer, childClient;
	struct sockaddr_in serverAddr, clientAddr;
	long ack_number;
	struct R_SERVER_TYPE *r_server;
	short (*callback)(unsigned char *recv_buf, struct GO_PACKET_TYPE *send,  size_t recv_size, struct GO_BACKN_RECEIVER_TYPE *go_receiver);

};

/* Below function is taken from http://www.hackersdelight.org/hdcodetxt/crc.c.txt */
/* This is the basic CRC-32 calculation with some optimization but no
table lookup. The the byte reversal is avoided by shifting the crc reg
right instead of left and by using a reversed 32-bit word to represent
the polynomial.
   When compiled to Cyclops with GCC, this function executes in 8 + 72n
instructions, where n is the number of bytes in the input message. It
should be doable in 4 + 61n instructions.
   If the inner loop is strung out (approx. 5*8 = 40 instructions),
it would take about 6 + 46n instructions. */

unsigned int crc32b(unsigned char *message) {
   int i, j;
   unsigned int byte, crc, mask;

   i = 0;
   crc = 0xFFFFFFFF;
   while (message[i] != 0) {
      byte = message[i];            // Get next byte.
      crc = crc ^ byte;
      for (j = 7; j >= 0; j--) {    // Do eight times.
         mask = -(crc & 1);
         crc = (crc >> 1) ^ (0xEDB88320 & mask);
      }
      i = i + 1;
   }
   return ~crc;
}

/* Custom code to accept a number, and convert to unsigned char and pass it crc32b */
unsigned int crc32(u_int32_t aNumber) {
	int i = 0, j = 0;	
	u_int32_t copy_number = aNumber;
	while ( aNumber != 0) 
	{
		aNumber /= 10;
		j++;
	}
	unsigned char *number_buffer = (unsigned char *) calloc(1, j);
	while( i < j)
	{
	number_buffer[i] = (char)(copy_number % 10);
	copy_number /= 10;
	i++;
	}
	return crc32b(number_buffer);
}


#endif
