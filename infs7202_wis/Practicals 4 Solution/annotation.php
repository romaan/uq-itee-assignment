<?php
session_start();
$image = $_GET["image"];
if ($_POST['save'] == "Submit")
	{
	$file = fopen(substr($_POST["file"],0,strpos($_POST["file"],'.')).".txt","a+");
	fwrite($file,$_POST["tag"]."\r\n");
	fclose($file);
	$tag = "Tag Saved";
	}
?>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="gallery.css"/>
<title>Annotation</title>
<?php if (isset($tag)) 
{
?>
	<meta http-equiv="refresh" content="5;url=http://localhost/prac4">
<?php
}
?>
</head>
<body>
<img src="<?php echo $image;?>" alt="<?php echo $image;?>"/>
<?php
if (isset($tag)) 
{
	echo "<br/>".$tag;
	return;
}
if (isset($_SESSION['login']))
{
echo "<form action='annotation.php' method='post'><input type='text' name='tag'/><input type='hidden' name='file' value='".$image."'/>";
echo "<br/><input type='reset' value='Reset'/><input type='submit' name='save' value='Submit'/>";
echo "<br/>".$tag;
}
else 
{
echo "Please log in to get to this page";	
}
?>
</body>
</html>