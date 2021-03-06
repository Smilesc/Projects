// -----------------------------------
// CSCI 340 - Operating Systems
// Fall 2018
// server.h header file
// Homework 2
//
// -----------------------------------
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include "server.h"
// ------------------------------------
// Function prototype that creates a socket and
// then binds it to the specified port_number
// for incoming client connections
//
//
// Arguments:	port_number = port number the server
//				socket will be bound to.
//
// Return:      Socket file descriptor (or -1 on failure)
//
int bind_port(unsigned int port_number)
{
	// -------------------------------------
	// NOTHING TODO HERE :)
	// -------------------------------------
	// Please do not modify
	int socket_fd;
	int set_option = 1;
	struct sockaddr_in server_address;

	socket_fd = socket(AF_INET, SOCK_STREAM, 0);
	setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, (char *)&set_option, sizeof(set_option));
	if (socket_fd < 0)
		return FAIL;
	bzero((char *)&server_address, sizeof(server_address));
	server_address.sin_family = AF_INET;
	server_address.sin_addr.s_addr = INADDR_ANY;
	server_address.sin_port = htons(port_number);
	if (bind(socket_fd, (struct sockaddr *)&server_address, sizeof(server_address)) == 0)
	{
		return socket_fd;
	}
	else
	{
		return FAIL;
	}
} // end bind_port function
  // ------------------------------------
// Function prototype that accepts a client
// socket connection
//
//
// Arguments:	server file descriptor
//
// Return:      Termination status of client
//				( 0 = No Errors, -1 = Error )
//
int accept_client(int server_socket_fd)
{
	int exit_status = OK;
	int client_socket_fd = -1;
	socklen_t client_length;
	struct sockaddr_in client_address;
	char request[2048];
	client_length = sizeof(client_address);
	client_socket_fd = accept(server_socket_fd, (struct sockaddr *)&client_address, &client_length);

	// -------------------------------------
	// TODO:
	// -------------------------------------
	// Modify code to fork a child process
	// -------------------------------------
	int child = fork();
	if (client_socket_fd >= 0)
	{
		if (child == 0)
		{
			bzero(request, 2048);

			read(client_socket_fd, request, 2047);

			if (DEBUG)
			{
				printf("Here is the http message:\n%s\n\n", request);
			}

			static char entity_body[2000] = "<html><body><h2>CSCI 340 (Operating Systems) Homework 2</h2><table border=1 width=\"50%\"><tr><th>Key</th><th>Value</th></tr>";
			char *end_type = strchr(request, ' ');
			char *begin_pairs = strchr(request, '?');
			int type_length = end_type - request;
			char type[type_length];
			strncpy(type, request, type_length);
			type[type_length] = '\0';

			if ((strcmp(type, "GET") == 0) && (begin_pairs == NULL))
			{
				//Extract file name
				char *begin_file = strchr(request, '/') + 1;
				char *end_file = strchr(begin_file, ' ');

				int file_length = end_file - begin_file;

				char file_name[file_length];
				strncpy(file_name, begin_file, file_length);
				file_name[file_length] = '\0';

				FILE *file = fopen(file_name, "r");

				//Parse file to string
				char c = fgetc(file);
				for (int i = 0; c != EOF; i++)
				{
					entity_body[i] = c;
					c = fgetc(file);
				}
			}

			else if (strcmp(type, "GET") == 0)
			{

				char *token_start = begin_pairs + 1;

				while (1)
				{
					//find end of key/value pair
					char *token_end = strchr(token_start, '&');
					if (token_end == NULL)
					{
						token_end = strchr(token_start, ' ');
					}

					//extract key
					char *key_end = strchr(token_start, '=');
					int key_len = key_end - token_start;
					char key[key_len];
					strncpy(key, token_start, key_len);
					key[key_len] = '\0';

					//extract value
					char value[token_end - key_end];
					int val_len = token_end - key_end - 1;
					strncpy(value, (key_end + 1), val_len);
					value[val_len] = '\0';

					//HTML things here
					strcat(entity_body, "<tr><td>");
					strcat(entity_body, key);
					strcat(entity_body, "</td><td>");
					strcat(entity_body, value);
					strcat(entity_body, "</td></tr>");

					token_start = token_end + 1;
					printf("entity_body: %s\n", entity_body);
					printf("key:%s, value:%s\n", key, value);

					if (token_end[0] == ' ')
					{
						break;
					}
				}
				strcat(entity_body, "</table></body></html>");
			}
			
			if (strcmp(type, "POST") == 0)
			{
				//Locate body
				char *body = strstr(request, "\r\n\r\n") + 4;

				strcat(entity_body, "<tr>");

				while (1)
				{
					//find end of key/value pair
					char *token_end = strchr(body, '&');
					if (token_end == NULL)
					{
						token_end = strchr(body, '\0');
					}

					//extract key end
					char *key_end = strchr(body, '=');

					//extract value
					char value[token_end - key_end];
					int val_len = token_end - key_end - 1;
					strncpy(value, (key_end + 1), val_len);
					value[val_len] = '\0';

					//HTML things here
					strcat(entity_body, "<td>");
					strcat(entity_body, value);
					strcat(entity_body, "</td>");

					body = token_end + 1;

					if (token_end[0] == '\0')
					{
						break;
					}
				}
				strcat(entity_body, "</tr></table></body></html>");
			}
			char response[512];
			sprintf(response, "HTTP/1.1 200 OK\r\nContent-Length: %d\r\n\r\n%s", (int)strlen(entity_body), entity_body);

			if (DEBUG)
				printf("%s\n", response);

			write(client_socket_fd, response, strlen(response));

			close(client_socket_fd);
			exit(0);
		}
		else if (child > 0)
		{
			close(client_socket_fd);
		}
		else
		{
			return FAIL;
		}
	}
	else
	{
		exit_status = FAIL;
	}

	if (DEBUG)
		printf("Exit status = %d\n", exit_status);

	return exit_status;

} // end accept_client function