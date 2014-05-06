/********************************
 * Temperature Sensor: Get the temperature sensor data
 * Send the data to Home Manager
 *********************************/
import java.lang.InterruptedException;

import exchange.TemperatureHomemanagerPrx;
import exchange.TemperatureHomemanagerPrxHelper;


final public class TemperatureSensor extends FileIceOperation {

	public TemperatureSensor(String file) throws Exception {
		super(file);
	}
	
	public int run(String[] args) {
		try {
		Ice.ObjectPrx publisher = iceStormSetup(args[0]);  //Sending Topic Name to iceSetup    	        
	    TemperatureHomemanagerPrx aTemperatureHomemanager = TemperatureHomemanagerPrxHelper.uncheckedCast(publisher);
		
	    	while(true) {
	    	String []str = getLine().split(",");
	    	int time = Integer.parseInt(str[1]);
	    	Integer temperature = Integer.parseInt(str[0]);
	    	System.out.println("Current temperature:"+temperature+" for seconds:"+time);
				for (int i = 1; i<= time; i++) {
				System.out.println("Current temperature:"+temperature+" for seconds:"+time+" now:"+i);			
				//Publishing the event
				aTemperatureHomemanager.temperatureData(temperature);					    	   
	    	    Thread.sleep(1000L);
				}
	    	}
	    } catch (InterruptedException ex) {
	        //Ignore
	    } catch(Ice.CommunicatorDestroyedException ex) {
	    	//Ignore
	    } catch (Exception ex) {
	      System.out.println("Exception :"+ex.getMessage());
	      return 1;
	    }
	    return 0;
	}
}