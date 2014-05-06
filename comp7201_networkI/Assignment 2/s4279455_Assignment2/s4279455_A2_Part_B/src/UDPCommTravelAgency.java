import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;

/*Abstract class that defines the UDP Communication for every process*/
abstract public class UDPCommTravelAgency {
	
	protected DatagramSocket socket;
	protected DatagramPacket receivePacket, sendAckPacket;
	protected DatagramPacket sendPacket, receiveAckPacket;
	protected int listeningPort;
	protected Vector<String> sendMessage;
	protected Vector<String> receiveMessage;
	protected byte buffer[];
	protected Timer timer;
	
	protected UDPCommTravelAgency() {
		this(0);
	}
	
	protected UDPCommTravelAgency(int port) {
		sendMessage = new Vector<String>();
		receiveMessage = new Vector<String>();
		
		//Initialize our port to listen
		try {
			if (port == 0)
				socket = new DatagramSocket();
			else
				socket = new DatagramSocket(port);
			listeningPort = socket.getLocalPort();		
		} catch (SocketException e) {
			System.err.println("Socket already in use");
			System.exit(1);
		}	
	}
	
	protected String receive(boolean lookupRequestExpected, String lookupMatch) {
		String timestamp_message = null;
		String message = null;
		try {
			buffer = new byte[4096];
			receivePacket = new DatagramPacket(buffer, buffer.length);
			socket.receive(receivePacket);
			timestamp_message = new String(receivePacket.getData()).trim();
			String [] tempArray = timestamp_message.split("::");
			if (tempArray.length == 1)
				receiveACK(tempArray[0]);
			else if (tempArray.length == 2) {
				if (lookupRequestExpected && tempArray[1].split(":").length == 4 && tempArray[1].split(":")[3].equals("nameServerResponse") && tempArray[1].split(":")[2].equals(lookupMatch)) {
					message = tempArray[1];
					sendACK(tempArray[0]);
				} else if (tempArray[1].split(":").length != 4 || !tempArray[1].split(":")[3].equals("nameServerResponse")) {
					message = receiveUDP(tempArray[0], tempArray[1]);
					return message;
				}
				else {
					sendACK(tempArray[0]);
					return null;
				}
			}
			else {
				System.out.println("Invalid message format");
			}
		} catch (Exception ex) {
			System.err.println("Error in communication");
			System.exit(1);
		}		
		return message;
	}
	
	private String receiveUDP(String timeStamp, String message) {
		try {		
		if (receiveMessage.indexOf(timeStamp) != -1) {
			//System.out.println("Duplicate message discard:"+timeStamp+":"+message);
			sendACK(timeStamp);
		} else {
			receiveMessage.add(timeStamp);			
			//Store 10 message stamps
			if (receiveMessage.size() >= 15) {				
				receiveMessage.remove(receiveMessage.firstElement());
			}
			//System.out.println("Received UDP message:"+timeStamp+":"+message);
			sendACK(timeStamp);
			return message;
		}
		} catch (Exception ex) {
			System.err.println("Error in communication");
			System.exit(1);
		}
		return null;
	}
	
	private void sendACK(String timeStamp) throws Exception {		
		sendAckPacket = new DatagramPacket(timeStamp.getBytes(), timeStamp.length(), receivePacket.getAddress(), receivePacket.getPort());
		socket.send(sendAckPacket);
	}

	protected void sendUDP(String message, String ip, int port) {
		
		try {
		
		Long timeStamp = new Date().getTime();
		Integer additional = (int)(Math.random()*1000 + 1);
		timer = new Timer(true);
		ReRunTravelAgency rr = new ReRunTravelAgency(message, ip, port, timeStamp.toString() + additional.toString(),sendMessage,this);
		timer.schedule(rr, 500 );
		
		message = timeStamp + additional.toString()+"::"+message;
		
		sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName(ip), port);	
		double rand = 0;
			if ((rand = Math.random()) >= 0.5) {
				//System.out.println("Sending UDP Message:"+message);
				socket.send(sendPacket);
			} else if (rand >= 0.3){
				//System.out.println("Sending Two Identical UDP Message:"+message);
				socket.send(sendPacket);socket.send(sendPacket);
			}
			else {
				//System.out.println("UDP Message unsent:"+message);
			}
		} catch (Exception ex) {
			System.err.println("Error in communication");
			System.exit(1);
		}
	}
	
	private void receiveACK(String ackTimeStamp) throws Exception {
		//boolean aBool = sendAcks.remove(ackTimeStamp);
		sendMessage.remove(ackTimeStamp);
		//System.out.println("Received ACK:"+ ackTimeStamp+" Marked as Received First time within time:"+aBool);		
	}		
	
	protected abstract void takeAction(String line) throws Exception;
	
	//Registration process
	protected void registerNameServer(String registerMessage, String nameServerIp, int nameServerPort) {
		sendUDP(registerMessage, nameServerIp, nameServerPort);
		receive(false, null); //Waiting for ACK
	}
	
	//Lookup process
	protected String[] lookup(String processName, String nameServerIp, int nameServerPort) {		
		String reply = lookupNameServer("lookup:"+processName, nameServerIp, nameServerPort);
		String[] replyTokens = reply.split(":");
		if (replyTokens.length == 4 && replyTokens[3].equals("nameServerResponse") ) {
			if (replyTokens[0].compareTo("Error") == 0) {
				System.err.println(processName + " "+  replyTokens[1]);
				System.exit(1);
				return null;
			}
			//We are sure to get IP and PORT only if not raise exception
			assert(replyTokens.length == 4);
		return replyTokens;
		}	
		System.err.println("Error in communication");
		System.exit(1);
		return null;
	}
		
	//Lookup process communication
	private String lookupNameServer(String message, String nameServerIp, int nameServerPort) {
		String reply = "";
	       try {        	
	       	sendUDP(message,nameServerIp, nameServerPort);	   		
	       	while ((reply = receive(true, message.split(":")[1])) == null);		       
	       } catch (Exception e) {
	           System.err.println("Cannot connect to name server located at:"+nameServerIp+":"+nameServerPort);
	           System.exit(1);
	       }
	       return reply;
	}
}
