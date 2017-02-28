# Chat-Room-Simulation

Client-Server Chat Room Simulation

Overview
	
The main objective of this project is to implement a Java based chat simulation that will allow users with an internet connection to engage in an online communication.  The development of this project focused on the development of a message protocol that would allow users to log in properly, send messages or private message to one another, and perform additional functions as well.

Development

The Chat simulation was developed iteratively.  At the beginning, the development was focused on a server that would allow communication between a single user and a central server.  In the next step, functionality was added that allowed to connect multiple users. The development focused on the creation of private communication between users.  This step allowed multiple users to communicate among themselves.

The java class ‘Client Instance Of’ is responsible for opening a socket connection with clients, connections verification, and to maintain the chat simulation alive.  The class ‘Command Runner’ is responsible for handling the interactions between clients and the server.  This class broadcasts messages to all users in the server window, handles private message requests, prints a list of online users and prints chat history.

Users’ messages is one of the key components of this project.  For every message sent, the first byte is the message type which can be one of the following:

•	  Broadcast a message – used to send messages from a client to other users  
  through the server.
  Help - -help – prints out a list of all functionalities. 
•	  Check online users - ou – used to check which users are live in the chat.
•	  Private message - -pm – used to send a private message from one user to another 
  user.
•	  Mute - -mute – used when a user wants to avoid from receiving chat 
  communication.
•	  Unmute - -unmute – used when a user wants to receive chat communication 
  after being muted.
•	  Print Chat History - -gh – used in order to print chat history.
•	  Exit Chat - -out – used when a user wants to exit the chat.


Below, there is an example message communication between the server and two clients.


   
1.	Server is been Setup.  
2.	Client 1 connects to the server. Server confirms by printing out the user name.
3.	Client 2 connects to the server. Server confirms by printing out the user name.
4.	Client 1 broadcast a message to Client 2 through the server.
5.	Client 2 response back with a broadcast message to Client 1 through the server.
6.	Client 1 sends a private message to Client 2 through the server.
7.	Client 2 mutes its chat communication and the server prints that a user was muted.
8.	Client 2 unmutes its chat communication and the server prints that a user was unmuted.
9.	Client 2 requests to print chat history and the server prints out a message that a chat history is been printed out. The chat history is been printed in Client 2 window.
10.	Client 1 requests to see a list of online users and the server prints out a message that an online users list is been printed out. The list is been printed in Client 1 window.
11.	Client 1 exits the chat.
12.	Client 2 exits the chat.

User’s Guide

The following steps are needed to run the Chat simulation successfully:

•	Open a command line window from the server src folder to start up the server and type: “java Passimtv [port number] [password] (Please note that the word: “password” is used as a password connection).
•	Open a command line window from the client src folder to start up the client and type: “java PassimtvUser [username] localhost [port number] (Please note that the port number must match the port number in the server window).
•	Type -help, to see a list of all functionalities that are available in the chat simulation. 
•	Exit –out, to exit the chat simulation or simply close the window.

Note: For more functions, please refer to the development section above. 

Conclusion

This project was a great challenge for me. I have come up with the idea for a chat simulation through a website that I use to login in order to catch sports games online where a small chat room is used to broadcast messages from various users that are online. 
No doubt that chat applications that exists today, are a wonderful ways to communicate with people that live close by or been away from you on the other side of the world.

When I have started this project, I knew that I would like to implement functions that do not exist within the website chat. For example, the ability to mute myself or the print out the chat history to see communications from other users while you were muted. 
Throughout the development process, I have learned a lot about what it takes to develop a messaging protocol and to create basic functionalities.  Overall, it was great experience to see it all come together after a few weeks of implementation. 
