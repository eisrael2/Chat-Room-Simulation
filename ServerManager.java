import java.io.*;
import java.util.*;

public class ServerManager implements Runnable {
	
	private UserManager boss;
	private BufferedReader stdIn;
	private String tep;
	private Date currentTime;
	private PriorityQueue<String> removalQueue;
	private long timeDiff;
	
	public ServerManager(UserManager manager) {
		
		boss = manager;
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		currentTime = new Date();
		removalQueue = new PriorityQueue<String>();
	}
	@Override
	public void run() {
		while(boss.isRunning()) {
			try {
				if(stdIn.ready()) {
					tep = stdIn.readLine();
					if(tep.equals("-shutdown")) {
						boss.getUserList().forEach((key, value) -> {
							value.addToQueue("Server Shutdown.");
							removalQueue.add(key);
						});
						clearRemovalQueue();
						boss.shutDown();
					} 
					else {
						System.out.println("Unsupported command "+ tep);
					}
				}
				currentTime.setTime(System.currentTimeMillis());
				
				boss.getUserList().forEach((key, value) -> {
					timeDiff = currentTime.getTime() - value.getLastSeen();
					if(timeDiff > 1500) {
						System.out.println(key + " removed for timeout.");
						removalQueue.add(key);
					}
				});
				clearRemovalQueue();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clearRemovalQueue() {
		while(!removalQueue.isEmpty()) {
			String userName = removalQueue.remove();
			boss.removeInstance(userName);
		}
	}
}
