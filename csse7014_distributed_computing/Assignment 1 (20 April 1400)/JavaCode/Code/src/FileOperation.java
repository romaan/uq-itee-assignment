/***********************
FileOperation: Class reponsible for all file handling issues
				Grand Parent of HomeManager class that stores and retrieves logs from filesyste
************************/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;

public class FileOperation {

protected final String temperatureLog = "TemperatureLog.txt";
protected final String healthWarningLog = "HealthLog.txt";
protected FileWriter fileStream;
protected BufferedWriter bWriter;

	public FileOperation() {
		try
		{
		File aFile = new File(temperatureLog);
		File bFile = new File(healthWarningLog);
		aFile.delete();
		bFile.delete();
		aFile.createNewFile();
		bFile.createNewFile();
		
		} catch (Exception e) {
		System.err.println("Error in initializing file write");
		e.printStackTrace();
		}
	}

	//Read Temperature data and return as a single Single
	public String readTemperatureData() {
		  String completeLine = "";
		try{
		  FileInputStream fstream = new FileInputStream(temperatureLog);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine = "";
		  while ((strLine = br.readLine()) != null)   {
			  completeLine += "|" + strLine;
			  }
			  in.close();
		    }catch (Exception e){
		  System.err.println("Error: " + e.getMessage());
		  }
		  return completeLine;
	}
	
	//Read HealthWarningData Log file and return as a String
	public String readHealthWarningData(String user, String startDay, String endDay) {
	String completeLine = "";
	try{
		  FileInputStream fstream = new FileInputStream(healthWarningLog);
		  int start = getDay(startDay);
		  int end = getDay(endDay);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine = "";
		  while ((strLine = br.readLine()) != null)   {
			  String [] temp = strLine.split("\\ ");
			  if (warningLogFilter(user, start, end, temp[0], getDay(temp[1])) == true)
				  completeLine += "|" + strLine;			 
			  }
			  in.close();
		    }catch (Exception e){
		  System.err.println("Error: " + e.getMessage());
		  }
		  return completeLine;
	}
	
	//Write a line of Data to a given filename
	protected int writeData(String aLine, String fileName) {
		try {
		fileStream = new FileWriter(fileName , true);
		bWriter = new BufferedWriter(fileStream);
		bWriter.write(aLine+"\r\n");
		bWriter.close();
		return 0;
		}
		catch (Exception e) {
		System.err.println("Error in witing to file");
		e.printStackTrace();
		return 1;
		}
	}
	
	private boolean warningLogFilter(String user, int start, int end, String loggedUser, int loggedDay) {
	if (user.equals(loggedUser)) {
	int i = start;
		while (true) {
			if (loggedDay == i)
				return true;
			if (i == end)
				return false;
			i = (i + 1) % 7;
		}
	}
	return false;
	}
	
	private int getDay(String day) {
		String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		for (int i = 0 ; i < 7; i++)
			if (day.equals(days[i]))
				return i;
		return -1;
	}
}