<?php
session_start();
if (isset($_SESSION['login']))
{
	header('Location: gallery.php');
}
$aUser = $_POST["user"];
$aPassword = $_POST["password"];
$userCookie = $_COOKIE["user"];
if (($aUser == "INFS" && $aPassword == "3202") || ($aUser == "infs" && $aPassword == "7202"))
{
	if ($_POST["remember"] == "yes")
	{
		if ($_POST["period"] == "1 minute")
		{
			setcookie("user",$_POST["user"], time()+30);
		}
		else if ($_POST["period"] == "1 day")
		{
			setcookie("user",$_POST["user"], time()+(60*60*24));
		}
	}	
$_SESSION['login'] = 1;
header('Location: gallery.php');
}
?>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
</head>
<body>
<h1>Login</h1>
<hr/>
<?php if ($_POST["send"] == "Login") { echo "<b>Incorrect username or password</b>";}?>
<form name="frmLogin" action="<?php echo $PHP_SELF; ?>" method="post">
Username:<input type="text" name="user" value="<?php echo $userCookie?>"/>
<br/>
Password:<input type="password" name="password"/>
<br/>
<input type="submit" name="send" value="Login"/>
<br/>
<input type="checkbox" name="remember" value="yes"/>Remember Me
<br/>
<select name="period">
<option>1 minute</option>
<option>1 day</option>
</select>
</form>
</body>
</html>