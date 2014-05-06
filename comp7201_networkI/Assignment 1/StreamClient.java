/*
 * StreamClient.java
 *
 * Created on 16 August 2005, 13:34
 */

/**
 *
 * @author  peters
 */
import java.io.*;
import java.net.*;
public class StreamClient {
    
    /** Creates a new instance of StreamClient */
    public StreamClient() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        try {
            // Connect to the process listening on
            // port 34567 on this host (localhost)
            clientSocket = new Socket("localhost", 34567);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // "true" means flush at end of line
            in = new BufferedReader(
		    new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create a buffered reader to read from standard input
        BufferedReader stdin = new BufferedReader(
		new InputStreamReader(System.in));
        
        String userInput;
        
        while((userInput = stdin.readLine()) != null) {
            // Send the line to server
            out.println(userInput);
            // Wait for a reply
            String reply = in.readLine();
            // Print out the reply to standard output
            System.out.println("Reply: " + reply);
        }
        
        // Close everything
        out.close();
        in.close();
        stdin.close();
        clientSocket.close();
    }
    
}
