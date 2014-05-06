<?php
class DbAccess {	
	function __construct() {
	$this->mysql = mysql_connect("localhost","root","devrk");
	mysql_select_db("prac9");
	}
	
	function __destruct() {
	mysql_close($this->myql);
	}
	
	function makeTag($image, $imageid, $tag)
	{
	$tag_text_array = split(",",$tag);
	if (count($tag_text_array))
	{
	$result = mysql_query("select tag_id from tag order by tag_id desc limit 1");
		if (mysql_num_rows($result) != 1)
		{
			$tag_id = 1;
		}
		else
		{
			$rows = mysql_fetch_array($result);
			$tag_id = $rows['tag_id'] + 1;
		}
	mysql_query("INSERT INTO album values ()");
	$result = mysql_query("select album_id from album order by album_id desc limit 1");
	$rows = mysql_fetch_array($result);
	$album_id = $rows['album_id'];
	mysql_query("INSERT INTO album_image values(".$album_id.",".$imageid.", 0 )");

		for ($i = 0; $i < count($tag_text_array); $i++)
		{
		mysql_query("insert into tag values(".($tag_id + $i).",'".$tag_text_array[$i]."')");
		mysql_query("insert into tag_album values(".$album_id.",".($tag_id + $i).")");
		}
	}

	$result = mysql_query("select tag_text from tag, tag_album, album_image where tag_album.album_id=album_image.album_id and tag.tag_id = tag_album.tag_id and album_image.image_id = ".$imageid." and album_image.isalbum = 0");
	while ($rows = mysql_fetch_array($result))
		{
		echo $rows['tag_text']."<br/>";
		}
	}
	
	function displayImages($parMove, $parLimit, $isalbum) 
	{
	$result = mysql_query("select image_id, image_name, image_path from image LIMIT ".$parMove.", ".$parLimit);
		if (mysql_num_rows($result) < 1) {
			echo "No more images";
		}
		
		if ($isalbum == "no")
		while ($row = mysql_fetch_array($result))
  		{
		echo "<a href='annotation.php?image=".$row['image_path'].$row['image_name']."&imageid=".$row['image_id']."'>";
		echo "<img id='".$row['image_id']."' src='".$row['image_path'].$row['image_name']."' alt='Image' width='100px' height='100px'/></a>".PHP_EOL ;
		}		
		else
		while ($row = mysql_fetch_array($result))
  		{
		echo "<img id='".$row['image_id']."' src='".$row['image_path'].$row['image_name']."' ondragstart='drag(event)' alt='Image' width='100px' height='100px'/>".PHP_EOL ;
		}	
	}
	
	
	function makeAlbumTag($tags, $ids) {		    
    if ($ids == null || $tags == null ) {
	  	echo "Please add atleast one tag and one image to album";
		return;
	}

	$image_id_array = split(",",$ids);
	$tag_text_array = split(",",$tags);

	$album_id = 1;
	$tag_id = 1;
	$result = mysql_query("select tag_id from tag order by tag_id desc limit 1");

	while ($rows = mysql_fetch_array($result))
		$tag_id = $rows['tag_id'] + 1;
					
	mysql_query("INSERT INTO album values ()");
	$result = mysql_query("select album_id from album order by album_id desc limit 1");
	$rows = mysql_fetch_array($result);
	$album_id = $rows['album_id'];
	mysql_query("INSERT INTO album_image values(".$album_id.",".$imageid.", 0 )");
	echo "Tag:";
	for ($i = 0; $i < count($tag_text_array); $i++)
		{
		echo $tag_text_array[$i]." , ";
		mysql_query("insert into tag values(".($tag_id + $i).",'".$tag_text_array[$i]."')");
		mysql_query("insert into tag_album values(".$album_id.",".($tag_id + $i).")");
		}       

	echo "<br/>Image ID:";
    for ($i = 0; $i < count($image_id_array); $i++)
		{	
		echo $image_id_array[$i]." , ";
       	mysql_query("insert into album_image values(".$album_id.",".$image_id_array[$i].",1)");
		}
     
   echo "<br/>Saved";
	}
}

class Image extends DbAccess {
	
	//Given Image ID returns the image table information
    function getImage($image_id) {
		$returnResult = NULL;
		$result = mysql_query("select image_id, image_name, image_path, image_time from image where image_id = $image_id");
		if ($row = mysql_fetch_array($result))
		{
			$returnResult = array($row['image_id'],$row['image_path'].$row['image_name'], $row['image_time']);
		}
		return $returnResult;
	}
	
	//Given the tag_text, returns the image_id, image_path, image_name and timestamp array
	function getImageWithTag($tag_text) {
		$result = mysql_query("select image_path, image_name, image.image_id, image.image_time from image, album_image, tag, tag_album where image.image_id=album_image.image_id and album_image.album_id=tag_album.album_id and tag.tag_id=tag_album.tag_id and tag_text='$tag_text' and album_image.isalbum=0");
		$returnResult = array();
		$i = 0;
		while ($row = mysql_fetch_array($result)) {
			$returnResult[$i] = array();
			$returnResult[$i][0] = $row['image_id'];
			$returnResult[$i][1] = $row['image_path'].$row['image_name'];
			$returnResult[$i][2] = $row['image_time'];
			$i++;
		}
		return $returnResult;		
	}
	
	//Given the image_id returns all the tags for that image
	function getImageWithTags($image_id) {
		$returnResult = array();
		$i = 0;
		$result = mysql_query("select distinct t.tag_id, tag_text from tag_album ta, album_image ai, image i, tag t where ta.album_id=ai.album_id and ai.image_id=i.image_id and ta.tag_id = t.tag_id and i.image_id=$image_id and ai.isalbum=0");
		while ($row = mysql_fetch_array($result))
		{
			$returnResult[$i] = $row['tag_text'];
			$i++;
		}
		return $returnResult;
	}
	
	function searchImage($tag_text) {
		$imageArray = $this->getImageWithTag($tag_text);
		if (count($imageArray) == 0)
			echo "<br/><b>No matching result</b>";
		for ($i = 0; $i < count($imageArray); $i++) {
			echo "<img id='".$imageArray[$i][0]."' src='".$imageArray[$i][1]."' alt='Image' width='100px' height='100px'/><br/>".PHP_EOL ;
			$tagArray = $this->getImageWithTags($imageArray[$i][0]);
				for ($j = 0; $j < count($tagArray); $j++)
					echo "<b>| ".$tagArray[$j]." |</b>";
				echo "<hr/>";
		}
	}
	
	
}

class Album extends DbAccess {
	
		function getAlbum($album_id) {
		$returnResult = array();
		$i = 0;
		$result = mysql_query("select image_id from album_image where isalbum=1 and album_id=$album_id");
			while ($row = mysql_fetch_array($result))
			{
				$returnResult[$i] = $row['image_id'];
				$i++;
			}
		return $returnResult;
		}
		
		//Returns album_id array given the tag text
		function getAlbumWithTag($tag_text) {
		$returnResult = array();
		$i = 0;
		$result = mysql_query("select distinct ai.album_id from album_image ai, tag_album ta, tag t where ai.album_id=ta.album_id and ai.isalbum=1 and ta.tag_id=t.tag_id and t.tag_text='$tag_text'");
		while ($row = mysql_fetch_array($result))
			{
				$returnResult[$i] = $row['album_id'];
				$i++;
			}
		return $returnResult;
		}

		function searchAlbum($tag_text) {		
		$albumArray = $this->getAlbumWithTag($tag_text);
			if (count($albumArray) == 0)
				echo "<b>No match found</b>";
			for ($i = 0; $i < count($albumArray); $i++) {
				echo "<b>Album:$albumArray[$i]</b><br/>";
			$imageArray = $this->getAlbum($albumArray[$i]);
				for ($j = 0; $j < count($imageArray); $j++) {	
					$anImage = new Image();
					$imageInfo = $anImage->getImage($imageArray[$j]);
				    echo "<img id='".$imageInfo[0]."' src='".$imageInfo[1]."' alt='Image' width='100px' height='100px'/>".PHP_EOL ;
				}
				echo "<br/><hr/>";
			}
	}
}

class User extends DbAccess{
	public function login($query, $password) {
		$result = mysql_query("select password from user where username = '".$query."'");
		$rows = mysql_fetch_array($result);
		if(mysql_num_rows($result) == 1 && md5($password) == $rows['password']) {
			session_start();
			$_SESSION["login"] = 1;
			return "Pass";
		}
		else
			return "Incorrect Username or Password";
	}
	
	public function logout() {
		session_start();
		unset($_SESSION['login']);	
	}
	
	public function createUser($username, $password) {
		mysql_query("insert into user(username, password) values('".$username."',md5('".$password."'))") or die("Unable to create user");
		echo "New User created";
	}
}

if (isset($_REQUEST["user"]) && isset($_REQUEST["password"])) {
$userVerify = new User();
echo $userVerify->login(mysql_real_escape_string($_REQUEST['user']), mysql_real_escape_string($_REQUEST["password"]));
}
else if (isset($_REQUEST["logout"])) {
$userLogout = new User();
$userLogout->logout();
}	
else if (isset($_REQUEST["username"]) && isset($_REQUEST["password"])) {
$userNew = new User();
$userNew->createUser(mysql_real_escape_string($_REQUEST["username"]),mysql_real_escape_string($_REQUEST["password"]));
}
else if (isset($_REQUEST["move"]) && isset($_REQUEST["limit"]) && isset($_REQUEST["album"]))  {
$picture = new DbAccess();
$picture->displayImages(mysql_real_escape_string($_REQUEST["move"]), mysql_real_escape_string($_REQUEST["limit"]), mysql_real_escape_string($_REQUEST["album"]));
}
else if (isset($_REQUEST["image"]) && isset($_REQUEST["imageid"])) {
$tag = new DbAccess();
$tag->makeTag(mysql_real_escape_string($_REQUEST["image"]),mysql_real_escape_string($_REQUEST["imageid"]), mysql_real_escape_string($_REQUEST["tag"]));
}
else if (isset($_REQUEST["searchimage"])) {
$searchImage = new Image();
$searchImage->searchImage(mysql_real_escape_string($_REQUEST["searchimage"]));
}	
else if (isset($_REQUEST["tags"]) && isset($_REQUEST["ids"])) {
$albumTag = new DbAccess();
$albumTag->makeAlbumTag(mysql_real_escape_string($_REQUEST["tags"]),mysql_real_escape_string($_REQUEST["ids"]));
}
else if (isset($_REQUEST["searchalbumimage"])) {
$searchAlbum = new Album();
$searchAlbum->searchAlbum(mysql_real_escape_string($_REQUEST["searchalbumimage"]));
}	
?>