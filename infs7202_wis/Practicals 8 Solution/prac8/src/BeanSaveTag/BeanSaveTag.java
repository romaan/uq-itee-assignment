package BeanSaveTag;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

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
		
		public String write() {
			 String url = "jdbc:mysql://localhost:3306/";   
			    String dbName = "prac8";      
			    String driver = "com.mysql.jdbc.Driver";
			    String userName = "root";
			    String password ="devrk";
			    Connection conn = null;
	
			    ResultSet rs = null;

		        
			    try {
			        if (ids == null || tags == null )
			        	return "Please add atleast one tag and one image to album";
			        //Connecting to database
			        Class.forName(driver).newInstance();
			        conn = DriverManager.getConnection(url+dbName,userName,password);
			        java.sql.Statement s = conn.createStatement();
			        rs =  s.executeQuery("select tag_id from tag order by tag_id desc limit 1");
			        Integer tag_id = 1;
			        Integer album_id = 1;
			        	while (rs.next()) {
			        		tag_id = rs.getInt("tag_id") + 1;
			        	}
				    s.executeUpdate("insert into album values()");
			        rs = s.executeQuery("select album_id from album order by album_id desc limit 1");
			        while (rs.next()) {
		        		album_id = rs.getInt("album_id");
		        	}		
			        

			        String[] idArray = ids.split(",");
			        String[] tagsArray = tags.split(",");
			        
			        
			        for (Integer i = 0; i < idArray.length; i++)
			        	{		
			        	System.out.println("insert into album_image values("+album_id+","+idArray[i]+")");
			        	s.executeUpdate("insert into album_image values("+album_id+","+idArray[i]+",1)");
			        	}
			        for (Integer i = 0; i < tagsArray.length; i++)
			        	{
			        	s.executeUpdate("insert into tag values("+(tag_id+i)+",'"+tagsArray[i]+"')");
			        	s.executeUpdate("insert into tag_album values("+album_id+","+(tag_id+i)+")");
			        	}
			    	}
			        catch (Exception e)
			        {
			          System.out.println("Error:"+e.getMessage());
			  
			        }
			   
			    return "Saved";
		}
}
