<?php
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
		mysql_select_db("prac6", $mysql_pointer);
		$result = mysql_query("select image_id, image_name, image_location from Gallery LIMIT ".$this->pointer.", ".$this->limit);
		if (mysql_num_rows($result) < 1) {
			echo "No more images";
		}
		while ($row = mysql_fetch_array($result))
  		{
		  echo "<img src='".$row['image_location'].$row['image_name']."' alt='Image' width='100px' height='100px'/>" ;
		}		
}
}
$anImage = new Image();
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Practical 6</title>
<style type="text/css">
h1 {
	text-align:center;
}

#container {
	width: 740px;
	height: 700px;
	align:center;
	margin: 0 auto;
}
#image {
	width: 600px;
	height: 600px;
	border: 2px solid;
	float: left;
}
</style>
</head>

<body>
<h1>My Gallery</h1>
<hr/><br/>
<span style="width:600px; margin: 0 auto; text-align: center;">
	<form action="<?php echo $PHP_SELF; ?>" method="post">
   	  <input type="hidden" name="current" value="<?php echo $anImage->pointer;?>"/>
	  <input name="default" type="text" value="<?php echo $anImage->limit;?>" maxlength="2"/>
	  <input type="submit" name="setdefault" value="Set Default"/>
    </form>
</span>
<hr/><br/>
<div id="container" >
<div id="previous" style="float:left;">
<form name="previous" action="<?php echo $PHP_SELF;?>" method="post">
	<input type="hidden" name="current" value="<?php echo $anImage->pointer;?>"/>
	<input type="hidden" name="default" value="<?php echo $anImage->limit; ?>"/>
	<input type="submit" name="previous" value="Previous"/>
</form>
</div>
	<div id="image"><?php $anImage->displayImages();?></div>
<div id="next" style="float:right;">
<form action="<?php echo $PHP_SELF;?>" method="post">
	<input type="hidden" name="current" value="<?php echo $anImage->pointer;?>"/>
	<input type="hidden" name="default" value="<?php echo $anImage->limit; ?>"/>
	<input type="submit" name="next" value="Next"/>
</form>
</div>
</div>
</body>
</html>