<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Cancel
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Cancel</h2>
     <% using (Ajax.BeginForm("CancelList", "Home",
     new AjaxOptions
     {
         UpdateTargetId = "cancellist",
         HttpMethod = "post"
     }))
     {%>
        Passport Number: <input type="text" name="passport" /> <input type="submit" value="Search For Cancellation" />
     <% } %>
     <div id="cancellist">
     
     </div>
</asp:Content>
