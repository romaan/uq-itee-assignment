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
                List<string> content = new List<string> { txtFirstName.Text, txtLastname.Text, ddlTitle.SelectedItem.Value, ddlAreaCode.SelectedItem.Value, txtPhoneNumber.Text, txtAddress.Text, ddlState.SelectedItem.Value, txtEmail.Text, ddlYear.Text+"-"+ddlMonth.Text+"-"+ddlDate.Text, chkRemember.Checked.ToString(), txtComment.Text };               
                bool saveStatus = ac.addContact(content);
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

    }
}
