<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="refresh" Content="5;dynamicdragdrop.jsp"/>
<title>Prac8 Saving Page</title>
</head>
<body>
<jsp:useBean id="fw" class="BeanSaveTag.BeanSaveTag"/> 
<jsp:setProperty name="fw" property="*" />
IDS:<jsp:getProperty name="fw" property="ids" /><br/>
TAGS:<jsp:getProperty name="fw" property="tags" />

<h1><%= fw.write() %></h1>
</body>
</html>