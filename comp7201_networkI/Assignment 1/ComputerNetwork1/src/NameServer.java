import java.io.PrintWriter;
import java.util.HashMap;

/* 
 * NameServer.java: Provide a naming service 
 */

final public class NameServer extends Listener {
	
	//Store Process Name to IP:Port as HashMap
	private HashMap<String,String> nameToIpPort; 	
	
	//Initialize NameServer
	public NameServer(int listeningPort) throws Exception {
	super(listeningPort);	
	System.out.println("\nName server waiting for incoming connection...\n");
	nameToIpPort = new HashMap<String,String>();	
	while (true) {
		acceptConnection();
		takeAction(in.readLine(), out);
		}
	}
	
	//On receiving a request, parse the request and reply with response
	protected void takeAction(String line, PrintWriter out) {
		String[] token = line.split(":");

		// <register> : <processName> : <ip> : <port>
		if (token.length == 3 && token[0].compareTo("register") == 0) {			
			nameToIpPort.put(token[1], connSocket.getInetAddress().getHostName() + ":" + token[2]);
			System.out.println("Process -> " + token[1] + " with IP:Port "+ connSocket.getInetAddress().getHostName() + ":" + token[2] + " registered");
		}
		// <lookup> : <processName>
		else if (token.length == 2 && token[0].compareTo("lookup") == 0) {	
			String ipPort = nameToIpPort.get(token[1]);
			if (ipPort == null) {
				out.println("Error:Process has not registered with the Name Server");
				System.out.println("Error: "+token[1]+" Process has not registered with the Name Server");
			}
			else
				out.println(ipPort);
		}
	}

	//java NameServer <port_number>
	public static void main(String[] args) {	
		try {		
		if (args.length != 1 || Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) > 65535) 
			throw new Exception();
		} catch (Exception e) {
			System.err.println("Invalid command line argument for Name Server\n");
			System.exit(1);
		}
		
		try {
		new NameServer(Integer.parseInt(args[0]));
		} catch(Exception ex) {
			System.err.println("Error Communicating");
			System.exit(1);	
		}	
	}	
}