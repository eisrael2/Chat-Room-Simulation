
public class PassimtvUser {

	public static void main (String [] args) {
		@SuppressWarnings("unused")
		int argsSize = args.length;
		
		if(args.length != 3 ) {
			System.out.println("Usage:");
			System.out.println("java PassimtvUser [username] [hostname] [portnumber]");
		}
		else {
			String username = args[0];
			String hostname = args[1];
			int portNumber = Integer.parseInt(args[2]);
			
			try {
				PassimtvUserTalk socket = new PassimtvUserTalk(username, hostname, portNumber);
				socket.communicate();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}		
		}
	}
}
