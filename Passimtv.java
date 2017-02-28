
public class Passimtv {
	
	private static UserManager userManager;
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("java Passimtv [portnumber] [password]");
			return;
		}
		userManager = new UserManager(Integer.parseInt(args[0]), args[1]);
		userManager.startListening();
	}
}
