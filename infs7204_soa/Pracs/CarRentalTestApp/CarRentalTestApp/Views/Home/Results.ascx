<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<dynamic>" %>

<h1>Results</h1>
<%  var err = ViewData["myerr"];
    if (err != null)
    {
        Response.Write(err.ToString());
    }
    else if (ViewData.Model != null)
    {
        Response.Write("<table>");
        Response.Write("<b><tr><td>Registration Number</td><td>Vehicle ID</td><td>Manufacturer Name</td><td>Model Code</td><td>Daily Hire Rate</td><td>Link</td></tr></b>");
        foreach (CarRentalTestApp.Models.SearchResult result in ViewData.Model)
        {
            Response.Write("<tr><td>" + result.registration_number + "</td>");
            Response.Write("<td>" + result.vehicle_id + "</td>");
            Response.Write("<td>" + result.manufacturer_name + "</td>");
            Response.Write("<td>" + result.model_code + "</td>");
            Response.Write("<td>" + result.daily_hire_rate + "</td><td>");
            Response.Write("<a href=\"Review?vehicle_id=" + result.vehicle_id + "&pickupdate=" + ViewData["pickupdate"] + "&returnDate=" + ViewData["dropoffdate"] + "&pickupcity=" + ViewData["pickupcity"] + "&returncity=" + ViewData["returncity"] + "\">Review</a>");
            Response.Write("</td></tr>");
        }
        Response.Write("</table>");
    }
    else
    {
        Response.Write("No vehicle available");
    }

%>