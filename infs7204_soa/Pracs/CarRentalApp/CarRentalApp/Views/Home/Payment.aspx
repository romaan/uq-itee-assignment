<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<CarRentalApp.Models.PaymentModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Payment
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Payment</h2>

    <% using (Html.BeginForm()) {%>
        <%: Html.ValidationSummary(true) %>

        <fieldset>
            <legend>Fields</legend>
            <%= Html.ValidationMessage("error") %>
            <table>
            <tr><td>Select Card Type:</td><td><%= Html.RadioButton("card_type","MasterCard", true) %> Master Card <%= Html.RadioButton("card_type","Visa")%> Visa </td></tr>
            <tr><td>Name on the card:</td><td><%= Html.TextBox("card_name") %></td></tr>
            <tr><td>Credit card number:</td><td><%= Html.TextBox("card_number") %></td></tr>
            <tr><td>Security number:</td><td><%= Html.TextBox("security_number") %></td></tr>
            <tr><td>Expiry date:</td><td><%= Html.TextBox("expiry_date") %></td></tr>            
            </table>
            <hr />
            Total Hire Cost:
            <hr />
            <h2>Vehicle Informaion</h2>
              <fieldset>
        <legend>Fields</legend>
        <table>
        <tr><td><div class="display-label"><b>Registration Number</b></div>
        </td><td><div class="display-field"><%= ViewData["registration_number"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturer Name</b></div>
        </td><td><div class="display-field"><%= ViewData["manufacturer_name"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Model Code</b></div>
        </td><td><div class="display-field"><%= ViewData["model_code"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Body Style</b></div>
        </td><td><div class="display-field"><%= ViewData["body_style"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Automatic</b></div>
        </td><td><div class="display-field"><%= ViewData["automatic"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Passenger Capacity</b></div>
        </td><td><div class="display-field"><%= ViewData["passenger_capacity"]%></div></td></tr>
        
        <tr><td><div class="display-label"><b>Vehicle Category Description</b></div>
        </td><td><div class="display-field"><%= ViewData["vehicle_category_description"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Manufacturing Date</b></div>
        </td><td><div class="display-field"><%= ViewData["manufacturing_date"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Availability</b></div>
        </td><td><div class="display-field"><%= ViewData["availability"] %></div></td></tr>
        
        <tr><td><div class="display-label"><b>Daily Rental Rate</b></div>
        </td><td><div class="display-field"><%: String.Format("{0:F}", ViewData["daily_rental_rate"]) %></div></td></tr>

        <tr><td><div class="display-label"><b>Total Rental Rate</b></div>
        </td><td><div class="display-field"><% DateTime end = Convert.ToDateTime(ViewData["dropoffdate"]);
                                               DateTime start = Convert.ToDateTime(ViewData["pickupdate"]);

                                               double d = ((end - start).TotalDays + 1) * Convert.ToDouble(ViewData["daily_rental_rate"]);
                                               Response.Write(d.ToString()); %></div></td></tr>
        </table>
    </fieldset>
            <h2>Customer Information</h2>            
            <table>
            <tr><td>Customer ID:</td><td><%= ViewData["customer_id"] %></td></tr>
            <tr><td>First Name:</td><td><%= ViewData["first_name"] %></td></tr>
            <tr><td>Last Name:</td><td><%= ViewData["last_name"] %></td></tr>
            <tr><td>Gender:</td><td><%= ViewData["last_name"] %></td></tr>
            <tr><td>Age:</td><td><%= ViewData["age"] %></td></tr>
            <tr><td>Email:</td><td><%= ViewData["email"] %></td></tr>
            <tr><td>Phone Number:</td><td><%= ViewData["phone_number"] %></td></tr>
            <tr><td>Address:</td><td><%= ViewData["address"] %></td></tr>
            <tr><td>City:</td><td><%= ViewData["city"] %></td></tr>
            </table>
            <hr />
            <h2>Booking Details</h2>
            <table>
            <tr><td>Pickup Date:</td><td><%= ViewData["pickupdate"] %></td></tr>
            <tr><td>Drop off Date:</td><td><%= ViewData["dropoffdate"] %></td></tr>
            <tr><td>Pickup City:</td><td><%= ViewData["pickupcity"] %></td></tr>
            <tr><td>Return City:</td><td><%= ViewData["returncity"] %></td></tr>
            </table>

            <p>
                <input type="submit" value="Make Payment" />
            </p>
        </fieldset>

    <% } %>

    <div>
        <%: Html.ActionLink("Back to List", "Index") %>
    </div>

</asp:Content>

