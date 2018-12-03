import socket
import select
import sys
import threading
import bot

ip_address = "127.0.0.1"
port = 802

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind((ip_address, port)) 
server.listen(10)

client_list=[]

def client_thread(connection, address):

    connection.sendall(("You are now online as " + all_clients[connection]).encode('utf_8'))
    
    while(True):
        try:     
            message = connection.recv(2048).decode('utf_8')    
            if message:
                if message[:5] == "!wiki":
                    botThread = threading.Thread(target=bot.main, args=(message[5:-1],))
                    botThread.start()
                    
                    broadcast("[" + all_clients[connection] + " has sent a wiki request]", connection)
                    connection.sendall("[You have sent a wiki request]".encode('utf-8'))

                    botThread.join()
                    astring = bot.astring

                    broadcast(("[Wikibot] " + astring), connection)
                    connection.sendall(("[Wikibot] " + astring).encode('utf-8'))
                else:
                    message_to_send = "[" + all_clients[connection] + "] " + message
                    broadcast(message_to_send,connection)
            else:
                remove(connection)

        # Exception handling not our code: https://stackoverflow.com/questions/9823936/python-how-do-i-know-what-type-of-exception-occurred
        except Exception as ex:
            template = "An exception of type {0} occurred. Arguments:\n{1!r}"
            message = template.format(type(ex).__name__, ex.args)
            print(message)

def remove(rconnection):
    global i
    if rconnection in all_clients.keys():
        client_list.remove(rconnection)
        broadcast("[" + all_clients[rconnection] + " has left]", connection)
        del all_clients[rconnection]
        i -= 1

def broadcast(message, connection):
    for clients in client_list:
        if clients != connection:
            try:
                clients.sendall(message.encode('utf-8'))
            except:
                clients.close()
                remove(clients)
i = 1
all_clients = {}
print("Server is online")
while (True):
        connection, address = server.accept()
        client_list.append(connection)
        all_clients[connection] = "User " + str(i)
        broadcast("[" + all_clients[connection] + " has connected]", connection)
        chat_client = threading.Thread(target=client_thread,args=(connection,address))
        chat_client.start()
        if not chat_client.is_alive():
            print(all_clients[connection] + " has left")
            remove(connection)
        i += 1

for client in chat_clients:
    client.join()
connection.close()
server.close()