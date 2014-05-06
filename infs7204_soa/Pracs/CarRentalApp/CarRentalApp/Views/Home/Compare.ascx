<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<dynamic>" %>

<%
    
    if (ViewData["error"] != "")
        Response.Write("<br/><hr/>"+ ViewData["error"]);
    else
    {
        Response.Write("<br/><hr/><table>");
        Response.Write("<tr><td>Compare</td><td> Vehicle: " + ViewData["car1_id"] + "</td><td> Vehicle: " + ViewData["car2_id"] + "</td><td>Better</td></tr>");
        Response.Write("<tr><td>Daily Hire Rate</td><td>" + ViewData["car1_hire"] + "</td><td>" + ViewData["car2_hire"] + "</td><td>"+ ViewData["hire_winner"]+"</td></tr>");
        Response.Write("<tr><td>Engine Power</td><td>" + ViewData["car1_engine"] + "</td><td>" + ViewData["car2_engine"] + "</td><td>" + ViewData["engine_winner"] + "</td></tr>");
        Response.Write("<tr><td>Winner</td><td>" + ViewData["car1_pop"] + "</td><td>" + ViewData["car2_pop"] + "</td><td>" + ViewData["pop_winner"] + "</td></tr>");
        Response.Write("</table>");
    }

 %>