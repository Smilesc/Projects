
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
		if(child > 0 || child < 0)
		{
			exit(0);
		}
		else
		{
			printf("----Forked----\n");
			bzero(request, 2048);

			read(client_socket_fd, request, 2047);

			if (DEBUG)
				printf("Here is the http message:\n%s\n\n", request);

			// -------------------------------------
			// TODO:
			// -------------------------------------
			// Generate the correct http response when a GET or POST method is sent
			// from the client to the server.
			//
			// In general, you will parse the request character array to:
			// 1) Determine if a GET or POST method was used
			// 2) Then retrieve the key/value pairs (see below)
			// -------------------------------------

			/*
			------------------------------------------------------
			GET method key/values are located in the URL of the request message
			? - indicates the beginning of the key/value pairs in the URL
			& - is used to separate multiple key/value pairs 
			= - is used to separate the key and value
			
			Example:
			
			http://localhost/?first=brent&last=munsell
			
			two &'s indicated two key/value pairs (first=brent and last=munsell)
			key = first, value = brent
			key = last, value = munsell
			------------------------------------------------------
			*/

			/*
			------------------------------------------------------
			POST method key/value pairs are located in the entity body of the request message
			? - indicates the beginning of the key/value pairs
			& - is used to delimit multiple key/value pairs 
			= - is used to delimit key and value
			
			Example:
			
			first=brent&last=munsell
			
			two &'s indicated two key/value pairs (first=brent and last=munsell)
			key = first, value = brent
			key = last, value = munsell
			------------------------------------------------------
			*/
			static char *entity_body = 
				"<html><body><h2>CSCI 340 (Operating Systems) Homework 2</h2><table border=1 width=\"50%\"><tr><th>Key</th><th>Value</th></tr>";
			// char *end_type = strchr(request, ' ');
			char *parse_mark = strchr(request, '?') + 1;
			//char *end_pairs = strchr(request, "HTTP") - 1;

			//char url_only[end_pairs - parse_mark];
			//strncpy(url_only, parse_mark + 1, end_pairs - parse_mark);

			// char type[end_type - request];
			// strncpy(type, request, end_type - request);

			//char *token_end;

			char *amp = "&";
			char *equals = "=";
			char *space = " ";

			printf("TYPE: %c\n", request[0]);
			//printf("request[1]: %c\n", request[1]);
			if (request[0] == 71)
			{
				printf("in if******\n");
				static char *token;
				printf("did statements\n");

				char parse_item = 0;
				
				while(strcmp(parse_item, space) != 0)
				{
					int parse_item = *parse_mark;
					
					printf("in for\n");
					strcat(token, parse_mark);

					if(strcmp(parse_item, equals) == 0)
					{
						sprintf(entity_body, "<tr><td>%s</td>", token);
						free(token);
					}
					if(strcmp(parse_item, amp) == 0)
					{
						sprintf(entity_body, "<td>%s</td></tr>", token);
						free(token);
					}
					parse_mark++;
				}
				printf("after while\n");
				strcat(entity_body, "</table></body></html>");
			}
			else if (request[0] == 80)
			{
			}

			// THIS IS AN EXAMPLE ENTITY BODY
			//char *entity_body = "<html><body><h2>CSCI 340 (Operating Systems) Homework 2</h2><table border=1 width=\"50%\"><tr><th>Key</th><th>Value</th></tr></table></body></html>";

			char response[512];
			sprintf(response, "HTTP/1.1 200 OK\r\nContent-Length: %d\r\n\r\n%s", (int)strlen(entity_body), entity_body);

			if (DEBUG)
				printf("%s\n", response);

			write(client_socket_fd, response, strlen(response));

			close(client_socket_fd);
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
