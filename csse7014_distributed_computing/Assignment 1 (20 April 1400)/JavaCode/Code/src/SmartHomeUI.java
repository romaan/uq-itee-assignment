/***********************************
SmartHomeUI.java : Class reponsible to display the user interface.
Execution: java SmartHomeUI [ElvinURL]
***********************************/

import java.lang.System;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.avis.client.*;

final public class SmartHomeUI {

protected String elvinURL;
protected Elvin elvinRouter;
protected String user;
boolean waitEnter = false;
protected Subscription homeManagerStartMessage, healthWarningMessage, homeManagerInvalidMessage;

	//Enumerated Day: Helps verify if the user has entered the right input
	public enum Day {
		 Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
		}
	
	//Constructor	
	public SmartHomeUI(String elvinURL) throws Exception {
		this.elvinURL = elvinURL;
		System.out.println("\n\nWelcome to the Smart Home Monitoring System");
		System.out.print("Please enter your name:");
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(inStream);
		
		elvinRouter = new Elvin(elvinURL);
		
		homeManagerInvalidMessage = elvinRouter.subscribe("process == 'userStart'");
		homeManagerInvalidMessage.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
				if (e.notification.getString("user").equals(user) && e.notification.getString("permit").equals("no")) {
					System.out.println("Only two users permitted");
					try {System.in.read(); } catch (Exception ex)
					{ ex.printStackTrace();}
					elvinRouter.close();
					System.exit(0);
				}
				synchronized(homeManagerInvalidMessage) {
				homeManagerInvalidMessage.notify();
				}
				}
			});
			
		user = in.readLine();          
		new MyNotification(elvinRouter, "userUI","register","user",user);
		synchronized(homeManagerInvalidMessage) {
			homeManagerInvalidMessage.wait();
		}
		
		homeManagerStartMessage = elvinRouter.subscribe("all == 'start'");		
		homeManagerStartMessage.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
				System.out.println("All Sensors Activated");			
			}
		});

		healthWarningMessage = elvinRouter.subscribe("health == 'warning'");		
		healthWarningMessage.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {					
			//Warning messages will stop after enter is pressed 
				if (user.equals(e.notification.getString("username")) && waitEnter == false) {
					String heartRate = e.notification.getString("heartRate");
					String bpRate = e.notification.getString("bp");
					String day = e.notification.getString("day");
					System.out.println("\n"+day+": Health Warning!\n"+user+", your current heart rate and systolic blood pressure are "+heartRate+" bpm and "+bpRate+" mmHg, respectively");
					System.out.println("\nPlease relax and consider taking your medication.");			
					printScreen();
					}
				}
		});			
		mainMenu();
	}
	
	//Main Menu Screen PRINT SCREEN
	private void printScreen() {
	System.out.println("\n\nWelcome to the Smart Home Monitoring System");
	System.out.println("Please select an option:");
	System.out.println("1. View Log - Temperature adjustment");
	System.out.println("2. View Log - Health warnings for day(s)");
	System.out.println("E. Exit");
	}

	//MAIN MENU OPTIONS AND PROCESSING
	private void mainMenu() throws Exception {
	char opt;
	String tempStr;
		do {
		printScreen(); //Screen should be printed back after displaying warning message. NEED TO WORK
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(inStream);
		tempStr = in.readLine();
		opt = tempStr.length() > 0 ? tempStr.charAt(0) : 13;
		if (opt == 13 && waitEnter == true)
			waitEnter = false;
		else if (opt == 13)
			waitEnter = true;

			switch (opt) {
				case '1': /*RPC CALL*/
						String log[] = HomeManagerPseudoRPCClientStub.remoteMethod(elvinURL,"temperatureLog");
						for (int i = 0; i < log.length; i++)
						{
							String [] temp = log[i].split("\\|");
							if (temp.length <= 1)
							System.out.println("Log of temperature adjustment is empty");
							else 
							for (int j = 0; j < temp.length; j++) {
								System.out.println(temp[j]);
								if (j%3 == 0)
									System.out.println();
							}
						}
						break;
				case '2': 
						String[] dates = getStartEndDate();
						if (dates  != null) {
						/*RPC CALL*/
						String healthLog [] = HomeManagerPseudoRPCClientStub.remoteMethod(elvinURL,"readHealthWarningData",user,dates[0],dates[1]);
						for (int i = 0; i < healthLog.length; i++)
						{
							String [] temp = healthLog[i].split("\\|");
							if (temp.length <= 1 )
							System.out.println("Log of health warning is empty");
							else
							{
							String [][] collect = new String[temp.length][4];
							int collectCount = 0;
							System.out.println("Health Information of Warnings issued between "+dates[0]+" and "+dates[1] +" for "+ user+":");
							for (int j = 0; j < temp.length; j++) {
								String [] params = temp[j].split(" ");								
								if (params.length == 4) {
								for (i = 0; i < 4; i++)
								collect[collectCount][i] = params[i];
								collectCount++;
								}
							}
							printSorted(collect, collectCount, dates[0], dates[1]);
							}
						}
						}
						break;
				case 'E': System.out.println("SENDING EXIT SIGNAL");
							new MyNotification(elvinRouter, "userUI","close","user",user);
							break;
				case 13: break;		//Enter key is pressed
				default: System.out.println("Invalid command");
				}
		} while (opt != 'E');
	elvinRouter.close();
	}

	//Print the sorted dates health warning
	private void printSorted(String [][] params, int max, String startDate, String endDate) {
		Day start, end;
		try {
		start = Day.valueOf(startDate);
		end = Day.valueOf(endDate);
		Day d = start;
			do {
			printOrder(d, params, max); 
			if (d==end)
				break;
			d =  next(d);
			} while(true);
		} catch(Exception ex) {
		ex.printStackTrace();
		}
	}
	
	//Since enum does not allow incrementing in Java, this function returns the next day
	private Day next(Day d) {
		if (d == Day.Monday)
			return Day.Tuesday;
		else if (d == Day.Tuesday)
			return Day.Wednesday;
		else if (d == Day.Wednesday)
			return Day.Thursday;
		else if (d == Day.Thursday)
			return Day.Friday;
		else if (d == Day.Friday)
			return Day.Saturday;
		else if (d == Day.Saturday)
			return Day.Sunday;
		else
			return Day.Monday;
	}
	
	//Print the health log alerts of each day in order
	private void printOrder(Day d, String[][] params, int max) {
		try {
			boolean exist = false;
			for (int i = 0; i < max; i++)
			if (Day.valueOf(params[i][1]) == d) 
			exist = true;
			if (exist)
			System.out.println("\nThe recorded heart rate and systolic blood pressure on "+d+" were:");
			for (int j = 0; j < max; j++)
			if (Day.valueOf(params[j][1]) == d) 	
			System.out.print(params[j][2]+" "+params[j][3]+",");

		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	//Get the start and end date from the user, also throw exception if incorrect
	private String[] getStartEndDate() {
	String [] day = new String[2];
		try {
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(inStream);
			System.out.println("Please enter a start date:");			
			day[0] = in.readLine();
			System.out.println("Please enter an end date:");
			day[1] = in.readLine();
			Day.valueOf(day[0]);
			Day.valueOf(day[1]);
		} catch (Exception e) {
		System.out.println("Day must be a day of the week in full with first Letter capitalised (e.g., Monday)");
		return null;
		}
		return day;
	}
	
	//Main function, entry point of the program
	public static void main(String[] args) {
		if (args.length != 1) {
		System.out.println("Syntax: java SmartHomeUI [ElvinURL]");
		System.exit(0);
		}
		
		try {
			new SmartHomeUI(args[0]);
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
}