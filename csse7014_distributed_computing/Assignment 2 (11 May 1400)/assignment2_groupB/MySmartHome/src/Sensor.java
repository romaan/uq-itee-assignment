/********************************************************************************
 * Sensor.java
 * Runs TemperatureSensor or BPHeartRareSensor
 * Reads the .pub files and creates instance of either Temperature or BPHeartRate Sensor
 ************************************************************************************/
import java.io.FileNotFoundException;

public class Sensor {

private TemperatureSensor aTemperatureSensor;
private BPHeartRateSensor aBPHeartRateSensor;
	
	//Constructor
	public Sensor(String type, String predefinedDataFile) throws Exception {
		String []myArgs = new String[1];
		if(type.equals("temperature")) {
			aTemperatureSensor = new TemperatureSensor(predefinedDataFile);
			myArgs[0] = "TemperatureSensor";
			aTemperatureSensor.main("TemperatureSensor", myArgs, "config.pub");
		}
		//
		else{
			if(type.equals("BP")){
				aBPHeartRateSensor = new BPHeartRateSensor(predefinedDataFile);
				myArgs[0] = "BpHeartrateSensor";
				aBPHeartRateSensor.main("BPHeartRateSensor", myArgs, "config.pub");
			}
			else{
				System.out.println("Invalid [type] : type -> temperature | BP");
			}

		}
	}

	public static void main(String[] args) {
		if (args.length != 2 ) {
			System.out.println("Syntax: java Sensor [type] [predefined-data-file]");
			System.exit(0);
			}
			try	{
				new Sensor(args[0],args[1]);	
			} catch (FileNotFoundException ex) { 
			System.out.println("File Not Found: "+ex.getMessage());
			}catch (Exception ex) {
			ex.printStackTrace();
			}
	}
}
