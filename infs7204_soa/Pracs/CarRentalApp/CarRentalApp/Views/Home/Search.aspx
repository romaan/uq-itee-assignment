<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Search   
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Search</h2>
    
    <table>
    <% using (Ajax.BeginForm("Results", "Home",
     new AjaxOptions
     {
       UpdateTargetId = "results",
       HttpMethod = "post"
     })) {%>
    <tr><td>Vehicle Category:</td><td><%= Html.DropDownList("ddlVehicleCategory")%></td><td>Body Style:</td><td><%= Html.DropDownList("ddlBodyStyle")%></td></tr>
    <tr><td>Pickup City:</td><td><%= Html.DropDownList("ddlPickUp")%></td><td>Dropoff City:</td><td><%= Html.DropDownList("ddlReturnCity")%></td></tr>
    <tr><td>Pickup Date:</td><td><input type="text" name="pickupdate"/></td><td>Dropoff Date:</td><td><input type="text" name="dropoffdate" /></td></tr>
    <tr><td><input type="submit" value="Search" /></td><td><%= Html.ValidationMessage("pickupdate")%><%= Html.ValidationMessage("dropoffdate")%></td><td>Sort by:</td><td><%= Html.DropDownList("sort") %></td></tr>        
    </table>       
    <% }%>
    <div id="results">

    </div>
   <div id="compare">
</div>

</asp:Content>
