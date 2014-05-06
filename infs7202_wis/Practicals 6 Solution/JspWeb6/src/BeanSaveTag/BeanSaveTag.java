package BeanSaveTag;
import java.io.*;

public class BeanSaveTag {
	String ids;
	String tags;

		public void setIds(String val) {
		ids = val;
		}
		
		public String getIds() {
			return ids;
		}
		
		public void setTags(String val) {
			tags = val;
		}
		
		public String getTags() {
			return tags;
		}
		
		public void write() {
			 try{
				  // Create file 
				 System.out.println(System.getProperty("user.dir"));
				  FileWriter fstream = new FileWriter("album_annotation.txt",true);
				  BufferedWriter out = new BufferedWriter(fstream);
				  out.write(ids+" "+tags+"\r\n");
				  //Close the output stream
				  out.close();
				  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
				  } 
		}
}
