<?php
$image = $_REQUEST["image"];
$imageid = $_REQUEST["imageid"];
	$mysql = mysql_connect("localhost","root","devrk");
	mysql_select_db("prac8");
	
	$tag_text_array = split(",",$_REQUEST["tag"]);
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
	
?>