import java.util.TimerTask;
import java.util.Vector;
/*
 * ReRunTravelAgency: Re-transmit message if ACK is not received within timeout 
 */

public class ReRunTravelAgency extends TimerTask {

	String message;
	String ip;
	int port;
	Vector<String> sendAcks;
	String timeStamp;
	UDPCommTravelAgency aUDPCommTravelAgency;
	
	public ReRunTravelAgency(String message, String ip, int port, String timeStamp, Vector<String> sendAcks, UDPCommTravelAgency audpCommTravelAgency) {
		this.message = message;
		this.ip = ip;
		this.port = port;
		this.sendAcks = sendAcks;
		sendAcks.add(timeStamp);
		this.timeStamp = timeStamp;
		this.aUDPCommTravelAgency = audpCommTravelAgency;
	}

	@Override
	public void run() {		
		if (sendAcks.indexOf(timeStamp) != -1) {
			sendAcks.remove(timeStamp);
			//System.out.println("Resending UDP message:"+message);
			aUDPCommTravelAgency.sendUDP(message, ip, port);
		}
	}
}
