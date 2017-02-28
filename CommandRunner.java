import java.util.Map.Entry;

public class CommandRunner {
	private UserManager incharge;
	
	public CommandRunner(UserManager manager) {
		incharge = manager;
	}
	
	public void processInput(String message, String userName) {
		
		String[] command = message.substring(1).split(" "); //Processed message from processInput
		if(command[0].equals("pm")) {

			ClientInstanceOf tempInstance = incharge.getInstance(command[1]); 
			if(tempInstance != null) {
				tempInstance.addToQueue(userName + " pm: " + incharge.implodeRange(command, ' ', 2, command.length));
			} 
			else {
				incharge.getInstance(userName).addToQueue("[server]: " + command[1] + " is not currently online.");
			}
		}
		
		else if(command[0].equals("uo")) {
			System.out.println("Print Users Online");
			processCommand(command[0], userName, "");
		}
		else if(command[0].equals("gh")) {
			System.out.println("Print History");
			processCommand(command[0], userName, "");
		}
		else if(command[0].equals("mute")) {
			System.out.println("User mute");
			processCommand(command[0], userName, "");
		}
		else if(command[0].equals("unmute")) {
			System.out.println("User unmute");
			processCommand(command[0], userName, "");
		}
		else if(command[0].equals("heartbeat")) {
			ClientInstanceOf tempInstance = incharge.getInstance(userName);
			if(tempInstance != null) {
				tempInstance.updateLastSeen();
			}
		}
		else if(command[0].equals("out")){
				incharge.removeInstance(userName);
		}
	}
	
	public void processCommand(String command, String askerUserName, String other) {
		
		if(command.equals("uo")) {	
			ClientInstanceOf asker = incharge.getInstance(askerUserName);
			asker.addToQueue("Current Users Online: ");
			for (Entry<String, ClientInstanceOf> usersLoggedOn : incharge.getUserList().entrySet()) {
				asker.addToQueue(usersLoggedOn.getKey());
			}
		}
		
		else if(command.equals("gh")) {
			ClientInstanceOf getHistory = incharge.getInstance(askerUserName);
			for (int i = 0; i < incharge.history.size(); i++) {
				getHistory.addToQueue(incharge.history.get(i));
			}
			getHistory.addToQueue("Here is the History");
		}
		
		else if(command.equals("mute")) {
			ClientInstanceOf muter = incharge.getInstance(askerUserName);
			muteUser(other);
			muter.addToQueue("You muted " + other);
			muter.muted = true;
		}

		else if(command.equals("unmute")) {
			ClientInstanceOf unmuter = incharge.getInstance(askerUserName);
			unmuteUser(other);
			unmuter.muted = false;
			unmuter.addToQueue("You unmuted " + other);
		}
	}
	
	private void muteUser(String userName) {
		@SuppressWarnings("unused")
		ClientInstanceOf muter = incharge.getInstance(userName);
	}
	
	private void unmuteUser(String userName) {
		@SuppressWarnings("unused")
		ClientInstanceOf unmuter = incharge.getInstance(userName);
	}
}
