<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<CarRentalTestApp.Models.CustomerDetails>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Booking
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Booking</h2>
        
    <% using (Html.BeginForm()) {%>
        <%: Html.ValidationSummary(true) %>

        <fieldset>
            <legend>Fields</legend>
            <table>
            <tr><td>Passport Number</td><td><%= Html.TextBox("customer_id") %><%= Html.ValidationMessage("customer_id") %></td></tr>
            <tr><td>First Name</td><td><%= Html.TextBox("first_name")%><%= Html.ValidationMessage("first_name")%></td></tr>
            <tr><td>Last Name</td><td><%= Html.TextBox("last_name") %><%= Html.ValidationMessage("last_name") %></td></tr>
            <tr><td>Gender</td><td><%= Html.RadioButton("gender","M", true) %> Male <%= Html.RadioButton("gender","F")%> Female </td></tr>
            <tr><td>Age</td><td><%= Html.TextBox("age") %><%= Html.ValidationMessage("age") %></td></tr>            
            <tr><td>Email</td><td><%= Html.TextBox("email") %><%= Html.ValidationMessage("email") %></td></tr>        
            <tr><td>Phone Number</td><td><%= Html.TextBox("phone_number") %><%= Html.ValidationMessage("phone_number") %></td></tr>        
            <tr><td>Address</td><td><%= Html.TextBox("address") %><%= Html.ValidationMessage("address") %></td></tr>        
            <tr><td>City</td><td><%= Html.TextBox("city") %><%= Html.ValidationMessage("city") %></td></tr>    
            </table>
            <p>
                <input type="submit" value="Proceed to Payment" />
            </p>
        </fieldset>

    <% } %>

    <div>
        <%: Html.ActionLink("Back to List", "Index") %>
    </div>

</asp:Content>

