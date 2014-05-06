<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="Phonebook.Login" %>
<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    <asp:Label ID="lblUserName" runat="server" Text="UserName"></asp:Label>
    <asp:TextBox ID="txtUserName" runat="server"></asp:TextBox>     
    <asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server" ErrorMessage="*required field" 
        ControlToValidate="txtUserName" ForeColor="Red"></asp:RequiredFieldValidator><br />

       <asp:Label ID="lblPassword" runat="server" Text="Password"></asp:Label>
    <asp:TextBox ID="txtPassword" runat="server" TextMode="Password"></asp:TextBox>
         <asp:RequiredFieldValidator id="RequiredFieldValidator2" runat="server" ErrorMessage="*required field" 
        ControlToValidate="txtPassword" ForeColor="Red"></asp:RequiredFieldValidator><br />
    <asp:Button ID="btLogin" runat="server" Text="Login" 
    onclick="btLogin_Click" />
    <asp:Label ID="lblResult" runat="server" Text=""></asp:Label>
</asp:Content>
