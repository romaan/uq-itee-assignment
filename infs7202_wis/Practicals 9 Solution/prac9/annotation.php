<?php
session_start();
$image = $_REQUEST["image"];
$imageid = $_REQUEST["imageid"];
?>
<!DOCTYPE html>
<html>
<script type="text/javascript">

function showAjax(str)
{

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("content").innerHTML=xmlhttp.responseText;
    }
  };

xmlhttp.open("GET","AjaxCalls.php?"+str+"&tag="+this.document.form1.tag.value,true);
xmlhttp.send();
}

 </script>
<head>
<link rel="stylesheet" href="MyStyle.css"/>
<title>Annotation</title>
</head>
<body>
<h1>Annotation</h1>
<hr/>
<div style="text-align:center; float: left; border:2px solid gray"><table border="2"><tr><td><a href="Gallery.php">Gallery</a></td><td><a href="Album.php">Album</a></td><td><a href="Search.php">Search Image</a></td><td><a href="SearchAlbum.php">Search Album</a></td><td><a href="Upload.php">Upload</a></td></tr></table></div>
<br/><br/><hr/>
<img src="<?php echo $image;?>"/>
<?php
if (isset($_SESSION['login']))
{
echo "<div id='content'>";
echo "<form name='form1' action='annotation.php' method='post'><input type='text' name='tag'/>";
?>
<br/><input type='button' name='save' value='Submit' onclick='showAjax("image=<?php echo $image;?>&imageid=<?php echo $imageid;?>")' />
<?php
echo "</form><br/>".$tag;
echo "</div>";
}
else 
{
echo "Please log in to get to this page FORM";	
}
?>
<div id="logoutinfo"></div>
</body>
</html>