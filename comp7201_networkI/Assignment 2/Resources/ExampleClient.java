import java.net.*;
import java.io.*;

/*
  ExampleClient.java

  The ExampleClient takes a single command line parameter (the port of the server process on the localhost to 
  which it should send UDP packets). The ExampleClient then sends a test string 10 times to the server. Between 
  each send the ExampleClient rests for 1 second. The process then exits. 	

  No error checking is done in the program.

  Written by Ryan Wishart on the 22/9/2006

*/


public class ExampleClient{

	public static void main (String [] args) throws Exception{

		if(args.length != 1) {
			System.out.println("Usage: java " + "ExampleClient" + " " + "serverport");
			System.exit(-1);
		}
		//create a socket for sending UDP datagrams
		DatagramSocket socket = new DatagramSocket(); //dont care about the port we use

		//create a message to send to the Server
		String testString = "This is a message from the ExampleClient";

		//UDP packets are represented as DatagramPacket objects. We create one to send to the server.
		DatagramPacket packet = new DatagramPacket(testString.getBytes(), 
								testString.length(), 
								InetAddress.getLocalHost(), 
								Integer.parseInt(args[0])
							);

		//send the DatagramPacket packet to the server 10 times, sleeping for 1 sec in-between sends.
		for(int ii = 0; ii< 10; ii++){

			socket.send(packet);
			System.out.println("ExampleClient: sent message to ExampleServer");

			try{
				Thread.sleep(1000);

			} catch (Exception e){
	
				e.printStackTrace();
			}
		}
	}
}
