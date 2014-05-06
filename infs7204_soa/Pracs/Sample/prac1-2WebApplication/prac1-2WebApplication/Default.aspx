<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="prac1_2WebApplication._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    
    <asp:Label ID="Label1" runat="server" Text="lblFirstname">First name: </asp:Label>
    <asp:TextBox ID="txtboxFirstname" runat="server"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
        ControlToValidate="txtboxFirstname" ErrorMessage="*required field" 
        ForeColor="Red"></asp:RequiredFieldValidator>
    <br />
    <asp:Label ID="lblLastname" runat="server" Text="Label">Last name: </asp:Label>
    <asp:TextBox ID="txtboxLastname" runat="server"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
        ControlToValidate="txtboxLastname" ErrorMessage="*required field" 
        ForeColor="Red"></asp:RequiredFieldValidator>
    <br />
    <asp:Label ID="lblAreaCode" runat="server" Text="Label">Area Code: </asp:Label>
    <asp:DropDownList ID="DropDownListAreaCode" runat="server">
        <asp:ListItem>02</asp:ListItem>
        <asp:ListItem>03</asp:ListItem>
        <asp:ListItem>07</asp:ListItem>
        <asp:ListItem>08</asp:ListItem>
        <asp:ListItem>04</asp:ListItem>
    </asp:DropDownList>
&nbsp;<asp:Label ID="lblAreaCodeError" runat="server" ForeColor="Red"></asp:Label>
    &nbsp;&nbsp;
    <asp:Label ID="lblPhoneNO" runat="server" Text="Label">Phone Number: </asp:Label>
    <asp:TextBox ID="txtboxPhoneNO" runat="server" TextMode="Phone" MaxLength="8"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" 
        ControlToValidate="txtboxPhoneNO" ErrorMessage="*required field" 
        ForeColor="Red"></asp:RequiredFieldValidator>
    <br />
    <asp:Label ID="lblAddress" runat="server" Text="Label">Address: </asp:Label>
    <asp:TextBox ID="txtboxAddr" runat="server"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" 
        ControlToValidate="txtboxAddr" ErrorMessage="*required field" ForeColor="Red"></asp:RequiredFieldValidator>
    <br />
    <asp:Label ID="lblState" runat="server" Text="Label">State: </asp:Label>
    <asp:DropDownList ID="DropDownListState" runat="server">
        <asp:ListItem>NSW</asp:ListItem>
        <asp:ListItem>QLD</asp:ListItem>
        <asp:ListItem>VIC</asp:ListItem>
        <asp:ListItem>TAS</asp:ListItem>
        <asp:ListItem>SA</asp:ListItem>
        <asp:ListItem>NT</asp:ListItem>
    </asp:DropDownList>
    <br />
    <asp:Label ID="lblEmail" runat="server" Text="Label">email</asp:Label>
    <asp:TextBox ID="txtboxEmail" runat="server" 
        TextMode="Email"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" 
        ControlToValidate="txtboxEmail" ErrorMessage="*required field" ForeColor="Red"></asp:RequiredFieldValidator>
    <br />
    <asp:Label ID="lblDOB" runat="server" Text="Label">Date of Birth: </asp:Label>
&nbsp;&nbsp;&nbsp;
    <asp:Label ID="lblDay" runat="server" Text="Label">Day: </asp:Label>
    <asp:DropDownList ID="DropDownListDay" runat="server">
        <asp:ListItem>1</asp:ListItem>
        <asp:ListItem>2</asp:ListItem>
        <asp:ListItem>3</asp:ListItem>
        <asp:ListItem>4</asp:ListItem>
        <asp:ListItem>5</asp:ListItem>
        <asp:ListItem Selected="True">6</asp:ListItem>
        <asp:ListItem>7</asp:ListItem>
        <asp:ListItem>8</asp:ListItem>
        <asp:ListItem>9</asp:ListItem>
        <asp:ListItem>10</asp:ListItem>
        <asp:ListItem>11</asp:ListItem>
        <asp:ListItem>12</asp:ListItem>
    </asp:DropDownList>
&nbsp;<asp:Label ID="lblMonth" runat="server" Text="Label">Month: </asp:Label>
    <asp:DropDownList ID="DropDownListMonth" runat="server">
        <asp:ListItem>1</asp:ListItem>
        <asp:ListItem>2</asp:ListItem>
        <asp:ListItem>3</asp:ListItem>
        <asp:ListItem Selected="True">4</asp:ListItem>
        <asp:ListItem>5</asp:ListItem>
        <asp:ListItem>6</asp:ListItem>
        <asp:ListItem>7</asp:ListItem>
        <asp:ListItem>8</asp:ListItem>
    </asp:DropDownList>
&nbsp;<asp:Label ID="lblYear" runat="server" Text="Label">Year: </asp:Label>
    <asp:DropDownList ID="DropDownListYear" runat="server">
        <asp:ListItem>2012</asp:ListItem>
        <asp:ListItem>2011</asp:ListItem>
        <asp:ListItem Selected="True">1987</asp:ListItem>
    </asp:DropDownList>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <asp:CheckBox ID="CheckBoxRemind" runat="server" Text="Remind me this birthday!" />
    
    <br />
    
    <br />
    <br />
    <asp:Button ID="btnSubmit" runat="server" Text="Save" 
    onclick="btnSubmit_Click" />
    
</asp:Content>
