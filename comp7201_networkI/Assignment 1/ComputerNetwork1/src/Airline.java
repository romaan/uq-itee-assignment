import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/* 
* Airline.java : Accepts Airline Enquiry and Airticket Purchase request
*/
final public class Airline extends Listener {

	/* Flight class: Internal class to store all flight details*/
	class Flight {
		public String departureTime;
		public String arrivalTime;
		public int seat;
		public int price;
		
		public Flight(String departureTime, String arrivalTime, int seat, int price) {
			this.departureTime = departureTime;
			this.arrivalTime = arrivalTime;
			this.seat = seat;
			this.price = price;
		}
	}
	
	//Airline can be either VA or QF
	private final String airline;
	//To register Airline IP:Port and get IP:PORT details of other process
	private final RegisterLookup registerLookup;
	//Cache Process IP:Ports required for communication
	private String reservationIp = null;
	private int reservationPort = 0;
	private String bankIp = null;
	private int bankPort = 0;
	//Flights are stored as HashMap of <SOURCE:DESTINATION>,<FIGHT_NUMBER,(DEPARTURE TIME,ARRIVAL TIME,SEATS,PRICE)>>
	//so that it is easy to retrieve a particular flight details based on SOURCE:DESTINATION and FLIGHT_NUMBER
	private HashMap<String,HashMap<Integer,Flight>> flights;
	
	//Hard coded flight details of VA
	private void initializeHardCodeDataVA() {
		HashMap<Integer,Flight> aBNESYDflight = new HashMap<Integer,Flight>();
		aBNESYDflight.put(11,new Flight("05-00AM","06-00AM",20,125));
		aBNESYDflight.put(12,new Flight("08-00AM","09-00AM",32,150));
		aBNESYDflight.put(13,new Flight("11-00AM","12-00PM",27,115));
		aBNESYDflight.put(14,new Flight("01-00PM","03-00PM",5,110));
			
		HashMap<Integer,Flight> aSYDBNEflight = new HashMap<Integer,Flight>();
		aSYDBNEflight.put(21,new Flight("04-00AM","04-30AM",20,120));
		aSYDBNEflight.put(22,new Flight("07-30AM","09-00AM",32,105));
		aSYDBNEflight.put(23,new Flight("11-00AM","12-00PM",27,110));
		aSYDBNEflight.put(24,new Flight("01-00PM","03-00PM",12,125));
		flights = new HashMap<String,HashMap<Integer,Flight>>();
		flights.put("BNE:SYD", aBNESYDflight);		
		flights.put("SYD:BNE", aSYDBNEflight);		
	}
	
	//Hard coded flight details of QF
	private void initializeHardCodeDataQF() {
		HashMap<Integer,Flight> aBNESYDflight = new HashMap<Integer,Flight>();
		aBNESYDflight.put(31,new Flight("06-00PM","08-00PM",20,225));
		aBNESYDflight.put(32,new Flight("07-00PM","09-00AM",12,300));
		aBNESYDflight.put(33,new Flight("11-00AM","12-00PM",27,115));
		aBNESYDflight.put(34,new Flight("01-00PM","03-00PM",2,110));

		HashMap<Integer,Flight> aSYDBNEflight = new HashMap<Integer,Flight>();
		aSYDBNEflight.put(41,new Flight("01-00AM","04-30AM",20,120));
		aSYDBNEflight.put(42,new Flight("04-30AM","09-00AM",32,105));
		aSYDBNEflight.put(43,new Flight("12-00AM","12-00PM",27,110));
		aSYDBNEflight.put(44,new Flight("08-00PM","03-00PM",12,125));

		flights = new HashMap<String,HashMap<Integer,Flight>>();
		flights.put("BNE:SYD", aBNESYDflight);		
		flights.put("SYD:BNE", aSYDBNEflight);		
	}
	
	//Initialising an instance of Airline	
	public Airline(int nameServerPort, String nameServerIp, String airline) throws Exception {
		super();		
		this.airline = airline;
		if (this.airline.compareTo("QF") == 0) 
			initializeHardCodeDataQF();
		else
			initializeHardCodeDataVA();
				
		//Register with NameServer
		registerLookup = new RegisterLookup(nameServerPort, nameServerIp);				
		registerLookup.registerNameServer("register:Airline"+airline+":"+listeningPort+"\r\n");
		
		//Ready to accept incoming connections
		System.out.println("Airline "+this.airline+" waiting for incoming connections...");		
		while (true) {
			acceptConnection();			
			takeAction(in.readLine(),out);
		}
	}
	
	//When any incoming message is received, this method parses the string and takes required action
	protected void takeAction(String line, PrintWriter out) throws Exception {
		String[] temp1 = registerLookup.lookup("ReservationService");
		reservationIp = temp1[0];
		reservationPort = Integer.parseInt(temp1[1]);
		String[] tokens = line.split(":");
		
		//Receving Enquiry message: From ReservationService : Message -> enquiry: automatic|manual : origin_airport : destination_airport
		if (tokens.length == 4 && tokens[0].compareTo("enquiry")== 0) {
				String replyMessage = "enquiryResponse:"+tokens[1];
				HashMap<Integer,Flight> sendResponse = flights.get(tokens[2]+":"+tokens[3]);
				if (sendResponse != null) {
					for (Iterator<Integer> i = sendResponse.keySet().iterator(); i.hasNext();) {
						int flightNumber = (int) i.next();
						Flight aTemp = (Flight) sendResponse.get(flightNumber);
						replyMessage += ":" + flightNumber +":"+ aTemp.arrivalTime+":"+ aTemp.departureTime+":"+aTemp.price;
					}				
				}
				sendMessage(replyMessage, reservationIp, reservationPort);			
		//Receiving Purchase air ticket request: From ReservationServer : Message -> requestPurchase: automatic|manual : VA|QF : flight_number : origin_airport : destination_airport : customer_name: credit_card
		} else if (tokens.length == 8 && tokens[0].compareTo("requestPurchase") == 0 && (tokens[2].compareTo("VA") == 0 || tokens[2].compareTo("QF") == 0)) {		
				HashMap<Integer,Flight> flightDetail = flights.get(tokens[4]+":"+tokens[5]);
				if (flightDetail == null) { 
					sendMessage("responsePurchase:"+tokens[1]+":Error:Cannot find any flights between the origin and destination airport", reservationIp, reservationPort);
					return;
				}
				Flight f = null;
				try 
				{
					f = flightDetail.get(Integer.parseInt(tokens[3]));
					if (f == null) {
						sendMessage("responsePurchase:"+tokens[1]+":Error:Could not find flight matching flight number", reservationIp, reservationPort);
						return;
					}
				} catch (Exception ex) {
					sendMessage("responsePurchase:"+tokens[1]+":Error:Could not find flight matching flight number", reservationIp, reservationPort);
					return;
				}
				if (f.seat == 0) {
					System.out.println("\n\nBooking of "+ tokens[6]+ " by  "+tokens[2]+" on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+" not successful. Seats left:"+f.seat);
					sendMessage("responsePurchase:"+tokens[1]+":Error:There are no more seats left on flight "+tokens[2], reservationIp, reservationPort);
					return;									
				} else {
					f.seat -= 1;
					System.out.println("\n\nBooking of "+ tokens[6]+ " by  "+tokens[2]+" on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+". Requesting Bank for transaction. Seats left:"+f.seat);
					String[] temp2 = registerLookup.lookup("Bank");
					bankIp = temp2[0]; 
					bankPort = Integer.parseInt(temp2[1]);
					sendMessage("airline"+this.airline+":"+line, bankIp, bankPort);
				}
		// Receiving Response of Purchase : From Bank : Message -> responsePurchase: automatic|manual : Ok|Error : Message
		} else if (tokens[0].compareTo("responsePurchase") == 0) {
			sendMessage(line, reservationIp, reservationPort);			
		}
	}
	
	//Airline <NameServer port number> <NameServer hostname> <QF or	VA>
	public static void main(String[] args) {
		try {
		if (args.length != 3  || Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) > 65535 || (args[2].compareTo("QF") !=0 &&  args[2].compareTo("VA") !=0)) 
		    throw new Exception();
		} catch (Exception ex) {
			System.err.println("Invalid command line arguments for Airline\n");
			System.exit(1);
		}				
		
		try {
			new Airline(Integer.parseInt(args[0]), args[1], args[2]);
		} catch (Exception e) {
			System.err.println("Error Communicating");
			System.exit(1);
		}		
	}

}