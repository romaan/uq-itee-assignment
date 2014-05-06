/****************************************************************
Sensor.java
	(1) Sensors clock, temperature, users' bp heart and location
	(2) Sends messages to Home Manager
Execution: java Sensor [type] [predefined-data-file] [ElvinURL]
		type -> "temperature" | "BP" | "location" | "clock"
*****************************************************************/

import java.lang.System;
import org.avis.client.*;
import java.io.RandomAccessFile;
import java.io.File;

final public class Sensor  {

Byte temperature;	
Float bp, heartRate;
String user;
RandomAccessFile filePointer;
String temperatureMode = null;
boolean startMessage;
protected Subscription homeManagerStartMessage, homeManagerExitMessage, temperatureModeMessage;
protected Elvin elvinRouter;

	//Initialize the appropriate sensor
	public Sensor(String type, String predefinedDataFile, String elvinURL) throws Exception {
		elvinRouter = new Elvin(elvinURL);							
		File file = new File(predefinedDataFile);
		filePointer = new RandomAccessFile(file,"r");
		
			switch (type) {
			case "temperature": 
					homeManagerExitMessage = elvinRouter.subscribe("sensor == 'shutdown'");
					exitGracefully();	
					temperatureModeMessage = elvinRouter.subscribe("temperature == 'mode'");
					temperatureModeMessage.addListener(new NotificationListener() {
						public void notificationReceived(NotificationEvent e) {
						temperatureMode = e.notification.getString("value");
						}
					});					
					senseTemperature();
					break;
			case "clock": 
					homeManagerExitMessage = elvinRouter.subscribe("sensor == 'shutdown'");
					exitGracefully();									
					senseClock();
					break;
			case "BP": getUser(predefinedDataFile,"bp.txt");
					homeManagerExitMessage = elvinRouter.subscribe("usersensor == 'shutdown'");
					exitUserSensorGracefully();
					homeManagerStartMessage = elvinRouter.subscribe("process == 'userStart'");					
					homeManagerStartMessage.addListener(new NotificationListener() {
						public void notificationReceived(NotificationEvent e) {
								if ((e.notification.getString("user")).equals(user) && (e.notification.getString("permit")).equals("no")) {
								System.out.println("Sorry, two users already present");
								makewait(2000);
								System.exit(0);
								}
								else if ((e.notification.getString("user")).equals(user) && (e.notification.getString("permit")).equals("yes")) {
								System.out.println("Sensing Blood pressure and heart rate started");
								startMessage = true;
								}
							}						
						});	
					new MyNotification(elvinRouter, "heartbp","register","user",user);
					waitForStartMessage();
					senseBPHeart();						
				break;
			case "location": getUser(predefinedDataFile,"Location.txt");			
					homeManagerExitMessage = elvinRouter.subscribe("usersensor == 'shutdown'");
					exitUserSensorGracefully();
					homeManagerStartMessage = elvinRouter.subscribe("process == 'userStart'");								
					homeManagerStartMessage.addListener(new NotificationListener() {
						public void notificationReceived(NotificationEvent e) {
								if ((e.notification.getString("user")).equals(user) && (e.notification.getString("permit")).equals("no")) {
								System.out.println("Sorry, two users already present");
								makewait(2000);
								System.exit(0);
								}
								else if ((e.notification.getString("user")).equals(user) && (e.notification.getString("permit")).equals("yes")) {
								System.out.println("Sensing location started");
								startMessage = true;
								}
							}						
						});	
					new MyNotification(elvinRouter, "location","register","user",user);
					waitForStartMessage();
					senseLocation();
				break;
			}		

	}

	//Wait for User UI , BP Heart and Location sensor to be UP
	private void waitForStartMessage() {
		while (!startMessage)
			continue;
	}
	
	private void getUser(String predefinedDataFile, String lastWord) {
	int lastIndex = predefinedDataFile.lastIndexOf(lastWord);
	int firstIndex = predefinedDataFile.lastIndexOf("\\") + 1;
	user = predefinedDataFile.substring(firstIndex,lastIndex);
	}

	//Read each line of the file repeatedly and return the String
	private String getLine() throws Exception {
	String line;	
		if ((line = filePointer.readLine()) != null)
			return line;
		filePointer.seek(0);
		return filePointer.readLine();
	}

	//Temperature sensor with periodic and non periodic mode
	private void senseTemperature() throws Exception  {
		Thread.sleep(10000);
		while (true) {
		String aLine = getLine();
		String []str = aLine.split(",");
		int time = Integer.parseInt(str[1]);
		int count = 0;
		Integer temperature = Integer.parseInt(str[0]);
		int olderTemperature = 0;
		System.out.println("Current temperature:"+temperature+" for seconds:"+time);
			for (int i = 1; i<= time; i++) {
				if (temperatureMode == null || temperatureMode.equals("periodic")) {
					new MyNotification(elvinRouter, "temperature","status","value",temperature.toString());					
					System.out.println("Periodic Mode Sensor Message sent");
				}
				System.out.println("Current temperature:"+temperature+" for seconds:"+time+" now:"+i);
				makewait(1000);
			}
			if (temperatureMode != null && temperatureMode.equals("nonperiodic") && (temperature < 15 || temperature > 28) && olderTemperature != temperature) {
					new MyNotification(elvinRouter,"temperature","status", "value",temperature.toString());
					System.out.println("Non Periodic Mode Sensor Message Sent");
			}
			olderTemperature = temperature;
		}
	}

	//Sensor of clock
	private void senseClock() throws Exception {
		Thread.sleep(10000);
		while (true) {
		String aLine = getLine();
		String []str = aLine.split(",");
		int time = Integer.parseInt(str[1]);
		String day = str[0];
		System.out.println("Current Clock:"+day);
			for (int i = 1; i <= time; i++) {
			System.out.println("Clock message sent:"+i);
			new MyNotification(elvinRouter,"clock","status","value",day);
			makewait(1000);
			}					
		}
	}

	//Sensor of location
	private void senseLocation() throws Exception {	
		while (true) {
		String aLine = getLine();
		String []str = aLine.split(",");
		int time = Integer.parseInt(str[1]);
		String location = str[0];
		System.out.println("Current User:"+user+" location:"+location+" for seconds:"+time);
			for (int i = 1; i <= time; i++) {
			System.out.println("Location message sent:"+i);
			new MyNotification(elvinRouter,"location","status","value",location,"user",user);
			makewait(1000);
			}
		}
	}
	
	//Sensor of BP and Heart
	private void senseBPHeart() {
		try {
		while (true) {
		String aLine = getLine();
		String[] str = aLine.split(",");
		int time = Integer.parseInt(str[1]);
		String[] heartbpString = str[0].split("_");
		String heartRate = heartbpString[0];
		String bp = heartbpString[1];
		System.out.println("Current Heart Rate:"+heartRate+" Blood Pressure:"+bp+" for user:"+user+" Time:"+time);
			for (int i = 1; i <= time; i++) {
			System.out.println("Heart Rate and BP message sent");
			new MyNotification(elvinRouter,"health","status","heartRate",new String(heartRate),"bp",new String(bp),"user",user);
			Thread.sleep(1000);			
			}
		}
		} catch (Exception e) {
		e.printStackTrace();	
		}
	}

	//Exiting all the UIs
	private void exitGracefully() {
		try {		
				homeManagerExitMessage.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {
							elvinRouter.close();
							System.out.println("CLOSING ALL UI SIGNAL");
							System.exit(0);
							}
						});
			} catch (Exception ex) {
			ex.printStackTrace();
			}
	}
	
	//Exiting only a specific user UI - Location, Heart BP, SmartHomeUI
	private void exitUserSensorGracefully() {
			try {		
				homeManagerExitMessage.addListener(new NotificationListener() {
					public void notificationReceived(NotificationEvent e) {
							if ((e.notification.getString("user")).equals(user)) {
							elvinRouter.close();
							System.out.println("CLOSING USER SPECIFIC UI");
							System.exit(0);
							}
						}
					});
			} catch (Exception ex) {
			ex.printStackTrace();
			}
	}
	
	//Wait
	private void makewait(int millisec) {
	try {
			Thread.sleep(millisec);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//Static main function - Starting point of flow of execution
	public static void main(String[] args) {
		if (args.length != 3) {
		System.out.println("Syntax: java Sensor [type] [predefined-data-file] [ElvinURL]");
		System.exit(0);
		}
		try	{
			new Sensor(args[0],args[1],args[2]);	
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
}