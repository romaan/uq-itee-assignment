import exchange.BpheartrateHomemanagerPrx;
import exchange.BpheartrateHomemanagerPrxHelper;

final public class BPHeartRateSensor extends FileIceOperation {

private String user; 

	protected BPHeartRateSensor(String file) throws Exception {
		super(file);
		getUser(file,"bp.txt");
	}

	private void getUser(String file, String lastWord) {
	int lastIndex = file.lastIndexOf(lastWord);
	int firstIndex = file.lastIndexOf("/") + 1;
	user = file.substring(firstIndex,lastIndex);
	}
	
	@Override
	public int run(String[] args) {
			try {
			Ice.ObjectPrx publisher = iceStormSetup(args[0]);  //Sending Topic Name to iceSetup    	        
		    BpheartrateHomemanagerPrx aBpheartrateHomemanager = BpheartrateHomemanagerPrxHelper.uncheckedCast(publisher);
			
		    	while(true) {
		    		String[] str = getLine().split(",");
		    		int time = Integer.parseInt(str[1]);
		    		System.out.println("Current Heart Rate and Blood Pressure:"+str[0]+" for user:"+user+" Time:"+time);
					for (int i = 1; i<= time; i++) {
						System.out.println("Current Heart Rate and Blood Pressure:"+str[0]+" for user:"+user+" for time:"+time+" now:"+i);		
						aBpheartrateHomemanager.bpHeartrateData(str[0], user);					    	   
		    	    Thread.sleep(1000L);
					}
		    	}
		    } catch (InterruptedException ex) {
		        //Ignore
		    } catch(Ice.CommunicatorDestroyedException ex) {
		    	//Ignore
		    } catch (Exception ex) {
		      System.out.println("Exception :"+ex.getMessage());
		      ex.printStackTrace();
		      return 1;
		    }
		return 0;
	}

}
