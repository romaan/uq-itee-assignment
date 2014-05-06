/************************
User.java : Parent class of HomeManager. 
Responsible for user management.

*************************/
import org.avis.client.*;
abstract public class User extends FileOperation {
Elvin elvinRouter;
/*Who is the user: Below three arrays store this information*/
protected String[] userLocation = new String[2];
protected String[] userBPHeart = new String[2];
protected String[] userUI = new String[2];
/*Store the actual values*/
protected String[] userLocationValue = new String[2];
protected Integer[] userHeartValue = new Integer[2];
protected Integer[] userBPValue = new Integer[2];
protected String clockValue;
protected Integer temperatureValue;

	public User(String elvinURL) throws Exception{
	elvinRouter = new Elvin(elvinURL);
		for (int i = 0; i < 2; i++) {
		userLocation[i] = null;
		userBPHeart[i] = null;
		userUI[i] = null;	
		}		
	}
	
	/*Returns true or false based on atleast one user present*/
	protected boolean atleastOneUserPresent() {
		for (int i = 0; i < 2; i++) {
			if (userLocation[i] != null && userBPHeart[i] !=null && userUI[i] != null)
				return true;
		}
	return false;
	}
	
	protected int userPresent(String category,  String user) {
		for (int i = 0; i < 2; i++) {
			switch (category) {
				case "location": if (userLocation[i] != null && userLocation[i].equals(user)) return i; break;
				case "heartbp": if (userBPHeart[i] != null && userBPHeart[i].equals(user)) return i; break;
				case "userUI": if (userUI[i] != null && userUI[i].equals(user)) return i; break;
			}
		}
		return -1;
	}
	
	/*Add a new user*/
	protected boolean addUser(String category,String userName) {
		switch (category) {
			case "location": return location(userName);
			case "heartbp": return heartbp(userName);
			case "userUI": return userUI(userName); 
		}
		return false;
	}
	
	private boolean userUI(String userName) {
	int i;
		for (i = 0; i < 2; i++) 
		 if (userUI[i] == null) {
			userUI[i] = userName;
			return true;
		}
		return false;
	}
	
	private boolean heartbp(String userName) {
	int i;
		for (i = 0; i < 2; i++) 
		 if (userBPHeart[i] == null) {
			userBPHeart[i] = userName;
			return true;
			}
		return false;
	}
	
	private boolean location(String userName) {
	int i;
		for (i = 0; i < 2; i++) 
		 if (userLocation[i] == null) {
			userLocation[i] = userName;
			return true;
			}
		return false;
	}
	
	protected void notifyUserStart(String permit) {	
	for (int i = 0; i < 2; i++)
		if (userUI[i] != null)
		new MyNotification(elvinRouter, "process","userStart","user",userUI[i],"permit",permit);
	}

	protected boolean checkToStartSecondUser(String user) {
		for (int i = 0 ; i < 2; i++)
		if (userLocation[i] != null && userUI[i] != null & userBPHeart[i] != null)
		if (userLocation[i].equals(user) && userUI[i].equals(user) & userBPHeart[i].equals(user))
			return true;
		return false;
	}
	
	//Returns the User Home list as String
	protected String userHome()
	{
		int count = 0, j = 0;
		String userAtHome = "";
		for (int i = 0; i < 2; i++) 
		if (userLocationValue[i] != null && userLocationValue[i].equals("home")) {
			count++;
			userAtHome += userLocation[i];
		}
		if (count == 0)
			return "None";				
	return userAtHome;
	}
	
	//Returns the number of Users present at Home
	protected int userHomeCount()
	{
		int count = 0, j = 0;
		for (int i = 0; i < 2; i++) 
		if (userLocation[i] != null && userLocationValue[i] != null && userLocationValue[i].equals("home")) {
			count++;
		}				
	return count;
	}
}