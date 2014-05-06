CC = gcc
CFLAGS = -g -Wall -pthread

all: ftclient ftserver

ftclient: client/ftclient.c
	$(CC) $(CFLAGS) $^ -o client/$@

ftserver: server/ftserver.c
	$(CC) $(CFLAGS) $^ -o server/$@

dist: clean
	tar -czf ../srtp.tar.gz *
clean:
	find client/* -type f -name '*.[ch]' -prune -o -exec rm -f '{}' ';'
	find server/* -type f -name '*.[ch]' -prune -o -exec rm -f '{}' ';'
