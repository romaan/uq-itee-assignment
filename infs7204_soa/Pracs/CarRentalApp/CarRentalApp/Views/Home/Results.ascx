<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<dynamic>" %>

<h1>Results</h1>

<%  var err = ViewData["myerr"];
    if (err != null)
    {
        Response.Write(err.ToString());
    }
    else if (ViewData.Model != null)
    {
     using (Ajax.BeginForm("Compare", "Home",
     new AjaxOptions
     {
       UpdateTargetId = "compare",
       HttpMethod = "post"
     }))
     {
        Response.Write("<table>");
        Response.Write("<b><tr><td>Registration Number</td><td>Vehicle ID</td><td>Manufacturer Name</td><td>Model Code</td><td>Daily Hire Rate</td><td>Link</td><td><input type='submit' value='Compare'/></td></tr></b>");
        foreach (CarRentalApp.Models.SearchResult result in ViewData.Model)
        {
            Response.Write("<tr><td>" + result.registration_number + "</td>");
            Response.Write("<td>" + result.vehicle_id + "</td>");
            Response.Write("<td>" + result.manufacturer_name + "</td>");
            Response.Write("<td>" + result.model_code + "</td>");
            Response.Write("<td>" + result.daily_hire_rate + "</td>");
            Response.Write("<td><a href=\"Review?vehicle_id=" + result.vehicle_id + "&pickupdate=" + ViewData["pickupdate"] + "&returnDate=" + ViewData["dropoffdate"] + "&pickupcity=" + ViewData["pickupcity"] + "&returncity=" + ViewData["returncity"] + "\">Review</a></td>");
            Response.Write("<td><input type='checkbox' name='icompare' value='"+result.vehicle_id+"'/></td></tr>");
        }
        Response.Write("</table>");
     }
    }
    else
    {
        Response.Write("No vehicle available");
    }

%>
 