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

xmlhttp.open("GET","myajax.php?"+str+"&tag="+this.document.form1.tag.value,true);
xmlhttp.send();
}

 </script>
<head>
<link rel="stylesheet" href="gallery.css"/>
<title>Annotation</title>
</head>
<body>
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
</body>
</html>