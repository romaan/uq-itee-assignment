<?php
$image = $_GET["img"];
?>
<html>
<head>
<title>Annotation Page</title>
</head>
<body>

<form name = "form1">
<img src="<?php echo $image;?>" style="width:200px; height:200px;" alt="Image"/>
<br/>
<input type="text" name="tag"/>
<input type ="submit" value = "Submit"/>
<input type = "reset" value = "Reset"/>
<br/>
</body>
</html>