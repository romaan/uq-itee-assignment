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
			 String url = "jdbc:mysql://localhost:3306/";    //I have used my sql database
			    String dbName = "prac7";      //created database emp on mysql
			    String driver = "com.mysql.jdbc.Driver";
			    String userName = "root";
			    String password ="devrk";
			    Connection conn = null;
			    String output = "";
			    ResultSet rs = null;
			    try {
			        //Connecting to database
			        Class.forName(driver).newInstance();
			        conn = DriverManager.getConnection(url+dbName,userName,password);
			        java.sql.Statement s = conn.createStatement();
			        rs =  s.executeQuery("select tag_id from tags order by tag_id desc limit 1");
			        Integer tag_id = 1;
			        Integer album_id = 1;
			        	while (rs.next()) {
			        		tag_id = rs.getInt("tag_id") + 1;
			        	}
			        rs = s.executeQuery("select album_id from album order by album_id desc limit 1");
			        while (rs.next()) {
		        		album_id = rs.getInt("album_id") + 1;
		        	}
			        String[] idArray = ids.split(",");
			        String[] tagsArray = tags.split(",");
			        
			        if (idArray.length == tagsArray.length){ 
			        for (Integer i = 0; i < idArray.length; i++)
			        	{
			        
			        	s.executeUpdate("insert into tags values("+(tag_id+i)+",'"+tagsArray[i]+"')");
			        	s.executeUpdate("insert into image_tag values("+idArray[i]+","+(tag_id+i)+",NOW())");
			        	s.executeUpdate("insert into album values("+album_id+","+(tag_id+i)+")");
			        	}
			        }
			        else
			        	return "Number of Snaps should be equal to Number of tags";
			    	}
			        catch (Exception e)
			        {
			          System.out.println("Error:"+e.getMessage());
			        }
			    return "Saved";
		}
}
