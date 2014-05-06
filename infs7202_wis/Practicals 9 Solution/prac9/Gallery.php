<?php
session_start();
if (!isset($_SESSION['login']))
	{
	header('location: index.php');
	}
?>
<!DOCTYPE html>
<html>
<head>
<script language="javascript" src="Script.js"></script>
<title>Gallery</title>
<link rel="stylesheet" href="Mystyle.css"/>
</head>
<body onLoad="showImage('default','no');">
<h1>My Gallery</h1>
<hr/>
<div style="text-align:center; float: left; border:2px solid gray"><table border="2"><tr><td><a href="Gallery.php">Gallery</a></td><td><a href="Album.php">Album</a></td><td><a href="Search.php">Search Image</a></td><td><a href="SearchAlbum.php">Search Album</a></td><td><a href="Upload.php">Upload</a></td></tr></table></div>
<div style="float:right;">
	<input type="button" name="logout" value="Logout" onClick="logout();"/>
</div>
<br/>
<div style="text-align: center;">Set Default Limit:<input type="text" id="limit" name="limit" value="3"/><input type="button" name="setdefault" value="Default" onClick="showImage('default','no');"/></div>
<hr/>
<div style="text-align:center;">
<div style="float:left;"><input type="button" name="move" value="Previous" onClick="showImage('previous','no');"/></div>
<div id = "image">

</div>
<div style="float: left;"><input type="button" name="move" value="Next" onClick="showImage('next','no');"/></div>
<div id="logoutinfo"></div>
</body>

</html>