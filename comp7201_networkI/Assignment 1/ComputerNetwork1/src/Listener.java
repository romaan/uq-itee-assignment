import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
* Listener.java: Parent class for Airline, Bank, TravelAgent, ReservationService and NameServer
* that contain methods to listen on a Port and send messages to other processes
*/

//Class Responsible to listen to incoming requests
abstract public class Listener {
	
	private ServerSocket serverSocket;	
	protected Socket connSocket;
	protected int listeningPort;
	protected PrintWriter out;
	protected BufferedReader in;
	
	//Constructor of the class, creating ServerSocket to listen incoming requests
	protected Listener(int preferredPort) {		
		try {
            serverSocket = new ServerSocket(preferredPort); 
    		//Get System Generated or Manually set Port number
            listeningPort = serverSocket.getLocalPort();
        } catch (Exception e) {
            System.err.println("Cannot listen on a given port " + listeningPort + "\n");
            System.exit(1);
        }		
	}
	
	//Overloaded constructor to create server socket with system generated port number
	protected Listener() {
		this(0);				
	}
	
	//Accept incoming connections
	protected void acceptConnection() throws Exception {       	
        	connSocket = serverSocket.accept();    		
        	out = new PrintWriter(connSocket.getOutputStream(), true);        	
        	in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
	}
	
	//Send message to Destination IP:Port 
	protected void sendMessage(String message, String destinationIp, int destinationPort)  {
		try {
		Socket clientSocket = new Socket(destinationIp, destinationPort);		
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(message);
        out.close();
        clientSocket.close();
		} catch (Exception ex) {
			System.err.println("Error communicating "+destinationIp+":"+destinationPort);
			ex.printStackTrace();
			System.exit(1);
		}
	}
		
	//Every Server Process listening has to take action for incoming messages
	protected abstract void takeAction(String line,PrintWriter out) throws Exception;
		

}
