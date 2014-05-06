import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * ResisterLook.java: Lookup class for process to find other processes' IP address and Port Number
 */
final public class RegisterLookup {
	
	private final int nameServerPort;
	private final String nameServerIp;
	
	public RegisterLookup(int nameServerPort, String nameServerIp) {		
		this.nameServerPort = nameServerPort;
		this.nameServerIp = nameServerIp;
	}
	
	//Lookup process
	public String[] lookup(String processName) {
		String reply = lookupNameServer(processName);
		String[] replyTokens = reply.split(":");
		if (replyTokens.length == 2) {
			if (replyTokens[0].compareTo("Error") == 0) {
				System.err.println(processName + " "+  replyTokens[1]);
				System.exit(1);
				return null;
			}
			//We are sure to get IP and PORT only if not raise exception
			assert(replyTokens.length == 2);
		return replyTokens;
		}	
		System.exit(1);
		return null;
	}
	
	//Lookup process communication
	private String lookupNameServer(String processName) {
		Socket clientSocket = null;
		BufferedReader in = null; 
		PrintWriter out = null;
		String reply = "";
        try {        	
        	clientSocket = new Socket(nameServerIp, nameServerPort);
        	out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("lookup:"+ processName +"\r\n");  
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reply = in.readLine();            
            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Cannot connect to name server located at:"+nameServerIp+":"+nameServerPort);
            System.exit(1);
        }
        return reply;
	}
	
	//Registration process
	public void registerNameServer(String registerMessage) {
		Socket clientSocket = null;
        PrintWriter out = null;                
        try {        	
            clientSocket = new Socket(nameServerIp, nameServerPort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(registerMessage);
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Cannot connect to nameserver located at:"+nameServerIp+":"+nameServerPort);
            System.exit(1);
        }
	}
}
