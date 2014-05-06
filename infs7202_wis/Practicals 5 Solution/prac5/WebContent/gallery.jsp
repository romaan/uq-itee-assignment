<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="prac5.css" />
<title>Web Information System Practical Five</title>
</head>
<body>
<h1>Web Information System Practical Five</h1>
<hr/>
<%! private int accessCount = 0; %> 
<h2>Accesses to page since server reboot: <%= ++accessCount %></h2> 
<hr/>
<ul id="nav">
<li><a href="http://www.uq.edu.au" target="_blank">Page 1</a></li>
<li><a href="http://www.google.com" target="_blank">Page 2</a></li>
<li><a href="http://www.yahoo.com" target="_blank">Page 3</a></li>
</ul>
<hr/>

<!-- Table starts-->
<table>

<tr>
<td><a href="images/1.jpg" target="_blank"><img src="images/1.jpg" alt="Intel"/></a><br/><b>Intel</b></td>
<td><a href="images/2.jpg" target="_blank"><img src="images/2.jpg" alt="Google"/></a><br/><b>Google</b></td>
<td><a href="images/3.jpg" target="_blank"><img src="images/3.jpg" alt="Father of computer"/></a><br/><b>Father of Computer</b></td>
</tr>

<tr>
<td><a href="images/4.jpg" target="_blank"><img src="images/4.jpg" alt="Apple"/></a><br/><b>Apple</b></td>
<td><a href="images/5.jpg" target="_blank"><img src="images/5.jpg" alt="Microsoft"/></a><br/><b>Microsoft</b></td>
<td><a href="images/6.jpg" target="_blank"><img src="images/6.jpg" alt="Linux"/></a><br/><b>Linux</b></td>
</tr>

</table>
<!--Table ends-->

<hr/>
</body>
</html>