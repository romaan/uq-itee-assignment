import java.util.HashMap;

/* 
 * NameServer.java: Provide a naming service 
 */

final public class NameServer extends UDPCommTravelAgency {
	
	//Store Process Name to IP:Port as HashMap
	private HashMap<String,String> nameToIpPort; 	
	
	//Initialize NameServer
	public NameServer(int listeningPort) throws Exception {
	super(listeningPort);	
	System.out.println("\nName server waiting for incoming connection...\n");
	nameToIpPort = new HashMap<String,String>();	
	while (true) {
		String receiveMessage = null;
		while ((receiveMessage = receive(false, null)) == null);
		takeAction(receiveMessage);
		}
	}
	
	//On receiving a request, parse the request and reply with response
	protected void takeAction(String line) {
		String[] token = line.split(":");
		// <register> : <processName> : <ip> : <port>
		if (token.length == 3 && token[0].compareTo("register") == 0) {			
			nameToIpPort.put(token[1], receivePacket.getAddress().getHostAddress() + ":" + token[2]);
			System.out.println("Process -> " + token[1] + " with IP:Port "+ receivePacket.getAddress().getHostAddress() + ":" + token[2] + " registered");
		}
		// <lookup> : <processName>
		else if (token.length == 3 && token[0].compareTo("lookup") == 0) {	
			String ipPort = nameToIpPort.get(token[1]);					
			String []replyIpPort = nameToIpPort.get(token[2]).split(":");
			assert(replyIpPort != null);	//Assuming that there is always IP and port registered before requesting lookup
			
			if (ipPort == null) {
				sendUDP("Error:Process has not registered with the Name Server:nameServerResponse:xxxx",replyIpPort[0], Integer.parseInt(replyIpPort[1]));
				System.out.println("Error: "+token[1]+" Process has not registered with the Name Server");
			}
			else {
				System.out.println("Lookup->"+ipPort+":"+token[1]+":nameServerResponse");
				sendUDP(ipPort+":"+token[1]+":nameServerResponse",replyIpPort[0], Integer.parseInt(replyIpPort[1]));
			}
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
			ex.printStackTrace();
			System.exit(1);	
		}	
	}	
}