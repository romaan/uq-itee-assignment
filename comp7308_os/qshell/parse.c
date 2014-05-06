/* parse : Split the input into tokens
and analyze the input */
#ifndef _PARSE_H_
#define _PARSE_H_ 

int emptySpaces(char *line);
int parse(char *line, struct commands_struct *command_line);

int emptySpaces(char *line) {
int i;
	for (i = 0; i < strlen(line); i++)
		if (line[i] != ' ' && line[i] != 10) {
			return FALSE;
		}
return TRUE;
}

int parse(char *line, struct commands_struct *command_line) {
int anIndex,i;
enum automata { INITIAL, COMMAND_ACCEPTED, UNKNOWN_ERROR } state;
char **token = init_token();//[MAX_TOKEN][MAX_TOKEN_SIZE];
int token_number = 0, token_letter = 0;
	//Check for empty string
	if (emptySpaces(line))
		return 1;
	//Tokenize
	for (anIndex = 0; anIndex < strlen(line); anIndex++) 
	{
		if(line[anIndex] == '#')	//Got comment, ignore rest
		{
		token[token_number][token_letter] = '\0';
		token_number++;
		break;
		}
		else if (line[anIndex] == ' ' || line[anIndex] == '\n'  || line[anIndex] == '\0')
		{
		token[token_number][token_letter] = '\0';
		token_number++;
		token_letter = 0;
		}
		else
		{
		token[token_number][token_letter++] = line[anIndex];
		}
	}
	//Fill the command_line structure
	for (anIndex = 0, command_line->total_command = 0, state = INITIAL; anIndex < token_number; anIndex++)
	{
		if (state == INITIAL)  //is alpha numeric
		{
			strcpy(command_line->command_list[command_line->total_command], token[anIndex]);
			//Initially zero arguments
			command_line->total_argument[command_line->total_command] = 0;
			state = COMMAND_ACCEPTED;
		}
		else if (state == COMMAND_ACCEPTED) 
		{
			if (strcmp(token[anIndex],"|") == 0 && anIndex + 1 < token_number)
			{
				command_line->total_command++;
				state = INITIAL;
			}
			else if (strcmp(token[anIndex],">") ==0 && anIndex + 1 < token_number)
			{
				strcpy(command_line->cmd_output[command_line->total_command], token[++anIndex]);
			}
			else if (strcmp(token[anIndex],"<") == 0 && anIndex + 1 < token_number)
			{
				strcpy(command_line->cmd_input[command_line->total_command], token[++anIndex]);
			}
			else if(strcmp(token[anIndex], "&") == 0 && anIndex + 1 == token_number)
			{
				command_line->background_process = TRUE;
			}
			else if (strlen(token[anIndex]) > 0)	//We have an argument for command
			{
				strcpy(command_line->argument_list[command_line->total_command][command_line->total_argument[command_line->total_command]], token[anIndex]);
				command_line->total_argument[command_line->total_command]++;
			}
			/*else //Error resulting in UNKOWN
			{
			state = UNKNOWN_ERROR;
			printf("Command unknown");

			}*/
		}
		else
		{
			printf("\nUnkown state");
		}
	}
	//Free the allocated memory
	free(token);

return 0;
}
#endif
