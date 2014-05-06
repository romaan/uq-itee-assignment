
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Web Information System Practical 5 File 2</title>
<link rel="stylesheet" href="dragdrop.css"/>
<script type="text/javascript">
function allowDrop(ev)
{
 ev.preventDefault();
}

function drag(ev)
{
 ev.dataTransfer.setData("Text",ev.target.id);
}

function drop(ev)
{
var data=ev.dataTransfer.getData("Text");
var tmp = document.getElementById(data);

	if (ev.target.nodeName == "DIV")
	{
	 ev.target.appendChild(tmp);
	}
	else
	{
	ev.target.parentNode.appendChild(tmp);
	}
 ev.preventDefault();
 }
 
 function getList()
 {
	 var a = this.document.getElementById("selected").getElementsByTagName("img");;
	 var i;
	 for (i = 0; i < a.length; i++)
		 {
		 this.document.form1.ids.value += a[i].id;
		 if (i != a.length -1)
			 this.document.form1.ids.value += ",";
		 }
 }
 </script>
</head>
<body>
<h1>Gallery</h1>
<hr/>
<table width="100%">
<tr><td width="60%"><h3>All Photos</h3></td><td width="40%"><h3>Selected Album</h3></td></tr>
</table>
<div id="maincontainer" style="width:100%; height: 700px;">
<div id="all" ondrop="drop(event)" ondragover="allowDrop(event)">
	<img src="images/1.jpg" draggable="true" ondragstart="drag(event)" id="1" />
	<img src="images/2.jpg" draggable="true" ondragstart="drag(event)" id="2" />
	<img src="images/3.jpg" draggable="true" ondragstart="drag(event)" id="3" />
	<img src="images/4.jpg" draggable="true" ondragstart="drag(event)" id="4" />
	<img src="images/5.jpg" draggable="true" ondragstart="drag(event)" id="5" />
	<img src="images/6.jpg" draggable="true" ondragstart="drag(event)" id="6" />
	<img src="images/7.jpg" draggable="true" ondragstart="drag(event)" id="7" />
</div>

<div id="selected" ondrop="drop(event)" ondragover="allowDrop(event)">

</div>
<div id="form" style="float:right">
<form name="form1" action="SaveIdTag.jsp" method="POST" onsubmit="getList()">
Tag: <input type="text" name="tags"/>
<input type="hidden" name="ids" value=""/>
<input type="submit" name="save" value="Submit"/>
</form>
</div>
</div>

</body>
</html>