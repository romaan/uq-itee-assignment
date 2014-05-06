/* 
* ReservationService: Class to accept requests from Agents and forward it to Airline also get response from Airline and forward it to appropriate agenst
*/

final public class ReservationService extends UDPCommTravelAgency {
	
	//Cache the NameServer IP and Port
	private final int nameServerPort;
	private final String nameServerIp;
	//Cache Process IP:Ports required for communication
	private String airlineVAIp = null;
	private int airlineVAPort = 0;
	private String airlineQFIp = null;
	private int airlineQFPort = 0;
	private String travelAgentmanualIp = null;
	private int travelAgentmanualPort = 0;
	private String travelAgentautomaticIp = null;
	private int travelAgentautomaticPort = 0;
	
	//Constructor for reservation service
	public ReservationService(int nameServerPort, String nameServerIp) {
		super();		
		this.nameServerIp = nameServerIp;
		this.nameServerPort = nameServerPort;
		registerNameServer("register:ReservationService:"+listeningPort,this.nameServerIp,this.nameServerPort);
		System.out.println("ReservationService waiting for incoming connections...");
		
		try {
			while (true) {
				String message;					
				while ((message = receive(false, null)) == null);
				takeAction(message);
			}
		} catch (Exception e) {	
			System.err.println("Cannot listen on given port " + listeningPort);
			System.exit(1);
		}
	}
	
	protected void resolveAddress(String name) {	
		if (name.compareTo("TravelAgentmanual") == 0 && travelAgentmanualIp == null) {
		String[] temp1 = lookup("TravelAgentmanual:ReservationService", nameServerIp, nameServerPort);
		travelAgentmanualIp = temp1[0];
		travelAgentmanualPort = Integer.parseInt(temp1[1]);
		}
		else if (name.compareTo("TravelAgentautomatic") == 0 && travelAgentautomaticIp == null) {
		String[] temp2 = lookup("TravelAgentautomatic:ReservationService", nameServerIp, nameServerPort);
		travelAgentautomaticIp = temp2[0];
		travelAgentautomaticPort = Integer.parseInt(temp2[1]);
		}
		else if (name.compareTo("AirlineVA") == 0 && airlineVAIp == null) {
		String[] temp3 = lookup("AirlineVA:ReservationService", nameServerIp, nameServerPort);
		airlineVAIp = temp3[0];
		airlineVAPort = Integer.parseInt(temp3[1]);
		}
		else if (name.compareTo("AirlineQF") == 0 && airlineQFIp == null) {
		String[] temp4 = lookup("AirlineQF:ReservationService", nameServerIp, nameServerPort);
		airlineQFIp = temp4[0];
		airlineQFPort = Integer.parseInt(temp4[1]);
		}
	}
	
	protected void takeAction(String line) throws Exception {				
		System.out.println("\nMessage ->"+line);
		resolveAddress("TravelAgentautomatic");
		resolveAddress("TravelAgentmanual");
		resolveAddress("AirlineVA");
		resolveAddress("AirlineQF");
		String[] messageTokens = line.split(":");
		//Receive enquiry request from TravelAgent and forward to Airline				
		if (messageTokens.length == 5 && messageTokens[0].compareTo("enquiry") == 0) {					
				if (messageTokens[4].compareTo("VA") == 0) 	 {							
					sendUDP("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineVAIp, airlineVAPort);					
				}
				else if (messageTokens[4].compareTo("QF") == 0)  {					
					sendUDP("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineQFIp, airlineQFPort);
				}
		//Receive enquiryResponse from Airline and forward to Travel Agent
		} else if (messageTokens.length >= 2 && messageTokens[0].compareTo("enquiryResponse") == 0) { 
				if (messageTokens[1].compareTo("manual") == 0) { 						
					sendUDP(line, travelAgentmanualIp, travelAgentmanualPort);
				}
				else if (messageTokens[1].compareTo("automatic") ==0) {					
					sendUDP(line, travelAgentautomaticIp, travelAgentautomaticPort);
				}
		//Receive purchase request from Travel Agent and forward to Airline
		} else if (messageTokens.length == 8 && messageTokens[0].compareTo("requestPurchase") == 0 ) {		
			if (messageTokens[2].compareTo("VA") == 0)  {				
				sendUDP(line, airlineVAIp, airlineVAPort);
			}
			else if (messageTokens[2].compareTo("QF") == 0)  {				
				sendUDP(line, airlineQFIp, airlineQFPort);
			}
			else { //Error, invalid airline entered by user
				if (messageTokens[1].compareTo("manual") == 0) {					
					sendUDP("responsePurchase:XXXX:Error:Airline not found", travelAgentmanualIp,travelAgentmanualPort);
				}
				else if (messageTokens[1].compareTo("automatic") == 0) {
					resolveAddress("TravelAgentautomatic");
					sendUDP("responsePurchase:XXXX:Error:Airline not found", travelAgentautomaticIp,travelAgentautomaticPort);
				}
			}
		//Response of purchase from airline to be forwarded to Travel Agent
		} else if (messageTokens.length == 4 && messageTokens[0].compareTo("responsePurchase") == 0) {			
			if (messageTokens[1].compareTo("manual") == 0) 	{					
				sendUDP(line, travelAgentmanualIp,travelAgentmanualPort);
			}
			else if (messageTokens[1].compareTo("automatic") == 0) {				
				sendUDP(line, travelAgentautomaticIp, travelAgentautomaticPort);
			}
		}		
	}
	
	//ReservationService <NameServer port number> <NameServer host name>
	public static void main(String[] args) {
		try {
			if (args.length != 2 || Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) > 65535)  
			    throw new Exception();
		} catch (Exception ex) {
			System.err.println("Invalid command line arguments for Airline\n");
			System.exit(1);
		}				
			
		try {		
			new ReservationService(Integer.parseInt(args[0]),args[1]);
		} catch (Exception e) {
			System.err.println("Error Communicating");
			System.exit(1);
		}	
	}
}