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
for ($i = 1; $i <= 10; $i++)
echo "<a href='annotation.php?image=images/".$i.".jpeg&imageid=".$i."'><img src='images/".$i.".jpeg' alt='".$i."'></a>";
?>
</body>
</html>