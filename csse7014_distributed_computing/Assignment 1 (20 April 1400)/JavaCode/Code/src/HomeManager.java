/***********************************
HomeManager.java :
	(1) Receives data from all sensors
	(2) Sends health warnings to UI
	(3) Adjusts Air Conditioner
	(4) Keeps track of logs of temperature adjustment and health warnings
Execution: java HomeManager [ElvinURL]
***********************************/
import org.avis.client.*;
import java.util.Map;
import java.util.HashMap;

final public class HomeManager extends User{

int [] oldheartValue = new int[2];
int []  oldbpValue = new int[2];
Subscription[] register = new Subscription[3];
Subscription locationStatus, healthStatus, clockStatus, exitStatus, userUIClose, temperatureStatus;
HomeManagerPseudoRPCServerStub rpcServer;
int waitMode;

	public HomeManager(String elvinURL) throws Exception {
		super(elvinURL);		
		rpcServer = new HomeManagerPseudoRPCServerStub(elvinURL, this);
		waitMode = 0;
		waitForRegistration();
		startMonitoring();	
		while (true) {
		System.out.println("Temperature-"+temperatureValue+" Clock-"+clockValue+" Temperature WaitingMode:"+waitMode);
		if (userUI[0] != null)
		System.out.println("User 1: Location-"+userLocation[0]+" BP:"+userBPValue[0]+" Heart:"+userHeartValue[0]+" Location:"+userLocationValue[0]);
		if (userUI[1] != null)
		System.out.println("User 2: Location-"+userLocation[1]+" BP:"+userBPValue[1]+" Heart:"+userHeartValue[1]+" Location:"+userLocationValue[1]);
		waiting();
		}
	}

	//Waiting mode after temperature adjustment
	private void waiting() {
		try {
			Thread.sleep(1000);
			if (waitMode != 0)
				waitMode++;
			if (waitMode == 6)
			waitMode = 0;
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
	
	private void startMonitoring()  {
	
		try {
			//Clock sensor status 
			clockStatus  = elvinRouter.subscribe("clock == 'status'");
			clockStatus.addListener(new NotificationListener() {
				public void notificationReceived(NotificationEvent e) {
					String value = e.notification.getString("value");
					clockValue = value;
					}
				});		
				
			//Temperature sensore status
			temperatureStatus = elvinRouter.subscribe("temperature == 'status'");
			temperatureStatus.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {	
						if (waitMode != 0)
							return;
						temperatureValue = Integer.parseInt(e.notification.getString("value"));
						while (clockValue == null)
							continue;
						if (userHomeCount() == 0) {
						new MyNotification(elvinRouter, "temperature","mode","value","nonperiodic");
							if (temperatureValue > 28 || temperatureValue < 15) {
							waitMode = 1;
							writeData(clockValue+": Air Condition Adjusted", temperatureLog );
							writeData("Temperature: at "+temperatureValue+" degrees", temperatureLog );
							writeData("At home:NO ONE",temperatureLog );
							}
						}
						else if(temperatureValue != 22)
						{
						waitMode = 1;
						new MyNotification(elvinRouter, "temperature","mode","value","periodic");
						writeData(clockValue+": Air Condition Adjusted", temperatureLog );
						writeData("Temperature: at "+temperatureValue+" degrees", temperatureLog );
						writeData("At home:"+userHome(), temperatureLog );
						}
					}
						
				});
	
			//Location sensor status 
			locationStatus = elvinRouter.subscribe("location == 'status'");
			locationStatus.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {
					if (waitMode != 0)
						return;
					String locUser = e.notification.getString("user");
					String locValue = e.notification.getString("value");
					if (locValue.equals("home")) {		
						new MyNotification(elvinRouter,"temperature","mode","value","periodic");
					}
					if (userPresent("location",locUser) == -1)
						return;
					userLocationValue[userPresent("location",locUser)] = locValue;	
				}	
				});
			healthStatus = elvinRouter.subscribe("health == 'status'");
			healthStatus.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {
					String locUser = e.notification.getString("user");
					String strHeartRate = e.notification.getString("heartRate");
					int heartValue = Integer.parseInt(strHeartRate);
					String strBP = e.notification.getString("bp");
					String strClock = clockValue;
					int bpValue = Integer.parseInt(strBP);
					if (userPresent("heartbp",locUser) == -1)
						return;
						
					userHeartValue[userPresent("heartbp",locUser)] = heartValue;
					userBPValue[userPresent("heartbp",locUser)] = bpValue;
						
						if ((heartValue > 100 || bpValue > 140) && (oldheartValue[userPresent("heartbp",locUser)]  != heartValue || oldbpValue[userPresent("heartbp",locUser)] != bpValue) ) {
							try{
							System.out.println("Health Warning sent");
							new MyNotification(elvinRouter,"health", "warning","heartRate",strHeartRate,"bp",strBP,"username",locUser, "day",strClock);						
							writeData(locUser+" "+strClock+" "+strHeartRate+" "+strBP,healthWarningLog);
							} catch (Exception ex) {
							System.out.println("HomeManager Health Warning EXCEPTION");
							ex.printStackTrace();
							}
						}
						oldheartValue[userPresent("heartbp",locUser)] = heartValue;
						oldbpValue[userPresent("heartbp",locUser)] = bpValue;						
					}
				});
					
			//Close UserUI event
			userUIClose = elvinRouter.subscribe("userUI == 'close'");
			System.out.println("UserUI close Status Subscribed");
			userUIClose.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {
					System.out.println("UserUI close signal Received");
					String closingUser = e.notification.getString("user");					
							notifyUserClose(closingUser);
								try {
								Thread.sleep(1000);								
								} catch(Exception ex) {
								System.out.println("HomeManager: USERUI CLOSE EXCEPTION");
								ex.printStackTrace();
								}
							if (userPresent("userUI",closingUser) != -1)
							userUI[userPresent("userUI",closingUser)] = null;							
							if (userPresent("heartbp",closingUser) != -1)
							userBPHeart[userPresent("heartbp",closingUser)] = null;
							if (userPresent("location",closingUser) != -1)
							userLocation[userPresent("location",closingUser)] = null;
							if (atleastOneUserPresent() == false) {
							System.out.println("Sending Shutdown message");
							notifyShutdown();
								try {								
									Thread.sleep(2000);
									System.exit(0);
								} catch(Exception ex) {
								System.out.println("Shutdown Message EXIT EXCEPTION");
								ex.printStackTrace();
								}								
							}
					}																			
					
					private void notifyShutdown() {
					Notification message;
					try{
					message = new Notification();			
					message.set("sensor", "shutdown");     					
					elvinRouter.send(message);
					} catch (Exception e) {
					System.out.println("Sensor shutdown message EXCEPTION");
	    	    	e.printStackTrace();
					}
					}
					
					private void notifyUserClose(String userName) {
					Notification message;
					try{
					message = new Notification();			
					message.set("usersensor", "shutdown");     
					message.set("user",userName);					
					elvinRouter.send(message);
					} catch (Exception e) {
					System.out.println("User sensor message EXCEPTION");
	    	    	e.printStackTrace();
					}
					}
				});

				while (atleastOneUserPresent() == false) {
				continue;
				}			
				new MyNotification(elvinRouter,"process","start");	
			}
		catch (Exception ex) {
		System.out.println("CATCH EXCEPTION AFTER NOTIFY");
		ex.printStackTrace();
		}
	}

	//Wait for registration of UI, Location and HeartBP of a specific user
	private void waitForRegistration() throws Exception {

	register[0] = elvinRouter.subscribe("location == 'register'");
	register[1] = elvinRouter.subscribe("heartbp == 'register'");
	register[2] = elvinRouter.subscribe("userUI == 'register'");


		register[0].addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
			if (addUser("location",e.notification.getString("user"))) {
				System.out.println("Location Sensor for user "+e.notification.get("user")+" Registered");
				if (checkToStartSecondUser(e.notification.getString("user")))
					MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"),"yes");
			}
			else {
				System.out.println("Location sensor could not be started as two users are present");				
				MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"), "no");
				}
			}					
		});

		register[1].addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
			if (addUser("heartbp",e.notification.getString("user"))) {
				System.out.println("HeartBP Sensor for user "+e.notification.get("user")+" Registered");
				if (checkToStartSecondUser(e.notification.getString("user")))
					MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"),"yes");
			}
			else {
				System.out.println("HeartBP sensor could not be started as two users are present");	
				MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"),"no");
				}
			}
		});

		register[2].addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
			if (addUser("userUI",e.notification.getString("user"))) {
				System.out.println("UserUI for user "+e.notification.get("user")+" Registered");
				if (checkToStartSecondUser(e.notification.getString("user")))
					MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"),"yes");
			}
			else {
				System.out.println("UserUI could not be started as two users are present");	
				MyNotification.notifyUserStart(elvinRouter, e.notification.getString("user"),"no");
				}
			}		
		
		});
	}

	public static void main(String[] args) {
		if (args.length != 1) {
		System.out.println("Syntax: java HomeManager [ElvinURL]");
		System.exit(0);
		}

		System.out.println("\nHome Manager Started\n");
		try {
			new HomeManager(args[0]);
		} catch (Exception ex) {
		System.out.println("MAIN EXCEPTION CAUGHT");
		ex.printStackTrace();
		}
	}
}