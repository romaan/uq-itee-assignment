<?php
session_start();
if (isset($_SESSION['login']))
	{
	header('location: gallery.php');
	}
?>
<!DOCTYPE html>
<html>
<head>
<script language="javascript" src="Script.js"></script>
<title>Login Page</title>
</head>
<body onLoad="getCookie('prac9User')">
<h1>Login</h1>
<hr/>
<div id="userLogin" style="visibility:hidden;"></div> 
<form name="frmLogin">
Username:<input type="text" name="user" value=""/>
<br/><br/>
Password:<input type="password" name="password"/>
<br/><br/>
<input type="button" name="send" value="Login" onClick="verifyUser()"/>
<input type="checkbox" name="remember" value="yes"/>Remember Me
<br/>
<select name="period">
<option>1 minute</option>
<option>1 day</option>
</select>
</form>
<br/>
<input type="button" onClick="create()" value="Create User"/>
<div id="newUserMessage"></div>
</body>
</html>