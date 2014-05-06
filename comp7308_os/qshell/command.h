/*Command.h : Containts the structure of a command*/
#ifndef _COMMAND_H_
#define _COMMAND_H_
#define MAX_COMMANDS 10
#define MAX_ARG 10
#define MAX_LENGTH 50
#define MAX_TASKS 10
enum boolean { FALSE, TRUE };

/*Struct representing the commands for a given input line*/
struct commands_struct
{
	//Default input is STDIN and output is STDOUT
	char cmd_input[MAX_COMMANDS][MAX_LENGTH];
	char cmd_output[MAX_COMMANDS][MAX_LENGTH];

	enum boolean background_process;
	
	char command_list[MAX_COMMANDS][MAX_LENGTH];
	char argument_list[MAX_COMMANDS][MAX_ARG][MAX_LENGTH];
	int total_command;
	int total_argument[MAX_COMMANDS];
};

/*Struct to hold all the background jobs*/
struct background_task
{
	int tasks[MAX_TASKS];
	int max;
};
#endif
