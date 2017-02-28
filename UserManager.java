import java.io.*;
import java.net.*;
import java.util.*;

public class UserManager {
	
	private ServerSocket serverSocket;
	private String serverPassword;
	private boolean running = true;
	private int port;
	private HashMap<String, ClientInstanceOf> clientList = new HashMap<String, ClientInstanceOf>();
	private Thread janitorThread;
	private CommandRunner commandProcessor;
	ArrayList<String> history = new ArrayList<String>(); 
	
	//Create a new server at the specified port
	public UserManager(int port, String password) {
		
		this.port = port; //port to listen on
		this.serverPassword = password; //enable access to administrator commands
		janitorThread = new Thread(new ServerManager(this));
		janitorThread.start();
		commandProcessor = new CommandRunner(this);
		
		try {
			serverSocket = new ServerSocket(this.port);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// Open server socket for listening
	public void startListening() {
		while(running){
			try {
				try {
					Socket clientSocket = serverSocket.accept();
					createInstance(clientSocket);
				} 
				catch (SocketException e) {
					System.out.println("Server closed. Goodbye!");
				}				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Create a new instance for the user
	private void createInstance(Socket clientSocket) {
		ClientInstanceOf client = new ClientInstanceOf(clientSocket, this);
		Thread thread = new Thread(client);
		thread.start();
	}
	
	//Add a given instance to a user
	public boolean addInstance(String userName, ClientInstanceOf client) {
		if(clientList.containsKey(userName))
			return false;
		clientList.put(userName, client); //Associate userName with a client
		return true;
	}
	
	//Gets the user instance of
	public ClientInstanceOf getInstance(String userName) {
		return clientList.get(userName);
	}
	
	//Delete a given instance
	public void removeInstance(String userName) {
		clientList.get(userName).addToQueue("You have been removed from the server");
		clientList.get(userName).cleanInstance();
		clientList.remove(userName);
	}
	
	//Client input process
	public void processInput(String message, String userName) {
		if(message.charAt(0) == '-') {
			commandProcessor.processInput(message, userName);
			if (!message.equals("-heartbeat")) {
				this.history.add(userName + " : " + message);
			}
		} 
		else {
			addMessageToAllExcept(userName + " said: " + message, userName);
			this.history.add(userName + " : " + message);
		}
	}
	
	//Message sent to all users - 1 (the source)
	public void addMessageToAllExcept(String message, String exceptUser) {
		clientList.forEach((key, value) -> {
			if(!value.getUserName().equals(exceptUser))
				value.addToQueue(message); //User's message sent
		});
	}
	
	public HashMap<String, ClientInstanceOf> getUserList() {
		return clientList;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public String getPassword() {
		return serverPassword;
	}
	
	//Output strings concatenations
	public String implodeRange(String[] strArray, char glue, int start, int end) {
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < end; i++) { //start and end of user input index
			builder.append(strArray[i] + glue); //user input string array
		}
		return builder.toString();
	}
	
	//Server shutdown gracefully
	public void shutDown() {
		running = false;
		
		try {
			serverSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
