/**
  
  ExampleServer.java

  A simple network program that uses threads and UDP. The program reads data from the keyboard 
  and prints it to standard out (system.out in Java) using one thread. Another thread reads input from a datagram socket 
  and prints the data in any packets received on that socket to standard out. The program assumes that 
  the data in any received datagram packets will be in string form. 

  NOTE: the port on which to listen to for incoming UDP packets (DatagramPackets) should be specified on 
  the command line when starting ExampleServer (e.g., java ExampleServer 5000).

  written by Ryan Wishart 
  on 22/9/2006

*/


import java.io.*;
import java.net.*;

//class that will be run as a separate thread to monitor user input on Standard In (usually the keyboard i.e. System.in).  
class KeyboardThread extends Thread{

	BufferedReader reader;

	public KeyboardThread(){

		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	//this method MUST be defined for anything that extends class Thread.
	public void run(){


		try{
		
			//read from standard in and print to standard out
			String input = reader.readLine();

			while(input != null){

				if(input.contains("Bye!")){

					System.exit(0);
					
				}

				System.out.println("from keyboard: " + input);
				input = reader.readLine();
			}

		} catch (IOException e){

			e.printStackTrace();
		}
	}
}

public class ExampleServer{

	DatagramSocket socket; 
	DatagramPacket packet;

	//constructor takes the port to listen on as its parameter
	public ExampleServer(int port){

		try{

		//create a UDP socket to listen on 
		socket = new DatagramSocket(port);
		byte [] buffer = new byte[1024];

		//UDP packet that represents received UDP packets
		packet = new DatagramPacket(buffer, 1024);
		
		//constantly loop waiting for UDP packets, print out their data field to Standard Out
		while(true){
			
			socket.receive(packet);
			buffer = packet.getData();
			System.out.println("from ExampleClient: "+ new String(buffer));
		}

		} catch (Exception e){

			e.printStackTrace();
		}
	}

	//main method of the ExampleServer. Assumes that the port to listen for incoming UDP packets will 
 	//be passed on the commandline.
	public static void main (String [] args) throws IOException{
		if(args.length != 1) {
			System.out.println("Usage: java " + "ExampleServer" + " " + "serverport");
			System.exit(-1);
		}
		//create a thread to read from the keyboard
		KeyboardThread thread = new KeyboardThread();	
		thread.start(); //tell the JVM to execute KeyboardThread as a separate thread 
				//(the code in the run() method will be executed)

		//create an ExampleServer object to listen for incoming UDP packets and print them to standard out
		ExampleServer server = new ExampleServer(Integer.parseInt(args[0]));
	}
}
