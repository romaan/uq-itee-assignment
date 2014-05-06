<?php
session_start();
if (!isset($_SESSION['login']))
	{
	header('location: index.php');
	}
if ($_POST["logout"] == "Logout" )
{
unset($_SESSION['login']);
header('location: index.php');
}

class Image {
public $limit;
public $pointer;
public $msg;
function __construct() {
	if (isset($_POST["default"])) $this->limit = $_POST["default"]; else $this->limit = "3";
		
		if (isset($_POST["next"])) $this->pointer = $_POST["current"] + $this->limit;
		else if (isset($_POST["previous"])) $this->pointer = $_POST["current"] - $this->limit;
		else $this->pointer = "0";
}

function displayImages() 
{
		$mysql_pointer = mysql_connect("localhost","root","devrk");
		mysql_select_db("prac8", $mysql_pointer);
		$result = mysql_query("select image_id, image_name, image_path from image");// LIMIT ".$this->pointer.", ".$this->limit);
		if (mysql_num_rows($result) < 1) {
			echo "No more images";
		}
		while ($row = mysql_fetch_array($result))
  		{
		  echo "<a href='annotation.php?image=".$row['image_path'].$row['image_name']."&imageid=".$row['image_id']."'><img id='".$row['image_id']."' src='".$row['image_path'].$row['image_name']."' alt='Image' width='100px' height='100px'/></a>".PHP_EOL ;
		}		
}
}
$anImage = new Image();
?>
<!DOCTYPE html>
<html>
<head>
<title>Gallery</title>
<link rel="stylesheet" href="gallery.css"/>
</head>
<body>
<h1>My Gallery</h1>
<hr/>
<div style="float:right">
	<form name="frmLogout" action="gallery.php" method="post">
	<input type="submit" name="logout" value="Logout"/>
	</form>
</div>
<br/>
<hr/>
<?php

$anImage->displayImages();
?>
</body>
</html>