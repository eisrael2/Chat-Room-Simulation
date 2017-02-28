import java.io.*;
import java.net.*;
import java.util.*;


public class PassimtvUserTalk {

	String username;
	String hostname;
	int portNumber; 
	InetAddress inet;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	Socket socketFile;
	BufferedReader in;
	ArrayList<String> history;
	boolean startsWithDash;
	private Date lastHeartbeat;

	public PassimtvUserTalk(String username, String hostname, int portNumber) throws IOException {
		
		super();
		this.username = username;
		this.hostname = hostname;
		this.portNumber = portNumber;
		this.inet = InetAddress.getByName(hostname);
		this.socket = new Socket (inet,portNumber);
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream()); 
		in = new BufferedReader(new InputStreamReader(System.in));
		startsWithDash = false;
		lastHeartbeat = new Date();
	}

	public void communicate() throws Exception {
		
		dos.writeUTF(username); 

		String line = "";
		while (true) {
			if (dis.available() > 0) {
				line = dis.readUTF();
					System.out.println(line);
			}
			if(in.ready())
				if ((line = in.readLine()) != null) {
					String [] commands = line.split(" ");
					startsWithDash = line.charAt(0) == '-';

					switch(commands[0]) {

					case "-help":
						this.help();
						break;

					case "-uo":
						this.uo(line);
						break;

					case "-pm":
						this.pm(line);
						break;
						
					case "-gh":
						this.gh(line);
						break;
						
					case "-mute":
						this.muteUnmute(line);
						break;

					case "-unmute":
						this.muteUnmute(line);
						break;
						
					case "-out":
						System.out.println("You are exiting the chat");
						dos.writeUTF("-out");
						dis.close();
						dos.close();
						socket.close();
						break;

					default:
						if (startsWithDash) {
							this.help();
						}
						else {
							this.sendMessage(line);
						}
						break;
					}
				}
			if((System.currentTimeMillis() - lastHeartbeat.getTime()) > 1000) {
				lastHeartbeat.setTime(System.currentTimeMillis());
				dos.writeUTF("-heartbeat");
			}
		}
	}

	public void help() {
		String help = "-help :				        	Command types and their functionality. \n"
				       + "-uo: 				            Online users. \n"
				       + "-pm username message: 	    Send a private message to another online user. \n"
				       + "-mute         				mute chat \n"
				       + "-unmute       				unmute chat \n"
				       + "-gh							Get Chat History. \n"	
				       + "-out: 						Exit the application";

		System.out.println(help);

	}

	public void uo(String command) throws Exception {
		dos.writeUTF(command);
	}
	
	public void gh(String command) throws Exception {
		dos.writeUTF(command);
	}

	public void pm(String command) throws Exception {
		dos.writeUTF(command);
	} 
	
	public void muteUnmute(String command) throws Exception {
			dos.writeUTF(command);
		}

	public void sendMessage(String message) throws Exception {
		dos.writeUTF(message);
	}
}
