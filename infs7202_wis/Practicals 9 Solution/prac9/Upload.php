<?php
session_start();
if (!isset($_SESSION['login']))
	{
	header('location: index.php');
	}
if (isset($_POST["submit"])) {
if (($_FILES["file"]["type"] == "image/gif")
|| ($_FILES["file"]["type"] == "image/jpeg")
|| ($_FILES["file"]["type"] == "image/pjpeg"))
  {
  if ($_FILES["file"]["error"] > 0)
    {
    echo "Return Code: " . $_FILES["file"]["error"] . "<br />";
    }
  else
    {
    $msg = "Upload: " . $_FILES["file"]["name"] . "<br />";
    $msg = $msg."Type: " . $_FILES["file"]["type"] . "<br />";
    $msg = $msg."Size: " . ($_FILES["file"]["size"] / 1024) . " Kb<br />";
    $msg = $msg."Temp file: " . $_FILES["file"]["tmp_name"] . "<br />";

    if (file_exists("./images/" . $_FILES["file"]["name"]))
      {
      $msg = $_FILES["file"]["name"] . " already exists. ";
      }
    else
      {
      move_uploaded_file($_FILES["file"]["tmp_name"], "./images/" . $_FILES["file"]["name"]);
	  copy("./images/" . $_FILES["file"]["name"],"../../jee/prac9/WebContent/images/". $_FILES["file"]["name"]);
      $msg = $msg."Stored in: " . "images/" . $_FILES["file"]["name"];
	  $mysql = mysql_connect("localhost","root","devrk");
	  mysql_select_db("prac9");
	  mysql_query("insert into image(image_name, image_path, image_time) values('".$_FILES["file"]["name"]."','./images/', NOW())");
      }
    }
  }
else
  {
  $msg = "Invalid file";
  }
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Prac8 Upload</title>
<link rel="stylesheet" href="Mystyle.css"/>
<script language="javascript" src="Script.js"></script>
</head>

<body>
<h1>File Upload</h1>
<hr/>
<div style="text-align:center; float: left; border:2px solid gray"><table border="2"><tr><td><a href="Gallery.php">Gallery</a></td><td><a href="Album.php">Album</a></td><td><a href="Search.php">Search Image</a></td><td><a href="SearchAlbum.php">Search Album</a></td><td><a href="Upload.php">Upload</a></td></tr></table></div>

<div style="float:right;">
	<input type="button" name="logout" value="Logout" onClick="logout();"/>
</div>

<br/><br/>
<hr/>

<br/>
<form name="myWebForm" action="<?php echo $PHP_SELF;?>" method="post"  enctype="multipart/form-data">
<input type="file" name="file" id="file" /> 
<input type="submit" name="submit" value="Submit" />
</form>
<hr/>
<?php echo $msg;?>
<div id="logoutinfo"></div>
</body>
</html>