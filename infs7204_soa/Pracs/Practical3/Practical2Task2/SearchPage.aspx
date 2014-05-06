<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="SearchPage.aspx.cs" Inherits="Phonebook.SearchPage" %>
<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <asp:Label ID="lblSearchBy" runat="server" Text="Search"></asp:Label>
    <asp:DropDownList ID="ddlSearchBy" runat="server" autopostback="true" 
        onselectedindexchanged="ddlSearchBy_SelectedIndexChanged">
        <asp:ListItem>Full Name</asp:ListItem>
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
    <br />
        <asp:Button ID="btEdit" runat="server" Text="Edit" Height="26px" 
        onclick="btEdit_Click" />
    <br />
    <asp:Panel ID="PanelResult" runat="server">
    <asp:Label ID="lblFirstName" runat="server" Text="First Name"></asp:Label>
        <asp:TextBox ID="txtFirstName" runat="server"></asp:TextBox>
        <asp:RequiredFieldValidator id="RequiredFieldValidator2" runat="server" ErrorMessage="*required field" 
        ControlToValidate="txtFirstName" ForeColor="Red"></asp:RequiredFieldValidator>
        <br /><asp:Label ID="lblLastName" runat="server" Text="Last Name"></asp:Label>
        <asp:TextBox ID="txtLastname" runat="server"></asp:TextBox>
        <asp:RequiredFieldValidator id="RequiredFieldValidator3" runat="server" ErrorMessage="*required field" ForeColor="Red"
        ControlToValidate="txtLastName"></asp:RequiredFieldValidator>
        <br /><asp:Label ID="lblAreaCode" runat="server" Text="Area Code"></asp:Label>
        <asp:DropDownList ID="ddlAreaCode" runat="server">
            <asp:ListItem>02</asp:ListItem>
            <asp:ListItem>03</asp:ListItem>
            <asp:ListItem>07</asp:ListItem>
            <asp:ListItem>08</asp:ListItem>
        </asp:DropDownList>
        <asp:Label ID="lblAreaError" runat="server" Text="" ForeColor="Red"></asp:Label>
        <asp:Label ID="lblPhoneNumber" runat="server" Text="Phone Number"></asp:Label>
        <asp:TextBox ID="txtPhoneNumber" runat="server"></asp:TextBox>
       <asp:RegularExpressionValidator ID="number" runat="server" ValidationExpression="\d\d\d\d\d\d\d\d" ControlToValidate="txtPhoneNumber" ErrorMessage="Not Valid!" ForeColor="Red"></asp:RegularExpressionValidator>
        <asp:RequiredFieldValidator id="RequiredFieldValidator4" runat="server" ErrorMessage="*required field" ForeColor="Red"
        ControlToValidate="txtPhoneNumber"></asp:RequiredFieldValidator>
        <br /><asp:Label ID="lblAddress" runat="server" Text="Address"></asp:Label>
        <asp:TextBox ID="txtAddress" runat="server"></asp:TextBox>
        <asp:RequiredFieldValidator id="RequiredFieldValidator5" runat="server" ErrorMessage="*required field" ForeColor="Red"
        ControlToValidate="txtAddress"></asp:RequiredFieldValidator>
        <br /><asp:Label ID="lblState" runat="server" Text="State"></asp:Label>
        <asp:DropDownList ID="ddlState" runat="server">
            <asp:ListItem>NSW</asp:ListItem>
            <asp:ListItem>ACT</asp:ListItem>
            <asp:ListItem>VIC</asp:ListItem>
            <asp:ListItem>TAS</asp:ListItem>
            <asp:ListItem>QLD</asp:ListItem>
            <asp:ListItem>WA</asp:ListItem>
            <asp:ListItem>SA</asp:ListItem>
            <asp:ListItem>NT</asp:ListItem>
        </asp:DropDownList>
        <br /><asp:Label ID="lblEmail" runat="server" Text="Email:"></asp:Label>
        <asp:TextBox ID="txtEmail" runat="server"></asp:TextBox>
        <asp:RegularExpressionValidator ID="EmailID" runat="server" ValidationExpression="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*" ControlToValidate="txtEmail" ErrorMessage="Not Valid!" ForeColor="Red"></asp:RegularExpressionValidator>
        <asp:RequiredFieldValidator id="RequiredFieldValidator6" runat="server" ErrorMessage="*required field" ForeColor="Red"
        ControlToValidate="txtEmail"></asp:RequiredFieldValidator>
        <br /><asp:Label ID="lblDateofbirth" runat="server" Text="Date of Birth:"></asp:Label>
        <asp:Label ID="lblDate" runat="server" Text="Date:"> </asp:Label>
        <asp:DropDownList ID="ddlDate" runat="server">       </asp:DropDownList>
        <asp:Label ID="lblMonth" runat="server" Text="Month:"> </asp:Label>
        <asp:DropDownList ID="ddlMonth" runat="server">       </asp:DropDownList>
        <asp:Label ID="lblyear" runat="server" Text="Year:"> </asp:Label>
        <asp:DropDownList ID="ddlYear" runat="server">       </asp:DropDownList>
        <br />
        <asp:Label ID="lblID" runat="server" Visible="False"></asp:Label><br />
        <asp:Button ID="btUpdate" runat="server" Text="Confirm And Update" onclick="btUpdate_Click" />
        <br />
    </asp:Panel>
</asp:Content>