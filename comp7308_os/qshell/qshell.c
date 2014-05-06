/* Custom Shell : Main entry */
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<signal.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#include"command.h"
#include"initialize.c"
#include"parse.c"
#include"execute.c"

#define TRUE 1
#define FALSE 0
#define MAX_LINE 1024
extern pid_t pid;
extern int initialize();
extern int parse();
void get_cwd();
void signal_handler(int signo);
void processCommand(FILE *input_ptr);
void getCommandLineArgs(int argc, char *argv[]);

struct background_task *background_task_list;

//Get Present working directory
void get_cwd()
{
char cwd[MAX_LINE];
int i,status, result = 0;
	for (i = 0; i < background_task_list->max; i++)
	{
	result = 0;
		if (background_task_list->tasks[i] != 0) 
		{
		result = waitpid(background_task_list->tasks[i], &status, WNOHANG);
		if (result != 0) {
			fprintf(stdout,"\n [%u] finished with %u status\n,",background_task_list->tasks[i], status);
		background_task_list->tasks[i] = 0;
		}
		}
	}

	if (getcwd(cwd, sizeof(cwd)) == NULL)
		perror("getcwd() error ");
	fprintf(stdout, "\n%s?",cwd);
}

//Signal handler for the interrupt - Ctrl + C
void signal_handler(int signo)
{
	if (pid != 0) {
	printf("\n  [%u] interrupted\n", pid );
	//If ^C then stop any foreground process
	}
	fflush(stdout);
	get_cwd();
}

//Process commands pointed by input pointer (STDIN or CMD ARGS) -> Parse, Execute
void processCommand(FILE *input_ptr)
{
char input_line[MAX_LINE];
struct commands_struct *command_line = NULL;
get_cwd();
	while(fgets(input_line, MAX_LINE, input_ptr) != NULL) {
	command_line = init_command();
	if (parse(input_line, command_line) == 0) 
	{
	        execute(command_line);
	}
	get_cwd();
	free(command_line);
	fflush(input_ptr);
	}
}

//Decide whether the input is STDIN or from Input file
void getCommandLineArgs(int argc, char *argv[])
{
FILE *fd;
int aCount;
	for (aCount = 1; aCount < argc; aCount++)
	{
		fd = fopen(argv[aCount], "r");	
		if (fd < 0)
		{
		perror("Cannot open file to read");
		exit(1);
		}
		processCommand(fd);		
	}
}

int main(int argc, char *argv[]) 
{
initialize();
getCommandLineArgs(argc, argv);
processCommand(stdin);	
return 0;
}
