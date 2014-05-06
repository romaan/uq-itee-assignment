var moveVal = 0;
function showImage(move, isAlbum)
{
	var limit = Number(document.getElementById("limit").value);
	if (move == "next")
	{
		moveVal = moveVal + limit;
	}
	else if (move == "previous")
	{
		moveVal = moveVal - limit;
	}
ajaxCall("AjaxCalls.php?move="+moveVal+"&limit="+limit+"&album="+isAlbum,"image");
}

function ajaxCall(requestData, receiveContainer)
{

if (window.XMLHttpRequest)
  {
  xmlhttp=new XMLHttpRequest();
  }
else
  {
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById(receiveContainer).innerHTML=xmlhttp.responseText;
    }
  };

xmlhttp.open("GET",requestData,true);
xmlhttp.send();
}

function logout()
{
	ajaxCall("AjaxCalls.php?logout=Logout","logoutinfo");
	setTimeout("relocate()",100);
}

function relocate() {
	window.location="index.php";
}

function getList()
 {
	 var a = this.document.getElementById("selected").getElementsByTagName("img");;
	 var i, ids = "";
	 for (i = 0; i < a.length; i++)
		 {
		 ids += a[i].id;
		 if (i != a.length -1)
			 ids += ",";
		 }
ajaxCall("AjaxCalls.php?tags="+this.document.getElementById("tags").value+"&ids="+ids,"save");
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
 
 function searchimage()
{

ajaxCall("AjaxCalls.php?searchimage="+this.document.getElementById("searchtag").value, "imageresult");
}

function searchalbum()
{

ajaxCall("AjaxCalls.php?searchalbumimage="+this.document.getElementById("searchtag").value, "imageresult");
}

//Setup the Cookie
function setCookie(c_name,value,exSec)
{
var expiryTime = new Date();
expiryTime.setTime(expiryTime.getTime()+(exSec*1000)); 
var c_value=escape(value) + ";expires=" + expiryTime.toGMTString();
document.cookie=c_name + "=" + c_value;
}


//Get the cookie
function getCookie(cookieName)
{
var cookieVal = null;
	if(document.cookie)	   //only if exists
	{
       	var arr = document.cookie.split((escape(cookieName) + '=')); 
       	if(arr.length >= 2)
       	{
    	var arr2 = arr[1].split(';');
        cookieVal  = unescape(arr2[0]); //unescape() : Decodes the String
		this.document.frmLogin.user.value = cookieVal;
		}
	}
	return cookieVal;
}


//Verify User using Ajax Call
function verifyUser()
{
	if (document.frmLogin.remember.checked)
	{
		if (document.frmLogin.period.value == "1 minute")
		{
			setCookie("prac9User", document.frmLogin.user.value, 60);
		}
		else
		{
			setCookie("prac9User", document.frmLogin.user.value, (60 * 60 * 24));
		}
	}
	loginAjaxCall("AjaxCalls.php?user="+this.document.frmLogin.user.value+"&password="+this.document.frmLogin.password.value,"userLogin");
}

function rightPage()
{
	if (this.document.getElementById("userLogin").innerHTML == "Pass")
	{
		window.location = "Gallery.php";
	}
	else
	{
		this.document.getElementById("userLogin").style.visibility = 'visible'; 
		this.document.getElementById("userLogin").innerHTML = "Incorrect Username or Password";
	}
}

//Generic Ajax call
function loginAjaxCall(requestData, receiveContainer)
{

if (window.XMLHttpRequest)
  {
  xmlhttp=new XMLHttpRequest();
  }
else
  {
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById(receiveContainer).innerHTML=xmlhttp.responseText;
	rightPage();
    }
  };

xmlhttp.open("GET",requestData,true);
xmlhttp.send();
}
function create() {
var username = prompt("New username:");
var password = prompt("Password:");
ajaxCall("AjaxCalls.php?username="+username+"&password="+password,"newUserMessage");
}