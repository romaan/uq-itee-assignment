/* Execute.c: Run the command by
* forking and execlp a child process */
#ifndef _EXECUTE_H_
#define _EXECUTE_H_

#define TRUE 1
#define FALSE 0
#define FIST_CMD 0
#define MIDDLE_CMD 1
#define LAST_CMD 2

#include<unistd.h>

extern struct background_task *background_task_list;
extern char *PATH;	//Defined and Initialized in Initialize.c
extern char *HOME;
pid_t pid;

void testParse(struct commands_struct *aCommand);
int execute(struct commands_struct *aCommand);
int customCommand(char *command, char argument[MAX_ARG][MAX_LENGTH], int total_argument);

//Testing only: To ensure properly parsed
void testParse(struct commands_struct *aCommand)
{
int i,j;
        for (i = 0; i <= aCommand->total_command; i++)
        {   
        printf("\n COMMAND: %d %s",i, aCommand->command_list[i]);
	printf("\nInput: %s", aCommand->cmd_input[i]);
	printf("\nOutput: %s",aCommand->cmd_output[i]);
                for (j = 0; j < aCommand->total_argument[i]; j++)
                        printf("\n Arg %d: %s",j, aCommand->argument_list[i][j]);
        }  
printf("\n"); 
}

//Execute the command after parsing: Call either custom command or underlying commands
int execute(struct commands_struct *aCommand)
{
int traverse_command;
int status,i,j;
int pipe_buffer[2];
char **pass_argument;
int fd_read, fd_write;

	//If there are two or more command, then it means there is a pipe
	if (aCommand->total_command >= 1) {
	pipe(pipe_buffer);
	}
	
	//testParse(aCommand);
	for (traverse_command = 0; traverse_command <= aCommand->total_command; traverse_command++) 
	if(!customCommand(aCommand->command_list[traverse_command], aCommand->argument_list[traverse_command], aCommand->total_argument[traverse_command]) && strlen(aCommand->command_list[traverse_command]) > 0)
		{ 
		//Copy command and argument to pass to exec family
		pass_argument = init_pass_argument(aCommand->total_argument[traverse_command], aCommand->argument_list[traverse_command], aCommand->command_list[traverse_command]);	
		if ((pid = fork()) < 0)
	        {
			fprintf(stdout, "Command not found\n");
		}
		else if (pid == 0)
	 	{
			//SET PG ID to 0, so the interrupt is not transferred to this background process
			if (aCommand->background_process == TRUE)	{
			setpgid(0, 0);
			}			
			//PIPE if more than one command		
			if (aCommand->total_command > 0)
			{
				//First Command
				if (traverse_command == 0) {
				fflush(stdout);
				close(pipe_buffer[0]);				
				dup2(pipe_buffer[1], fileno(stdout));
				close(pipe_buffer[1]);				
				}
				//Last command
				else if (traverse_command == aCommand->total_command) {
				close(pipe_buffer[1]);
				dup2(pipe_buffer[0], fileno(stdin));
				close(pipe_buffer[0]);						
				}		
			}
			//Redirect input from file
			if (strlen(aCommand->cmd_input[traverse_command]))
			{
				fd_read = open(aCommand->cmd_input[traverse_command],O_RDONLY, 0666);	
				if (fd_read < 0)
				{
				perror("Cannot open file to read");				
				exit(1);
				}
				dup2(fd_read, STDIN_FILENO);
			}
			else if (aCommand->background_process == TRUE) //No input and is background
			{
				dup2(open("/dev/null",O_RDONLY, 0666), STDIN_FILENO);
			}
			//Redirect output to file
			if (strlen(aCommand->cmd_output[traverse_command]))
			{
				fd_write = open(aCommand->cmd_output[traverse_command],O_WRONLY|O_CREAT|O_TRUNC, S_IRUSR | S_IWUSR);
				if (fd_write < 0)
				{
				perror("Cannot open file to write");
				exit(1);
				}
				dup2(fd_write, STDOUT_FILENO);
			}
			if (aCommand->total_argument[traverse_command] == 0)
				execlp(aCommand->command_list[traverse_command], aCommand->command_list[traverse_command], (char *) 0);
			else
				execvp(aCommand->command_list[traverse_command],pass_argument);
			fprintf(stderr, "%s: command not found\n", aCommand->command_list[traverse_command]);
			
			exit(0);
		} else {
		/*Parent*/
		if (traverse_command != 0)
		{
			close(pipe_buffer[0]);
			close(pipe_buffer[1]);
		}
		
		if (aCommand->background_process == FALSE) {
			waitpid(pid, &status, 0);
			if (status != 0)	//Command was not executed successfully, hence aborting future commands
				break;
		}
		else {
		/* Run the process in background */
		for (j = 0; j < background_task_list->max; j++)
		if (background_task_list->tasks[j] == 0)
			{
			background_task_list->tasks[j] = pid;
			break;
			}
		waitpid(-1, &status, WNOHANG);
		fprintf(stdout, "[%u] started",pid);
		}
		pid = 0;
		free(pass_argument);	
		}
	}
return 0;
}

//Custom commands implemented in QShell
int customCommand(char *command, char argument[MAX_ARG][MAX_LENGTH], int total_argument) 
{
int i,j;
	if (strcmp(command,"exit") == 0)
	{
		exit(0);
	}
	else if (strcmp(command,"cd") == 0)
	{
		if (total_argument > 0) 
		{ 
    			if (chdir((const char *) argument[0]) == -1)
				printf("\n%s: No such file or directory",argument[0]);
		}
		else
		{
			if (chdir((const char *) HOME) == -1)
				printf("\n%s: No such file or directory", HOME);
		}
	}
	else
	{
		return FALSE;
	}
	return TRUE;
}


#endif
