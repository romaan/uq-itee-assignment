<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<CarRentalApp.Booking>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	BookingDetails
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>BookingDetails</h2>

    <fieldset>
        <legend>Fields</legend>
        <table>
        <tr><td><div class="display-label">Booking ID</div></td>
        <td><div class="display-field"><%: Model.booking_id %></div></td></tr>
        
        <tr><td><div class="display-label">Booking Status Code</div></td>
        <td><div class="display-field"><%: Model.booking_status_code %></div></td></tr>
        
        <tr><td><div class="display-label">Customer ID</div></td>
        <td><div class="display-field"><%: Model.customer_id %></div></td></tr>
        
        <tr><td><div class="display-label">Vehicle ID</div></td>
        <td><div class="display-field"><%: Model.vehicle_id %></div></td></tr>
        
        <tr><td><div class="display-label">Pickup Date</div></td>
        <td><div class="display-field"><%: String.Format("{0:g}", Model.pickup_date) %></div></td></tr>
        
        <tr><td><div class="display-label">Return Date</div></td>
        <td><div class="display-field"><%: String.Format("{0:g}", Model.return_date) %></div></td></tr>
        
        <tr><td><div class="display-label">Pickup City</div></td>
        <td><div class="display-field"><%: Model.pickup_city %></div></td></tr>
        
        <tr><td><div class="display-label">Return City</div></td>
        <td><div class="display-field"><%: Model.return_city %></div></td></tr>
        </table>
    </fieldset>
    <p>
       
        <%: Html.ActionLink("Back", "Cancel") %>
    </p>

</asp:Content>

