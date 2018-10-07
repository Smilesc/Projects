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
				printf("\nHere is the http message:\n%s\n", request);

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

			char *amp = "&";
			char *equals = "=";
			char *space = " ";

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

			// Determine HTTP verb that we are going to process
			char *httpGET = "GET";
			char *httpPOST = "POST";
			char httpVerbSeparators[] = " ";
			char *httpRequestToken;
			char *httpRequestContext;
			char httpVerb[15];
			httpRequestToken = strtok_r(request, httpVerbSeparators, &httpRequestContext);  // Start tokenizing the request string
			if (strcmp(httpRequestToken ,httpGET) == 0) strcpy(httpVerb, "GET");
			else if (strcmp(httpRequestToken ,httpPOST) == 0) strcpy(httpVerb, "POST");
			if (DEBUG) printf("HTTP Verb: %s\n", httpVerb);

			// Get the Request url
			char requestURL[2000];
			httpRequestToken = strtok_r (NULL, httpVerbSeparators, &httpRequestContext);
			strcpy(requestURL, httpRequestToken);
			if (DEBUG) printf("URL: %s\n", requestURL);

			// Get the request path
			char requestURLSeparator[] = "?";
			char *requestURLToken;
			char *requestURLStringContext;
			char requestPath[2000];
			char requestQueryString[2000];
			if (strchr(requestURL, '?') != NULL) {
				requestURLToken = strtok_r(requestURL, requestURLSeparator, &requestURLStringContext);
				strcpy(requestPath, requestURLToken);
				requestURLToken = strtok_r (NULL, requestURLSeparator, &requestURLStringContext);
				strcpy(requestQueryString, requestURLToken);
			} else {
				strcpy(requestPath, requestURL);
				requestQueryString[0] = '\0';
			}
			if (DEBUG) printf("Path: %s\n", requestPath);
			if (DEBUG) printf("Query String: %s\n", requestQueryString);

			// If we have a query string, then loop through the key/value pairs
			if ((requestQueryString != NULL) && (requestQueryString[0] != '\0')) {
				if (DEBUG) printf("Query String is not NULL or empty\n");
				char keyValuePairSeparator[] = "&";
				char *keyValuePairToken;
				char *keyValuePairContext;
				char key[2000];
				char value[2000];
				char keyValueSeparator[] = "=";
				char *keyValueToken;
				char *keyValueContext;
				keyValuePairToken = strtok_r(requestQueryString, keyValuePairSeparator, &keyValuePairContext);
				while (keyValuePairToken != NULL)
				{
					if (DEBUG) printf("keyValuePairToken: %s\n", keyValuePairToken);
					keyValuePairToken = strtok_r(NULL, keyValuePairSeparator, &keyValuePairContext);
					//keyValueToken = strtok_r(keyValuePairToken, keyValueSeparator, &keyValueContext);
					//strcpy(key, keyValueToken);
					//keyValueToken = strtok_r(NULL, keyValueSeparator, &keyValueContext);
					//strcpy(value, keyValueToken);
					//if (DEBUG) printf("%s = %s\n", key, value);
				}
			}


			if (strcmp(httpVerb ,httpGET) == 0)
			{
				if (DEBUG) printf("Processing HTTP GET\n");
				static char *token;
				

				//char parse_item = 0;
				printf("did statements\n");
				while(strcmp(parse_mark, space) != 0)
				{
					//int parse_item = *parse_mark;
					
					printf("in for\n");
					strcat(token, parse_mark);

					if(strcmp(parse_mark, equals) == 0)
					{
						sprintf(entity_body, "<tr><td>%s</td>", token);
						free(token);
					}
					if(strcmp(parse_mark, amp) == 0)
					{
						sprintf(entity_body, "<td>%s</td></tr>", token);
						free(token);
					}
					parse_mark++;
				}
				printf("after while\n");
				strcat(entity_body, "</table></body></html>");
			}
			else if (strcmp(httpVerb ,httpPOST) == 0)
			{
				printf("Processing HTTP POST\n");
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