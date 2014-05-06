<%@page import="java.sql.*" %> 
<%

class MyImage {
public Integer limit;
public Integer pointer;
	public MyImage(String dflt, String next, String previous, String current) {
		if (dflt != null) limit = Integer.parseInt(dflt); else limit = 3;

		if (next != null) pointer = Integer.parseInt(current) + limit;
		else if (previous != null) pointer = Integer.parseInt(current) - limit;
		else pointer = 0;
	}
	
	private ResultSet mysql_connect() {
		 String url = "jdbc:mysql://localhost:3306/";    //I have used my sql database
		    String dbName = "prac6";      //created database emp on mysql
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
		        rs =  s.executeQuery("select image_id, image_name, image_location from Gallery LIMIT "+pointer+", "+limit);
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
    		output+= "<img src='"+rs.getString("image_location")+rs.getString("image_name")+"' alt='Image' width='100px' height='100px' ondragstart='drag(event)' id='"+rs.getString("image_id")+"' />";
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

MyImage anImage = new MyImage(request.getParameter("default"),request.getParameter("next"),request.getParameter("previous"), request.getParameter("current"));
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Web Information System Practical 5 File 2</title>
<link rel="stylesheet" type="text/css" href="dynamicdragdrop.css"/>
<script type="text/javascript">
function allowDrop(ev)
{
 ev.preventDefault();
}

function drag(ev)
{
 ev.dataTransfer.setData("Text",ev.target.id);
}

function drop(ev)
{
var data=ev.dataTransfer.getData("Text");
var tmp = document.getElementById(data);

	if (ev.target.nodeName == "DIV")
	{
	 ev.target.appendChild(tmp);
	}
	else
	{
	ev.target.parentNode.appendChild(tmp);
	}
 ev.preventDefault();
 }
 
 function getList()
 {
	 var a = this.document.getElementById("selected").getElementsByTagName("img");;
	 var i;
	 for (i = 0; i < a.length; i++)
		 {
		 this.document.form1.ids.value += a[i].id;
		 if (i != a.length -1)
			 this.document.form1.ids.value += ",";
		 }
 }
 </script>
</head>
<body>
<h1>My Gallery</h1>
<hr/><br/>
<div style="width:600px; margin: 0 auto; text-align: center;">
	<form method="post" action="dynamicdragdrop.jsp">
   	  <input type="hidden" name="current" value="<%= anImage.pointer%>"/>
	  <input name="default" type="text" value="<%= anImage.limit%>" maxlength="2"/>
	  <input type="submit" name="setdefault" value="Set Default"/>
    </form>
</div>
<hr/><br/>
<div id="previous" style="float:left;">
<form name="previous" action="dynamicdragdrop.jsp" method="post">
	<input type="hidden" name="current" value="<%= anImage.pointer%>"/>
	<input type="hidden" name="default" value="<%= anImage.limit%>"/>
	<input type="submit" name="previous" value="Previous"/>
</form>
</div>

<div id="next" style="float:right;">
<form action="dynamicdragdrop.jsp" method="post">
	<input type="hidden" name="current" value="<%= anImage.pointer%>"/>
	<input type="hidden" name="default" value="<%= anImage.limit%>"/>
	<input type="submit" name="next" value="Next"/>
</form>
</div>

<table width="100%">
<tr><td width="60%"><h3>All Photos</h3></td><td width="40%"><h3>Selected Album</h3></td></tr>
</table>
<div id="maincontainer" style="width:100%; height: 700px;">
<div id="all" ondrop="drop(event)" ondragover="allowDrop(event)">
<%= anImage.displayImages() %>

</div>

<div id="selected" ondrop="drop(event)" ondragover="allowDrop(event)">

</div>
<div id="form" style="float:right">
<form name="form1" action="SaveIdTag.jsp" method="POST" onsubmit="getList()">
Tag: <input type="text" name="tags"/>
<input type="hidden" name="ids" value=""/>
<input type="submit" name="save" value="Submit"/>
</form>
</div>
</div>

</body>
</html>