<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="SearchPage.aspx.cs" Inherits="Practical2Task2.SearchPage" %>
<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <asp:Label ID="lblSearchBy" runat="server" Text="Search"></asp:Label>
    <asp:DropDownList ID="ddlSearchBy" runat="server" autopostback="true" 
        onselectedindexchanged="ddlSearchBy_SelectedIndexChanged">
        <asp:ListItem>Last Name</asp:ListItem>
        <asp:ListItem>Phone Number</asp:ListItem>
    </asp:DropDownList>
    <br />
    <asp:Panel ID="showhidePanel" runat="server">
        <asp:Label ID="lblSearchName" runat="server" Text="Search"></asp:Label>
        <asp:TextBox ID="txtSearchCriteria" runat="server"></asp:TextBox>
        <asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server" ErrorMessage="*required field" 
        ControlToValidate="txtSearchCriteria" ForeColor="Red"></asp:RequiredFieldValidator>
        <br />
        <asp:Button ID="btSearch" runat="server" Text="Search" onclick="btSearch_Click" />
    </asp:Panel>
    <asp:Label ID="lblResult" runat="server" Text=""></asp:Label>
</asp:Content>