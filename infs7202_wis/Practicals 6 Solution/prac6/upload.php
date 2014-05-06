<?php
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

    if (file_exists("upload/" . $_FILES["file"]["name"]))
      {
      $msg = $_FILES["file"]["name"] . " already exists. ";
      }
    else
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],
      "./upload/" . $_FILES["file"]["name"]);
      $msg = $msg."Stored in: " . "upload/" . $_FILES["file"]["name"];
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
<title>Prac6 Upload</title>
</head>

<body>
<h1>File Upload</h1>
<hr/>
<br/>
<form name="myWebForm" action="<?php echo $PHP_SELF;?>" method="post"  enctype="multipart/form-data">
<input type="file" name="file" id="file" /> 
<input type="submit" name="submit" value="Submit" />
</form>
<hr/>
<?php echo $msg;?>
</body>
</html>