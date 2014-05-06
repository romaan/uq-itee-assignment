<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<dynamic>" %>

<h3>Cancellation List</h3>
<% 
    if (ViewData["error"] != "")
   {
       Response.Write(ViewData["error"]);
   }       
   else
   {
       %>
       <table>
       <tr><td>Booking ID</td><td>Vehicle ID</td><td>Cancel</td></tr>
       <%
       foreach (CarRentalApp.Models.Booking booking in ViewData.Model)
       {
           Response.Write("<tr><td><a href='BookingDetails?booking_id=" + booking.booking_id + "'>" + booking.booking_id + "</a></td><td><a href='Review?vehicle_id=" + booking.vehicle_id + "&pickupdate=" + booking.pickup_date + "&returnDate=" + booking.return_date + "&pickupcity=" + booking.pickup_city + "&returncity=" + booking.return_city + "'>" + booking.vehicle_id + "</a></td><td><a href='DeleteBooking?booking_id=" + booking.booking_id + "'>Cancel</a></td></tr>");       
       } 
       %>
       </table>
 <%} %>