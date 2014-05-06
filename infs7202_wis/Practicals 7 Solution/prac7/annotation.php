<?php
session_start();
$image = $_REQUEST["image"];
if (isset($_POST['save']))
	{
	$imageid = $_POST["imageid"];
	$mysql = mysql_connect("localhost","root","devrk");
	mysql_select_db("prac7");
	$result = mysql_query("select tag_id from tags order by tag_id desc limit 1");
	if (mysql_num_rows($result) != 1)
	{
		$tag_id = 1;
	}
	else
	{
		$rows = mysql_fetch_array($result);
		$tag_id = $rows['tag_id'] + 1;
	}
	$tag_text_array = split(",",$_POST["tag"]);
	for ($i = 0; $i < count($tag_text_array); $i++)
	{
	mysql_query("insert into tags values(".($tag_id + $i).",'".$tag_text_array[$i]."')");
	mysql_query("insert into image_tag values(".$imageid.",".($tag_id + $i).",NOW())");
	}
	
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
<meta http-equiv="refresh" content="5;url=http://localhost/prac7">
<?php
}
?>
</head>
<body>
<img src="<?php echo $image;?>"/>
<?php
if (isset($tag)) 
{
	echo "<br/>".$tag;
	return;
}
if (isset($_SESSION['login']))
{
echo "<form action='annotation.php' method='post'><input type='text' name='tag'/><input type='hidden' name='image' value='".$image."'/><input type='hidden' name='imageid' value='".$_GET['imageid']."'/>";
echo "<br/><input type='reset' value='Reset'/><input type='submit' name='save' value='Submit'/>";
echo "<br/>".$tag;
}
else 
{
echo "Please log in to get to this page FORM";	
}
?>
</body>
</html>