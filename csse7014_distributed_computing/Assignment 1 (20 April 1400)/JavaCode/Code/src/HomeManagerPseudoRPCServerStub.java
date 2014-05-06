/*
HomeManagerPseudoRPCStubStub.java : Server stub implementing the RPC call
								:	Receives the message and calls the HomeManager function calls, gets the results and sends it to Client stub 
*/
import org.avis.client.*;

final public class HomeManagerPseudoRPCServerStub {

protected Elvin elvinRouter;
protected Notification temperatureLogResponse, healthLogResponse;
protected Subscription subscribeTemperatureLogRequest , subscribeHealthLogRequest;
protected HomeManager hm;

	public HomeManagerPseudoRPCServerStub(String server, HomeManager hm) {
		try {
		elvinRouter = new Elvin(server);
		this.hm = hm;
		subscribeTemperatureLogRequest = elvinRouter.subscribe("temperature == 'logrequest'");
		subscribeHealthLogRequest = elvinRouter.subscribe("health == 'logrequest'");
		receiveTemperatureSubscribeNotification();
		receiveHealthSubscribeNotification();
		}
		catch (Exception e) {
		System.err.println("Error in Server RPC Stub");
		e.printStackTrace();
		}
	}
	
	//Listening to messages from client stub and calling hm.readTemperatureData()
	public void receiveTemperatureSubscribeNotification() {
	subscribeTemperatureLogRequest.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
			String sendString = hm.readTemperatureData();
			new MyNotification(elvinRouter, "temperature","logresponse","text", hm.readTemperatureData());
			}
		});
	}
	
	//Listening to messages from client stub and calling hm.readHealthWarningData()
	public void receiveHealthSubscribeNotification() {
			subscribeHealthLogRequest.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
			new MyNotification(elvinRouter, "health","logresponse","text", hm.readHealthWarningData(e.notification.getString("user"), e.notification.getString("StartDay"), e.notification.getString("EndDay")));
			}
		});
	}


}