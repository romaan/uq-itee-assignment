/* Initialize the shell with
* necessary environment variables */
#ifndef _INITIALIZE_H_
#define _INITIALIZE_H_
#define MAX_TOKEN 100
#define MAX_TOKEN_SIZE 100

extern void signal_handler(int);
char *PATH;
char *HOME;
extern struct background_task *background_task_list;

int initialize();
struct background_task* init_background_task();
char** init_token();
struct commands_struct* init_command();
char** init_pass_argument(int size, char arg_list[MAX_ARG][MAX_LENGTH], char *cmd);

//Initialize the qShell
int initialize() {
	/* Register signal handler */
	signal(SIGINT, signal_handler);
	/* Inherit the path settings */
 	PATH = getenv("PATH");	
	HOME = getenv("HOME");
	background_task_list = init_background_task();
	background_task_list->max = MAX_TASKS;
	return 0;
}

//Allocate memory and initialize command structure
struct commands_struct* init_command() 
{
	int anIndex, internal_index;
	struct commands_struct* aCommand = (struct commands_struct *) calloc(1, sizeof(struct commands_struct));
	//Set defaults, and field not initialized are zeroed or cleared by calloc
	aCommand->background_process = FALSE;
	aCommand->total_command = 0; //No command stored at this point in array
	for (anIndex = 0; anIndex < MAX_COMMANDS; anIndex++)
	{
	strcpy(aCommand->command_list[anIndex],"\0");
		for (internal_index = 0; internal_index < MAX_ARG; internal_index++)
		strcpy(aCommand->argument_list[anIndex][internal_index],"\0");
	aCommand->total_argument[anIndex] = 0;
	}

return aCommand;
}

//Allocate and Initialize the token structure
char** init_token() 
{
int i;
	char **token = (char **) calloc(MAX_TOKEN * MAX_TOKEN_SIZE, sizeof(char));
	for (i = 0; i < MAX_TOKEN; i++)
		token[i] = (char *) calloc(MAX_TOKEN_SIZE, sizeof(char));
return token;
}

//Allocate and Initialize passing arguments
char** init_pass_argument(int size, char arg_list[MAX_ARG][MAX_LENGTH], char *cmd)
{
int i;
	//One extra block for storing the command and rest for arguments
	char **arg = (char **) calloc((size + 1) * MAX_LENGTH, sizeof(char));
	arg[0] = (char *) calloc(MAX_LENGTH, sizeof(char));
	strcpy(arg[0], cmd);
	for (i = 1; i < size + 1 ; i++) 
	{
		arg[i] = (char *) calloc(MAX_LENGTH, sizeof(char));		
		strcpy(arg[i], arg_list[i - 1]);
	}	
	return arg;
}

//Initialize empty background task struct
struct background_task* init_background_task()
{
	return (struct background_task *) calloc(1, sizeof(struct background_task));
}
#endif
