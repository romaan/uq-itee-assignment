<?php
session_start();
if (!isset($_SESSION['login']))
	{
	header('location: index.php');
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Web Information System Practical 9</title>
<link rel="stylesheet" href="Mystyle.css"/>
<script language="javascript" src="Script.js"></script>
</head>
<body onLoad="showImage('nothing','yes');">
<h1>Album</h1>
<hr/>
<div style="text-align:center; float: left; border:2px solid gray"><table border="2"><tr><td><a href="Gallery.php">Gallery</a></td><td><a href="Album.php">Album</a></td><td><a href="Search.php">Search Image</a></td><td><a href="SearchAlbum.php">Search Album</a></td><td><a href="Upload.php">Upload</a></td></tr></table></div><br/>
<div style="float:right;">
	<input type="button" name="logout" value="Logout" onClick="logout();"/>
</div>
<br/>
<div style="width:600px; margin: 0 auto; text-align: center;">
	<form name="defaultform">
	  <input name="limit" id="limit" type="text" value="3" maxlength="2"/>
	  <input type="button" name="setdefault" value="Set Default" onClick="showImage('nothing','yes');"/>
    </form>
</div>

<hr/><br/>
<div id="previous" style="float:left;">
	<input type="button" name="previous" value="Previous"  onclick="showImage('previous','yes');"/>
</div>

<div id="next" style="float:right;">
	<input type="button" name="next" value="Next" onClick="showImage('next','yes')"/>
</div>

<table width="100%">
<tr><td width="60%"><h3>All Photos</h3></td><td width="40%"><h3>Selected Album</h3></td></tr>
</table>

<div id="maincontainer" style="width:100%; height: 700px;">
	<div id="image" ondrop="drop(event)" ondragover="allowDrop(event)"></div>
	<div id="selected" ondrop="drop(event)" ondragover="allowDrop(event)"></div>

	<div style="float:right">
	Tag: <input type="text" id="tags" name="tags"/>
	<input type="button" name="tagsave" value="Submit" onClick="getList();"/>
	</div>
    <div id="save" style="float:left; text-align: left;"></div>
</div>
<div id="logoutinfo"></div>
</body>
</html>