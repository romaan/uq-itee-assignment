using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Phonebook
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {                
                if ((int)this.Session["login"] < 0)
                    Response.Redirect("Login.aspx");   
            }
            catch (Exception)
            {
                Response.Redirect("Login.aspx");
            }

            for (int date = 1; date <= 31; date++)
                ddlDate.Items.Add(new ListItem(date.ToString()));
            for (int year = 2012; year >= 1950; year--)
                ddlYear.Items.Add(new ListItem(year.ToString()));
            List<String> months = new List<string> { "Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Aug","Nov","Dec" };
            foreach (string month in months)
            {
            ddlMonth.Items.Add(new ListItem(month));
            }
        }

        protected void btSave_Click(object sender, EventArgs e)
        {
            lblAreaError.Text = "";
            ValidityAreaCode v = new ValidityAreaCode();
            if (v.IsValid(ddlState.SelectedItem.Value, ddlAreaCode.SelectedItem.Value) == false)
            {
                lblAreaError.Text = "The selected area code is not valid in the selected state!";
            }
            else
            { 
                PhoneService ac = new PhoneService();
                int date, month, year;
                int.TryParse(ddlDate.SelectedItem.Value, out date);
                month = getValue(ddlMonth.SelectedItem.Value);
                int.TryParse(ddlYear.SelectedItem.Value, out year);
                bool saveStatus = ac.addContact(txtFirstName.Text, txtLastname.Text, ddlTitle.SelectedItem.Value, 
                                                ddlAreaCode.SelectedItem.Value, txtPhoneNumber.Text, txtAddress.Text, 
                                                ddlState.SelectedItem.Value, txtEmail.Text, txtComment.Text,
                                                chkRemember.Checked,  new DateTime(year, month, date), (int)this.Session["login"]);
                if (saveStatus)
                {
                    lblResult.Text = "Contact Saved";
                }
                else
                {
                    lblResult.Text = "Unable to save contact information. Same name or number exists";
                }
            }
        }

        int getValue(string aMonth)
        {
            switch (aMonth)
            {
                case "Jan": return 1;
                case "Feb": return 2;
                case "Mar": return 3;
                case "Apr": return 4;
                case "May": return 5;
                case "Jun": return 6;
                case "Jul": return 7;
                case "Aug": return 8;
                case "Sep": return 9;
                case "Oct": return 10;
                case "Nov": return 11;
                default: return 12;
            }
        }
    }
}
