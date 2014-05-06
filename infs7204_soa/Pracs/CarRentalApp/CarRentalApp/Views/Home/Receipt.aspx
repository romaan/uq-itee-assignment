<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<CarRentalApp.Models.ParentModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Receipt
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    <% if (ViewData["error"] != "")
           Response.Write("Unable to save the data:"+ViewData["error"]);
       else
       { %>
    <div id = "receipt" runat="server">
    <h2>Receipt</h2>
    <hr />
    Payment Card Information
    <table>
    <tr><td>Card type:</td><td><%= Model.payment.cardtype %></td></tr>
    <tr><td>Card name:</td><td><%= Model.payment.cardname %></td></tr>
    <tr><td>Card number:</td><td><%= Model.payment.cardnumber %></td></tr>
    <tr><td>Security number:</td><td><%= Model.payment.securitynumber %></td></tr>
    <tr><td>Expirty Date:</td><td><%= Model.payment.expirydate %></td></tr>
    </table>
    <hr />
    Customer
    <table>
    <tr><td>Customer Passport:</td><td><%= Model.customer.customer_id %></td></tr>
    <tr><td>First Name</td><td><%= Model.customer.first_name %></td></tr>
    <tr><td>Second Name</td><td><%= Model.customer.last_name %></td></tr>
    <tr><td>Age</td><td><%= Model.customer.age %></td></tr>
    <tr><td>Gender</td><td><%= Model.customer.gender %></td></tr>
    <tr><td>Email</td><td><%= Model.customer.email %></td></tr>
    <tr><td>Phone Number</td><td><%= Model.customer.phone_number %></td></tr>
    <tr><td>Address</td><td><%= Model.customer.address %></td></tr>
    <tr><td>City</td><td><%= Model.customer.city %></td></tr>
    </table>
    <hr />
    Vehicle Information
     <table>
        <tr><td><div class="display-label"><b>Registration Number</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.registration_number %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturer Name</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.manufacturer_name%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Model Code</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.model_code%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Body Style</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.body_style%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Automatic</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.automatic%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Passenger Capacity</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.passenger_capacity%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Vehicle Category Description</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.vehicle_category_description%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturing Date</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.manufacturing_date%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Availability</b></div>
        </td><td><div class="display-field"><%: Model.reviewmodel.availability%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Daily Rental Rate</b></div>
        </td><td><div class="display-field"><%: String.Format("{0:F}", Model.reviewmodel.daily_rental_rate)%></div></td></tr>

        <tr><td><div class="display-label"><b>Total Rental Rate</b></div>
        </td><td><div class="display-field"><% DateTime end = Convert.ToDateTime(ViewData["dropoffdate"]);
                                               DateTime start = Convert.ToDateTime(ViewData["pickupdate"]);

                                               double d = ((end - start).TotalDays + 1 ) * Convert.ToDouble(Model.reviewmodel.daily_rental_rate);
                                               Response.Write(d.ToString()); %></div></td></tr>
        </table>
        <hr />
         <h2>Booking Details</h2>
            <table>
            <tr><td>Booking ID:</td><td><%: Model.booking.booking_id %></td></tr>
            <tr><td>Pickup Date:</td><td><%: Model.booking.pickup_date %></td></tr>
            <tr><td>Drop off Date:</td><td><%: Model.booking.return_date%></td></tr>
            <tr><td>Pickup City:</td><td><%: Model.booking.pickup_city %></td></tr>
            <tr><td>Return City:</td><td><%: Model.booking.return_city %></td></tr>
            </table>
            </div>            
            <% } %>
</asp:Content>