/*SRTP.h : Contains all the definitions and data structures for FTClient*/
#ifndef FTCLIENT_H 
#define FTCLIENT_H

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
#define MAX_WINDOW 5L
#define MIN_SEQ 1
#define MAX_SEQ 4096
#define MAX_RETRY 2
#define TIMEOUT 2

int ISDEBUG = FALSE;
/*Sequence number generator for GO Back N layer */
static long seq_generator = MIN_SEQ;

/*Our implementation consists of three layers - FTP layer, Reliablity layer and GO Back N layer*/
/* FT_* is used in FTP_PACKET and RT_* is used in R_PACKET*/
enum FR_TYPE { FT_NAME = 1, FT_DATA, FT_OK,  RT_CONNECT, RT_DATA, RT_FIN, RT_OK};

/* FTP Client - Sends FT_NAME or FT_DATA while server sends FT_OK or 0 to indicate the success*/
struct FTP_PACKET_TYPE {
	u_int32_t buffer_size;
	u_int32_t packet_type;
	unsigned char buffer[MAX_BUFFER];
	}__attribute__ ((packed));

/* FTP Client layers remebers the following*/
struct FT_CLIENT_TYPE {
	unsigned int port;
	char server[MAX_STRING];
	char filename[MAX_STRING];
	struct R_CLIENT_TYPE *r_client;
	} FT_CLIENT;

/* R layer - responsible to maintain the state and provide concurrency on server side. R client sends R_CONNECT, R_DATA while R server replies with R_OK or 0 packet */
struct R_PACKET_TYPE {
	u_int32_t buffer_size;
	u_int32_t packet_type;
	struct FTP_PACKET_TYPE ftp_packet;
}__attribute((packed));

/* R layers client remember the following*/
struct R_CLIENT_TYPE {
	struct GO_BACKN_SENDER_TYPE *go_sender;
	unsigned char *recv_buffer;
	size_t recv_buffer_size;
};

/* GO layer implements the go back n protocol with the help of seq, crc and buffer size*/
struct GO_PACKET_TYPE {
	u_int32_t seq_number;
	u_int32_t crc;
	u_int32_t buffer_size;
	unsigned char buffer[sizeof(struct R_PACKET_TYPE)];
}__attribute__((packed));

/*GO BACK layer remembers the following: In other words elements required for sliding window protocol on client side*/
struct GO_BACKN_SENDER_TYPE {
	int left_ptr;
	int right_ptr;
	int retry;
	int receiver_run;
	int buffer_count;
	long seq_number[MAX_WINDOW];
	long ack_number[MAX_WINDOW];
	long segment_number[MAX_WINDOW];
	long segment_size[MAX_WINDOW];
	unsigned char received_buffer[sizeof(struct R_PACKET_TYPE)];
	size_t received_buffer_size;
	int socketId;
	struct sockaddr_in serverAddr, clientAddr;
	struct hostent *host;
	
};

long get_next_seq() {
	if (seq_generator == MAX_SEQ)
		seq_generator = MIN_SEQ;
	return seq_generator++;
}

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
