<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Web Information System Practical 8</title>
<link rel="stylesheet" type="text/css" href="dynamicdragdrop.css"/>
<script type="text/javascript">
var current = 0;
var dfault;
function takeAction(str)
{
	showAjax(str);
	if (str == "next") {
		current = Number(current) + Number(dfault);
	} else if (str == "previous") {
		current = Number(current) - Number(dfault);
	}
	//alert("Current"+current+"Default"+dfault+"Move"+str);
}

function setDefault()
{
	dfault = Number(this.document.defaultform.d.value);
	current = 0;
	showAjax('nothing');
}

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
    document.getElementById("all").innerHTML=xmlhttp.responseText;
    }
  };

xmlhttp.open("GET","myajax.jsp?default="+dfault+"&move="+str+"&current="+current,true);
xmlhttp.send();
}

function getInnerHTML(str)
{
	return this.document.getElementById(str).innerHTML;
}

function setInnerHTML(str,setvalue)
{
	this.document.getElementById(str).innerHTML = setvalue;
}
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
<body onload="setDefault()">
<h1>My Gallery</h1>
<hr/><br/>
<div style="width:600px; margin: 0 auto; text-align: center;">
	<form name="defaultform">
	  <input name="d" type="text" value="3" maxlength="2"/>
	  <input type="button" name="setdefault" value="Set Default" onclick="setDefault()"/>
    </form>
</div>
<hr/><br/>
<div id="previous" style="float:left;">
	<input type="button" name="previous" value="Previous"  onclick="takeAction('previous')"/>
</div>

<div id="next" style="float:right;">
	<input type="button" name="next" value="Next" onclick="takeAction('next')"/>
</div>

<table width="100%">
<tr><td width="60%"><h3>All Photos</h3></td><td width="40%"><h3>Selected Album</h3></td></tr>
</table>
<div id="maincontainer" style="width:100%; height: 700px;">
<div id="all" ondrop="drop(event)" ondragover="allowDrop(event)">

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