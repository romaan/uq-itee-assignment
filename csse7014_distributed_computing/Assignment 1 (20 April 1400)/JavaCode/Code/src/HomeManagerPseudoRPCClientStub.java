/*
HomeManagerPseudoRPCClientStub.java : Client stub implementing the RPC call
*/
import org.avis.client.*;

final public class HomeManagerPseudoRPCClientStub implements Runnable {

protected Elvin elvinRouter;
protected Notification notifyClientOnTemperatureRequest;
protected NotificationListener receiveMessage;
protected Subscription subscribeLogRequest;
protected String [] result = null;
protected String [] arraySendAttributeValue;
protected String[] arrayReceiveAttributeValue;

	public HomeManagerPseudoRPCClientStub(String server, String responseString) {
		try {
			elvinRouter = new Elvin(server);
			 subscribeLogRequest = elvinRouter.subscribe(responseString);
		} catch (Exception e) {
		System.err.println("Error occured in HomeManagerPseudoRPCClientStub elvin initialization");
		e.printStackTrace();
		}
	}
	
	public void messageSendParameters(String ... parameters) {
	arraySendAttributeValue = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++)
			arraySendAttributeValue[i] = parameters[i];
	}
	
	public void messageReceiveParameters(String ... parameters) {
	arrayReceiveAttributeValue = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++)
			arrayReceiveAttributeValue[i] = parameters[i];
	}
	
	public void messageSend() {
		try{
			notifyClientOnTemperatureRequest = new Notification();
			for (int i = 0; i < arraySendAttributeValue.length; i+=2)
		    	notifyClientOnTemperatureRequest.set(arraySendAttributeValue[i], arraySendAttributeValue[i+1]);   
			elvinRouter.send(notifyClientOnTemperatureRequest);
			// Wait for server to send the reply back, thus executing blocking behaviour
				synchronized(subscribeLogRequest) {
					subscribeLogRequest.wait();
				}
		} catch (Exception e) {
	    	    	e.printStackTrace();
		}
	}
	
	public void messageReceive() {
	result = new String[arrayReceiveAttributeValue.length];
	subscribeLogRequest.addListener(new NotificationListener() {
			public void notificationReceived(NotificationEvent e) {
				for (int i = 0; i < arrayReceiveAttributeValue.length; i++)
					result[i] = e.notification.getString(arrayReceiveAttributeValue[i]);
				synchronized(subscribeLogRequest) {
				subscribeLogRequest.notify();
				}
			}
		});
	}
	
	public void closeElvin() {
	elvinRouter.close();
	}
	
	//Thread implementation
	public void run() {
		messageReceive();
		messageSend();
		closeElvin();
	}
	
	public String[] getResult() {
		return result;
	}
	
	public static String[]  remoteMethod(String server, String method, String ... parameter) {
		switch(method) {
		case "temperatureLog": return TemperatureLog(server);
		case "readHealthWarningData": return HealthWarningLog(server, parameter[0], parameter[1], parameter[2]); //return HealthLogPseudoRPCClibreak;
		}
		return null;
	}
	
	//Health Warning Client Stub
	private static String[] HealthWarningLog(String server, String user, String startDate, String endDate) {
	HomeManagerPseudoRPCClientStub rpcHealthLog = new HomeManagerPseudoRPCClientStub(server,"health == 'logresponse'");	
	rpcHealthLog.messageReceiveParameters("text");
	rpcHealthLog.messageSendParameters("health","logrequest","user",user,"StartDay",startDate,"EndDay",endDate);
	try {
		Thread t = new Thread(rpcHealthLog);
		t.start();
		t.join();
		} catch (Exception e) {
		System.err.println("Error in join");
		e.printStackTrace();
		}
	return rpcHealthLog.getResult();
	}
	
	//Temperature client stub
	public static String[] TemperatureLog(String server)
	{
		HomeManagerPseudoRPCClientStub rpc = new HomeManagerPseudoRPCClientStub(server,"temperature == 'logresponse'");	
		rpc.messageReceiveParameters("text");
		rpc.messageSendParameters("temperature","logrequest");
		try {
		Thread t = new Thread(rpc);
		t.start();
		t.join();
		} catch (Exception e) {
		System.err.println("Error in join");
		e.printStackTrace();
		}
		return rpc.getResult();
		
	}
}