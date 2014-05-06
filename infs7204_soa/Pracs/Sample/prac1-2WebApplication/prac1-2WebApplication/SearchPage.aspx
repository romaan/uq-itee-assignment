<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="SearchPage.aspx.cs" Inherits="prac1_2WebApplication.SearchPage" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    
        <asp:Label ID="Label1" runat="server" Text="Search by: "></asp:Label>
        <asp:DropDownList ID="DropDownList1" runat="server" AutoPostBack="True" 
            onselectedindexchanged="DropDownList1_SelectedIndexChanged" 
            ontextchanged="DropDownList1_SelectedIndexChanged">
            <asp:ListItem>Phone Number</asp:ListItem>
            <asp:ListItem Selected="True">Last name</asp:ListItem>
        </asp:DropDownList>
        <br />
    
    </div>
    <asp:Panel ID="PanelLastnameSearch" runat="server">
        <asp:Label ID="lblSearchLastname" runat="server" Text="Last name: "></asp:Label>
        <asp:TextBox ID="txtboxSearchLastname" runat="server" Width="164px"></asp:TextBox>
    </asp:Panel>
    <asp:Panel ID="PanelPhoneNoSearch" runat="server" Visible="False">
        <asp:Label ID="lblSearchPhoneNumber" runat="server" Text="Phone Number: "></asp:Label>
        <asp:TextBox ID="txtboxSearchPhoneNO" runat="server" Width="134px"></asp:TextBox>
    </asp:Panel>
    <br />
    <br />
    <asp:Button ID="btnSearch" runat="server" Text="Search" 
        onclick="btnSearch_Click" />
    <br />
    <br />
    <asp:Panel ID="PanelResult" runat="server" Height="187px" Visible="False">
        <asp:Label ID="Label2" runat="server" Text="Contact Info:" Font-Bold="False" 
            Font-Size="Larger"></asp:Label>
        <br />
        <br />
        <asp:Label ID="Label3" runat="server" Text="Label">Full name: </asp:Label>
        <asp:Label ID="lblResultFN" runat="server"></asp:Label>
        <br />
        <asp:Label ID="Label6" runat="server" Text="Full Phone Number: "></asp:Label>
        <asp:Label ID="lblResultPhN" runat="server"></asp:Label>
        <br />
        <br />
        <br />
        <br />
        <br />
    </asp:Panel>
    </form>
</body>
</html>
