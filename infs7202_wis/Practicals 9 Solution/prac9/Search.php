<?php
session_start();
if (!isset($_SESSION['login']))
{
	header('location: index.php');
}
?>
<!DOCTYPE html>
<head>
<title>Search Image</title>
<link rel="stylesheet" href="Mystyle.css"/>
<script language="javascript" src="Script.js"></script>
</head>
<body>
<h1 align="center">Search Images</h1>
<hr/>

<div style="text-align:center; float: left; border:2px solid gray"><table border="2"><tr><td><a href="Gallery.php">Gallery</a></td><td><a href="Album.php">Album</a></td><td><a href="Search.php">Search Image</a></td><td><a href="SearchAlbum.php">Search Album</a></td><td><a href="Upload.php">Upload</a></td></tr></table></div>

<div style="float:right;">
	<input type="button" name="logout" value="Logout" onClick="logout();"/>
</div>

<div style="text-align: center;">Search<input type="text" id="searchtag" name="tag"/><input type="button" name="search" value="Search" onClick="searchimage();"/>
<br/><br/><hr/>
<div id="imageresult"></div>
<div id="logoutinfo"></div>
</body>
</html>