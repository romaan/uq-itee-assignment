/*Extending Notification Class*/
import org.avis.client.*;

final public class MyNotification  {

Elvin elvinInstance;
Notification aNotify;

	public MyNotification(Elvin elvinParameter, String ... attributevalue)
	{
	aNotify = new Notification();
	elvinInstance = elvinParameter;
	for (int i = 0; i < attributevalue.length; i+=2)
		aNotify.set(attributevalue[i], attributevalue[i+1]);
	send();
	}
		
	public void send() {
		try {
			elvinInstance.send(aNotify);
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
	
	public static void notifyUserStart(Elvin elvinRouter, String userName, String permit) {
			Notification message;
			try{
			message = new Notification();			
		    	message.set("process", "userStart");     
				message.set("user",userName);
				message.set("permit",permit);
		    	elvinRouter.send(message);
			} catch (Exception e) {
					System.out.println("NOTIFY USER START EXCEPTION");
	    	    	e.printStackTrace();
			}
		}
}