/*
 * StreamServer.java
 *
 * Created on 16 August 2005, 13:01
 */

/**
 *
 * @author  peters
 */
import java.net.*;
import java.io.*;
public class StreamServer {
    
    /** Creates a new instance of StreamServer */
    public StreamServer() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws IOException {
        ServerSocket serverSocket = null;
        try {
            // listen on port 34567
            serverSocket = new ServerSocket(34567);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket connSocket = null;
        while(true) {
	    try {
		// block, waiting for a conn. request
		connSocket = serverSocket.accept();
		// At this point, we have a connection
		System.out.println("Connection accepted from " +
		    connSocket.getInetAddress().getHostName() +
		    " port " + connSocket.getPort());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    // Now have a socket to use for communication
	    // Create a PrintWriter and BufferedReader for 
	    // interaction with our stream
	    // "true" means we flush the stream on newline
	    PrintWriter out = new PrintWriter(connSocket.getOutputStream(), 
		    true);
	    BufferedReader in = new BufferedReader(
		    new InputStreamReader(connSocket.getInputStream()));

	    // Commented this out so that it works with the StreamClient
	    // out.println("Hello - send me some strings");

	    String line;
	    
	    // Read a line from the stream - until the stream closes
	    while ((line=in.readLine()) != null) {
		// Client can close the connection by sending "Bye"
		if(line.equals("Bye")) {
		    break;
		}
		// Perform the job of the server - convert
		// string to uppercase and return it
		line = line.toUpperCase();
		out.println(line);
	    }
	    
	    System.out.println("Finishing up...");
	    out.close();
	    in.close();
	    connSocket.close();
        }
        // serverSocket.close();
    }
    
}
