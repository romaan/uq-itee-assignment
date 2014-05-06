<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="Practical1._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Welcome to SOA ASP.NET! Practical 1
    </h2>
   <div><a href="CurrentTime.aspx">CurrentTime</a></div><div><a href="CalculateAge.aspx">Calcuate Age</a></div>
</asp:Content>
