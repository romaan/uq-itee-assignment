<%@page import="java.sql.*" %> 
<%
class MyImage {
public Integer limit;
public Integer pointer;
	public MyImage(String dflt, String move, String current) {
		if (dflt != null) limit = Integer.parseInt(dflt); else limit = 3;

		if (move.compareTo("next") == 0) pointer = Integer.parseInt(current) + limit;
		else if (move.compareTo("previous") == 0) pointer = Integer.parseInt(current) - limit;
		else pointer = 0;
	}
	
	private ResultSet mysql_connect() {
		 String url = "jdbc:mysql://localhost:3306/";    //I have used my sql database
		    String dbName = "prac8";   
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
		        if (pointer >= 0)
		        rs =  s.executeQuery("select image_id, image_name, image_path from image LIMIT "+pointer+", "+limit);
		        }
		        catch (Exception e)
		        {
		          System.out.println("Error:"+e.getMessage());
		        }
	return rs;
	}
	
	public String displayImages() 
	{
		String output = "";
		int count = 0;
		ResultSet rs = mysql_connect();
		try
		{
     	while (rs.next()) {
    		output+= "<img src='"+rs.getString("image_path")+rs.getString("image_name")+"' alt='Image' width='100px' height='100px' ondragstart='drag(event)' id='"+rs.getString("image_id")+"' />";
    		count++;
    	} 	
     	if (count == 0)
    			return "No more images";
		}
		catch (Exception ex) {
			return "No more images";
		}
     	return output;
	}
}

MyImage anImage = new MyImage(request.getParameter("default"),request.getParameter("move"), request.getParameter("current"));
%>
<%= anImage.displayImages()%>