<%@ Page Language="C#" AutoEventWireup="true"  MasterPageFile="~/Site.master" CodeBehind="CalculateAge.aspx.cs" Inherits="Practical1.CurrentAge" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
   
    <asp:TextBox ID="txtDoB" runat="server"></asp:TextBox>
    <asp:Button ID="btCalcAge" runat="server" onclick="btCalcAge_Click" 
        Text="Say My Age!" />
    <br />
    <asp:Label ID="lblAge" runat="server" Text=""></asp:Label>
   
</asp:Content>
