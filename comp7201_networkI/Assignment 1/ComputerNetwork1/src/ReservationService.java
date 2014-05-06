import java.io.PrintWriter;

/* 
* ReservationService: Class to accept requests from Agents and forward it to Airline also get response from Airline and forward it to appropriate agenst
*/

final public class ReservationService extends Listener{
	
	private final RegisterLookup registerLookup;
	private String airlineVAIp;
	private int airlineVAPort;
	private String airlineQFIp;
	private int airlineQFPort;
	private String travelAgentmanualIp = null;
	private int travelAgentmanualPort = 0;
	private String travelAgentautomaticIp = null;
	private int travelAgentautomaticPort = 0;
	
	public ReservationService(int nameServerPort, String nameServerIp) {
		super();		
		registerLookup = new RegisterLookup(nameServerPort, nameServerIp);		
		registerLookup.registerNameServer("register:ReservationService:"+listeningPort+"\r\n");		
		System.out.println("ReservationService waiting for incoming connections...");
		
		try {
			while (true) {
			acceptConnection();			
			takeAction(in.readLine(),out);
			}
		} catch (Exception e) {	
			System.out.println("Cannot listen on given port " + listeningPort);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	protected void resolveAddress(String name) {	
		if (name.compareTo("TravelAgentmanual") == 0) {
		String[] temp1 = registerLookup.lookup("TravelAgentmanual");
		travelAgentmanualIp = temp1[0];
		travelAgentmanualPort = Integer.parseInt(temp1[1]);
		}
		else if (name.compareTo("TravelAgentautomatic") == 0) {
		String[] temp2 = registerLookup.lookup("TravelAgentautomatic");
		travelAgentautomaticIp = temp2[0];
		travelAgentautomaticPort = Integer.parseInt(temp2[1]);
		}
		else if (name.compareTo("AirlineVA") == 0) {
		String[] temp3 = registerLookup.lookup("AirlineVA");
		airlineVAIp = temp3[0];
		airlineVAPort = Integer.parseInt(temp3[1]);
		}
		else if (name.compareTo("AirlineQF") == 0) {
		String[] temp4 = registerLookup.lookup("AirlineQF");
		airlineQFIp = temp4[0];
		airlineQFPort = Integer.parseInt(temp4[1]);
		}
	}
	
	protected void takeAction(String line, PrintWriter out) throws Exception {		
		System.out.println("\nMessage ->"+line);
		String[] messageTokens = line.split(":");
		//Receive enquiry request from TravelAgent and forward to Airline				
		if (messageTokens.length == 5 && messageTokens[0].compareTo("enquiry") == 0) {					
				if (messageTokens[4].compareTo("VA") == 0) 	 {		
					resolveAddress("AirlineVA");
					sendMessage("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineVAIp, airlineVAPort);					
				}
				else if (messageTokens[4].compareTo("QF") == 0)  {
					resolveAddress("AirlineQF");	
					sendMessage("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineQFIp, airlineQFPort);
				}
				else {
					resolveAddress("AirlineVA");
					resolveAddress("AirlineQF");
					sendMessage("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineVAIp, airlineVAPort);
			        sendMessage("enquiry:"+messageTokens[1]+":"+messageTokens[2]+":"+messageTokens[3], airlineQFIp, airlineQFPort);
				}
		//Receive enquiryResponse from Airline and forward to Travel Agent
		} else if (messageTokens.length >= 2 && messageTokens[0].compareTo("enquiryResponse") == 0) { 
				if (messageTokens[1].compareTo("manual") == 0) { 
					resolveAddress("TravelAgentmanual");					
					sendMessage(line, travelAgentmanualIp, travelAgentmanualPort);
				}
				else if (messageTokens[1].compareTo("automatic") ==0) {
					resolveAddress("TravelAgentautomatic");
					sendMessage(line, travelAgentautomaticIp, travelAgentautomaticPort);
				}
		//Receive purchase request from Travel Agent and forward to Airline
		} else if (messageTokens.length == 8 && messageTokens[0].compareTo("requestPurchase") == 0 ) {		
			if (messageTokens[2].compareTo("VA") == 0)  {
				resolveAddress("AirlineVA");
				sendMessage(line, airlineVAIp, airlineVAPort);
			}
			else if (messageTokens[2].compareTo("QF") == 0)  {
				resolveAddress("AirlineQF");
				sendMessage(line, airlineQFIp, airlineQFPort);
			}
			else { //Error, invalid airline entered by user
				if (messageTokens[1].compareTo("manual") == 0) {
					resolveAddress("TravelAgentmanual");
					sendMessage("responsePurchase:XXXX:Error:Airline not found", travelAgentmanualIp,travelAgentmanualPort);
				}
				else if (messageTokens[1].compareTo("automatic") == 0) {
					resolveAddress("TravelAgentautomatic");
					sendMessage("responsePurchase:XXXX:Error:Airline not found", travelAgentautomaticIp,travelAgentautomaticPort);
				}
			}
		//Respoinse of purchase from airline to be forwarded to Travel Agent
		} else if (messageTokens.length == 4 && messageTokens[0].compareTo("responsePurchase") == 0) {			
			if (messageTokens[1].compareTo("manual") == 0) 	{	
				resolveAddress("TravelAgentmanual");
				sendMessage(line, travelAgentmanualIp,travelAgentmanualPort);
			}
			else if (messageTokens[1].compareTo("automatic") == 0) {
				resolveAddress("TravelAgentautomatic");
				sendMessage(line, travelAgentautomaticIp, travelAgentautomaticPort);
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