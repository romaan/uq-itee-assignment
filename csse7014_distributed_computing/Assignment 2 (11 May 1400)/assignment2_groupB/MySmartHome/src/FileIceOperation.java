/************************************************
 * FileIceOperation.java
 * Initialize file read
 * Retrieve each line in the file
 * Setup ice storm proxy and publisher
 ************************************************/
import java.io.RandomAccessFile;

abstract public class FileIceOperation extends Ice.Application {

private RandomAccessFile filePointer;
final private String proxy = "SmartHome.Proxy";

	//Constructor: Called from its children-> TemperatureSensor.java or BPHearRateSensor.java
	protected FileIceOperation(String file) throws Exception {
		super();
		filePointer = new RandomAccessFile(file,"r");
	}
	
	//Read each line of the file repeatedly and return the String
	protected String getLine() throws Exception {
	String line;	
		if ((line = filePointer.readLine()) != null)
			return line;
		filePointer.seek(0);
		return filePointer.readLine();
	}
	
	//Setting up sensors to publish data
	protected Ice.ObjectPrx iceStormSetup(String topicName) {
		IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper.checkedCast(communicator().propertyToProxy(proxy));
	    IceStorm.TopicPrx topic = null;
	    Ice.ObjectPrx publisher;
	    
        if(manager == null) {
            System.err.println("Invalid proxy");
            System.exit(1);
        }
        
        try {
            topic = manager.retrieve(topicName);
        } catch(IceStorm.NoSuchTopic e) {
            try {
                topic = manager.create(topicName);
            } catch(IceStorm.TopicExists ex) {
                System.err.println(appName() + ": temporary failure, try again.");
            }
        } 
        
        publisher = (topic.getPublisher()).ice_oneway();
        return publisher;
	}
}
