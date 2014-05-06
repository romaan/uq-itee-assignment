<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<CarRentalTestApp.Models.ReviewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Review
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Review</h2>

    <fieldset>
        <legend>Fields</legend>
        <table>
        <tr><td><div class="display-label"><b>Registration Number</b></div>
        </td><td><div class="display-field"><%: Model.registration_number %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturer Name</b></div>
        </td><td><div class="display-field"><%: Model.manufacturer_name %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Model Code</b></div>
        </td><td><div class="display-field"><%: Model.model_code %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Body Style</b></div>
        </td><td><div class="display-field"><%: Model.body_style %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Automatic</b></div>
        </td><td><div class="display-field"><%: Model.automatic %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Passenger Capacity</b></div>
        </td><td><div class="display-field"><%: Model.passenger_capacity %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Vehicle Category Description</b></div>
        </td><td><div class="display-field"><%: Model.vehicle_category_description %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturing Date</b></div>
        </td><td><div class="display-field"><%: Model.manufacturing_date %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Availability</b></div>
        </td><td><div class="display-field"><%: Model.availability %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Daily Rental Rate</b></div>
        </td><td><div class="display-field"><%: String.Format("{0:F}", Model.daily_rental_rate) %></div></td></tr>

        <tr><td><div class="display-label"><b>Total Rental Rate</b></div>
        </td><td><div class="display-field"><% DateTime end = Convert.ToDateTime(ViewData["dropoffdate"]);
                                               DateTime start = Convert.ToDateTime(ViewData["pickupdate"]);

                                               double d = ((end - start).TotalDays + 1 ) * Convert.ToDouble(Model.daily_rental_rate);
                                               Response.Write(d.ToString()); %></div></td></tr>
        </table>
    </fieldset>
 

        <a href="Booking?id=<%= ViewData["id"]%>&pickupdate=<%= ViewData["pickupdate"]%>&dropoffdate=<%= ViewData["dropoffdate"]%>&returncity=<%= ViewData["returncity"]%>&pickupcity=<%= ViewData["pickupcity"] %>"><input type="button" value="Confirm Booking" /></a>
       


    <p>
        <%: Html.ActionLink("Edit", "Edit", new { /* id=Model.PrimaryKey */ }) %>
        <%: Html.ActionLink("Back to List", "Search") %>        
      
    </p>

</asp:Content>

