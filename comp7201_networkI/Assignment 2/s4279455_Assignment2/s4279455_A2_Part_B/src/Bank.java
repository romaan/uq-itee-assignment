
/*
* Bank.java: Accepts customer details and credit card number, generates, prints and replies with confirmation number
*/
public class Bank extends UDPCommTravelAgency {

	//Cache Process IP:Ports required for communication
	private String airlineVAIp = null;
	private int airlineVAPort = 0;
	private String airlineQFIp = null;
	private int airlineQFPort = 0;
	//Cache the NameServer IP and Port
	private final int nameServerPort;
	private final String nameServerIp;
	
	//Initializing an instance of Airline	
	public Bank(int nameServerPort, String nameServerIp) throws Exception {
		super();		
		//Register with NameServer
		this.nameServerIp = nameServerIp;
		this.nameServerPort = nameServerPort;
		registerNameServer("register:Bank:"+listeningPort, this.nameServerIp, this.nameServerPort);
		
		//Ready to accept incoming connections
		System.out.println("Bank waiting for incoming connections...");
		while (true) {
			String receiveMessage;
			while ((receiveMessage = receive(false, null)) == null);
			takeAction(receiveMessage);
		}

	}
	
	//When any incoming message is received, this method parses the string and takes required action
	protected void takeAction(String line) {		
		System.out.print("Message ::"+line);
		if (airlineVAIp == null) {
			String[] temp = lookup("AirlineVA:Bank",nameServerIp,nameServerPort);
			airlineVAIp = temp[0]; 
			airlineVAPort = Integer.parseInt(temp[1]);
		}
		if (airlineQFIp == null) {
			String[] temp = lookup("AirlineQF:Bank",nameServerIp,nameServerPort);
			airlineQFIp = temp[0]; 
			airlineQFPort = Integer.parseInt(temp[1]);
		}
		String[] tokens = line.split(":");
		int number = (int) (Math.random()*10000);
		//Receiving Purchase Transaction request: From Airline VA : Message -> airlineVA|arilineQF : requestPurchase: automatic|manual : VA|QF : flight_number : origin_airport : destination_airport : customer_name: credit_card
		if (tokens.length == 9 && tokens[0].compareTo("AirlineVA")==0) {
			System.out.println("\n\nSuccessful booking of "+ tokens[7]+ " by "+tokens[2]+" on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+". Confirmation number "+number);			
			sendUDP("responsePurchase:"+tokens[2]+":Ok:Successful booking of "+ tokens[7]+ " on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+". Confirmation number "+number, airlineVAIp, airlineVAPort);
		}
		//Receiving Purchase Transaction request: From Airline VA : Message -> airlineVA|arilineQF : requestPurchase: automatic|manual : VA|QF : flight_number : origin_airport : destination_airport : customer_name: credit_card
		else if (tokens.length == 9 && tokens[0].compareTo("AirlineQF") ==0) {
			System.out.println("\n\nSuccessful booking of "+ tokens[7]+ " by  "+tokens[2]+" on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+". Confirmation number "+number);			
			sendUDP("responsePurchase:"+tokens[2]+":Ok:Successful booking of "+ tokens[7]+ " on flight number "+ tokens[4] + " from "+tokens[5]+" to "+tokens[6]+" on "+tokens[0]+". Confirmation number "+number, airlineQFIp, airlineQFPort);
		}
	}
	
	//java Bank <NameServer port number> <NameServer hostname>
	public static void main(String[] args) {
		if (args.length != 2 || Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) > 65535) {
			System.err.println("Invalid command line arguments for Bank\n");
			System.exit(1);
		}		
		
		try {
			new Bank(Integer.parseInt(args[0]),args[1]);
		} catch (Exception e) {
			System.err.println("Error Communicating");
			System.exit(1);
		}	
	}

}