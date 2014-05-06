import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
 * TravelAgent: Responsible for accepting User Input or Generating Automated Requests
 */

final public class TravelAgent extends UDPCommTravelAgency {
	
	//Travel agent can be either Automatic or Manual
	private final String mode;
	//Cache the address of ReservationService
	private String reservationServiceIp = null;
	private int reservationServicePort = 0;
	//Cache the NameServer IP and Port
	private final int nameServerPort;
	private final String nameServerIp;
	private final String[][] storedRequest = { { "VA","BNE","SYD", null,null,null,null}, {"VA","BNE","SYD","14","John","1291 1003 1233"},
												{ "QF","SYD","BNE", null,null,null,null}, {"VA","BNE","SYD","33","Xeon","3491 1003 3433"}}; 
	
	//Initialize Travel Agent
	public TravelAgent(int nameServerPort, String nameServerIp, String mode) throws Exception {
		super();
		this.nameServerIp = nameServerIp;
		this.nameServerPort = nameServerPort;
		registerNameServer("register:TravelAgent"+mode+":"+listeningPort,this.nameServerIp, this.nameServerPort);

		this.mode = mode;				
		if (mode.compareTo("manual")==0)
			while (true)
				getInput();
		else if (mode.compareTo("automatic")==0)
			while (true) {
				getAutoInput();
				try {				        
			            Thread.sleep(3 * 1000);	//Sleep 3 seconds
			    } catch (InterruptedException e) {
			     
			    }
			}		
	}
	
	//Generate the random hard-coded input
	private void getAutoInput() throws Exception {
		int i = (int)(Math.random() * storedRequest.length);		
		if (i % 2 == 0) {
			System.out.println("\n\nEnquiry -> Airline: Origin: Destination: ->"+storedRequest[i][0]+":"+storedRequest[i][1]+":"+storedRequest[i][2]);
			sendReceive(0,storedRequest[i][0],storedRequest[i][1],storedRequest[i][2],null,null,null);
		}
		else {			
			System.out.println("\n\nPurchase -> Airline: Origin: Destination: Flight: Price: Credit Card: -> "+storedRequest[i][0]+":"+storedRequest[i][1]+":"+storedRequest[i][2]+":"+storedRequest[i][3]+":"+storedRequest[i][4]+":"+storedRequest[i][5]);			
			sendReceive(1,storedRequest[i][0],storedRequest[i][1],storedRequest[i][2],storedRequest[i][3],storedRequest[i][4],storedRequest[i][5]);
		}
		
	}
	
	//Parse the response received from Reservation Service
	protected void takeAction(String line) {
		String[] tokens = line.split(":");
		//enquiryResponse : automatic|manual : FlightNumber : Arrival Time : Departure Time : Price : Flight Number : Price
		if (tokens.length <= 2 && tokens[0].compareTo("enquiryResponse") == 0) {
			System.out.println("\n\nNo Flights Found");
		}
		else if (tokens[0].compareTo("enquiryResponse") == 0){
			System.out.println("Flight Number \t Arrival Time \t Departure Time \t Price");
			for (int i = 2; i < tokens.length; i += 4) {
				System.out.println(tokens[i] +"\t\t"+ tokens[i+1] +"\t\t"+ tokens[i+2] +"\t\t\t"+ tokens[i+3]);
			}			
		} 
		//responsePurchase: manual|automatic: Ok|Error : Message
		else if (tokens[0].compareTo("responsePurchase") == 0) {			
				System.out.println(tokens[3]);   //Contains error message or successful booking message
		}
	}
	
	//Method responsible to send and receive reply to and from Reservation Service
	public void sendReceive(int enquiryOrPurchase, String preferredAirline, String originAirport, String destinationAirport, String flightnumber, String name, String creditCard) throws Exception {
		if (reservationServiceIp == null) {
		String[] ipPort = lookup("ReservationService:TravelAgent"+mode, nameServerIp, nameServerPort);		
		reservationServicePort = Integer.parseInt(ipPort[1]);
		reservationServiceIp = ipPort[0];
		}
		if (enquiryOrPurchase == 0) {	
			if (preferredAirline.compareTo("VA") != 0 && preferredAirline.compareTo("QF") != 0) {
				String receiveMessageOne = null, receiveMessageTwo = null;
				sendUDP("enquiry:"+mode+":"+originAirport+":"+destinationAirport+":VA", reservationServiceIp, reservationServicePort);
				while ((receiveMessageOne = receive(false,null)) == null);
				takeAction(receiveMessageOne);
				sendUDP("enquiry:"+mode+":"+originAirport+":"+destinationAirport+":QF", reservationServiceIp, reservationServicePort);
				while ((receiveMessageTwo = receive(false,null)) == null);
				takeAction(receiveMessageTwo);
			} else {
				//Single Request : Either to VA or QF
				sendUDP("enquiry:"+mode+":"+originAirport+":"+destinationAirport+":"+preferredAirline, reservationServiceIp, reservationServicePort);
				String receiveMessageOne = null;
				while ((receiveMessageOne = receive(false,null)) == null);
				takeAction(receiveMessageOne);				
			}
		} else {
				sendUDP("requestPurchase:"+mode+":"+preferredAirline+":"+flightnumber+":"+originAirport+":"+destinationAirport+":"+name+":"+creditCard, reservationServiceIp, reservationServicePort);
				String receiveMessage;
				while ((receiveMessage = receive(false,null)) == null);
				takeAction(receiveMessage);
		}
	}
	
	//Accept input from user : Ensure the input is not null
	private String readLine() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		while (true) {
		input = br.readLine();
			if (input.length() != 0 && !input.contains(":"))
				break;
			System.out.println("\nPlease enter a valid input");
		}
		return input;
	}
	
	//Print User-Interface
	public void getInput() throws Exception {
		String operation, originAirport, destinationAirport, preferredAirline;
		String airLine, flightNumber, name, creditCard;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n\nSelect Opertation-> (e)Enquiry (p)Purchase (Press only enter key to Exit):");	
		operation = br.readLine();
		switch (operation) {
			case "e": System.out.println("Origin Airport:");
					  originAirport = readLine();
					  System.out.println("Destination Airport:");
					  destinationAirport = readLine();
					  System.out.println("Preferred Airline (VA or QF or Hit Enter if no Preferrance):");
					  preferredAirline = br.readLine();
					  sendReceive(0, preferredAirline, originAirport, destinationAirport, null, null, null);
					  break;
			case "p": System.out.println("Airline (VA or QF):");
					  airLine = readLine();
			          System.out.println("FlightNumber:");
			          flightNumber = readLine();
			          System.out.println("Origin Airport:");
			          originAirport = readLine();
			          System.out.println("Destination Airport:");
			          destinationAirport = readLine();
			          System.out.println("Passenger Name:");
			          name = readLine();
			          System.out.println("Credit Card:");
			          creditCard = readLine();
			          sendReceive(1, airLine, originAirport, destinationAirport,flightNumber,name,creditCard);
				      break;
			default: System.exit(0);
		}		
	}
	
	//java TravelAgent <name_server port number> <name_server hostname> <automatic or manual>
	public static void main(String[] args) {
		try {			
			if (args.length != 3 || Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) > 65535 || !(args[2].compareTo("manual") == 0 || args[2].compareTo("automatic") == 0)) {
				throw new Exception();
			}
		} catch(Exception ex) {
			System.err.println("Invalid command line arguments for Travel Agent\n");
			System.exit(1);
		}
		
		try {
		new TravelAgent(Integer.parseInt(args[0]),args[1],args[2]);
		} catch (Exception e) {
			System.err.println("Error Communicating");
			System.exit(1);
		}		
	}
}