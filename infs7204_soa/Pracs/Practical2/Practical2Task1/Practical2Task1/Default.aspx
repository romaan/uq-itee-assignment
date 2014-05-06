<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="Practical2Task1._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent"></asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        Text Editor
    </h2>
    <br />
    <asp:Panel ID="Panel1" runat="server">
    Search Word:<asp:TextBox ID="txtSearchWord" runat="server"></asp:TextBox> 
        <asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server"
        ControlToValidate="txtSearchWord"
        ErrorMessage="*Please enter a word!"
        ForeColor="Red">
        </asp:RequiredFieldValidator>
    <asp:Label ID="lblwordsearchresult" runat="server"></asp:Label>
        <asp:Panel ID="Panel2" runat="server">
        Replace Word:
            <asp:TextBox ID="txtReplaceWord" runat="server"></asp:TextBox>
                <asp:RequiredFieldValidator id="RequiredFieldValidator2" runat="server"
                ControlToValidate="txtReplaceWord"
                ErrorMessage="*Please enter a word!"
                ForeColor="Red">
                </asp:RequiredFieldValidator>
        </asp:Panel>
    </asp:Panel>
   
 
    <br />
    Main Text:<br />
    <asp:TextBox ID="txtMain" runat="server" Height="165px" Width="399px" Rows="10" TextMode="MultiLine"></asp:TextBox>
                <asp:RequiredFieldValidator id="RequiredFieldValidator3" runat="server"
                ControlToValidate="txtMain"
                ErrorMessage="*Please enter a word!"
                ForeColor="Red">
                </asp:RequiredFieldValidator>
    <br />
    <asp:DropDownList ID="ddlFunctionality" runat="server" autopostback="true"
        onselectedindexchanged="ddlFunctionality_SelectedIndexChanged" >
        <asp:ListItem Value="Word Count">Word Count</asp:ListItem>
        <asp:ListItem Value="Word Occurence Count">Word Occurence Count</asp:ListItem>
        <asp:ListItem Value="Replace Text">Replace Text</asp:ListItem>
    </asp:DropDownList>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <asp:Button ID="btSubmit" runat="server" Text="Submit" 
        onclick="btSubmit_Click" /><br /><br />    
    <asp:Label ID="lblWordCount" runat="server" Text=""></asp:Label>
    <br />
</asp:Content>
