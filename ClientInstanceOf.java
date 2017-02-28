import java.io.*;
import java.net.*;
import java.util.*;

public class ClientInstanceOf implements Runnable {
	
	private boolean running = true;
	private String userName;
	private UserManager userManager;
	private Socket clientSocket;
	public DataOutputStream output;
	private DataInputStream input;
	private PriorityQueue<String> messageQueue;
	private String nextLine = "";
	boolean muted = false;
	private Date lastSeen;
	
	public ClientInstanceOf(Socket clientSocket, UserManager userManager) {
		this.userManager = userManager;
		this.clientSocket = clientSocket;
		this.lastSeen = new Date();
		
		try {
			output = new DataOutputStream( this.clientSocket.getOutputStream());
			input = new DataInputStream( this.clientSocket.getInputStream());
			userName = input.readUTF(); //read the line and analysis the connection user name
			System.out.println(userName + " connected.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if(!userManager.addInstance(userName, this))
			try {
				output.writeUTF("Username unavailable. Connection closed.");
				cleanInstance();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		else
			messageQueue = new PriorityQueue<String>();
		
		while(running) {
			try {
				if(running && input.available() > 0) {
					nextLine = input.readUTF();
					userManager.processInput(nextLine, userName);
				}
				else if(running && !messageQueue.isEmpty()) {
					output.writeUTF(messageQueue.remove());
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void addToQueue(String message) {
		if(!muted) {
			messageQueue.add(message);
		}
	}
	
	public void updateLastSeen() {
		lastSeen.setTime(System.currentTimeMillis());
	}
	
	public long getLastSeen() {
		return lastSeen.getTime();
	}
	
	public void cleanInstance() {
		try {
			running = false;
			while(!messageQueue.isEmpty()) {
				output.writeUTF(messageQueue.remove());
			}
			input.close();
			output.close();
			clientSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
