<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="prac2_1WebApplication._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    
    <asp:Label ID="lblFirstWord" runat="server" Text="search word: " 
        Visible="False"></asp:Label>
    
<asp:TextBox ID="txtboxFirstWord" runat="server"></asp:TextBox>
<asp:Label ID="lblFoundError" runat="server" ForeColor="Red"></asp:Label>
<asp:Label ID="lblWordOccur" runat="server"></asp:Label>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" 
        ControlToValidate="txtboxFirstWord" ErrorMessage="*Please enter a word!" 
        ForeColor="Red" Visible="False"></asp:RequiredFieldValidator>
<br />
<asp:Label ID="lblSecondWord" runat="server" Text="replace word: " Visible="False"></asp:Label>
    <asp:TextBox ID="txtboxSecondWord" runat="server"></asp:TextBox>
    <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" 
        ControlToValidate="txtboxSecondWord" ErrorMessage="*Please enter a word!" 
        ForeColor="Red" Visible="False"></asp:RequiredFieldValidator>
    <br />
<br />
    Main text:<br />
<asp:TextBox ID="txtboxMain" runat="server" Height="215px" TextMode="MultiLine" 
    Width="342px"></asp:TextBox>
<asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
    ControlToValidate="txtboxMain" ErrorMessage="*Please enter the text!" 
    ForeColor="Red"></asp:RequiredFieldValidator>
<br />
<br />
    <asp:DropDownList ID="DropDownListOperation" runat="server" 
        onselectedindexchanged="DropDownListOperation_TextChanged" 
        ontextchanged="DropDownListOperation_TextChanged" AutoPostBack="True">
        <asp:ListItem Selected="True">Word Count</asp:ListItem>
        <asp:ListItem>Word Occurrence Count</asp:ListItem>
        <asp:ListItem>Replace</asp:ListItem>
    </asp:DropDownList>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<asp:Button ID="btnSubmit" runat="server" Text="Submit" onclick="btnSubmit_Click" />
&nbsp;&nbsp;&nbsp;&nbsp;
    <br />
    <br />
    <asp:Label ID="lblWordCount" runat="server"></asp:Label>
    <br />
<br />
<br />
    
</asp:Content>
