using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Phonebook
{
    public partial class SearchPage : System.Web.UI.Page
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            PanelResult.Visible = false;
            btEdit.Visible = false;
            if (ddlSearchBy.Text == "Full Name")
                lblSearchName.Text = "Full Name:";
            else
                lblSearchName.Text = "Phone:";

            for (int date = 1; date <= 31; date++)
                ddlDate.Items.Add(new ListItem(date.ToString()));
            for (int year = 2012; year >= 1950; year--)
                ddlYear.Items.Add(new ListItem(year.ToString()));
            for (int month = 12; month >= 1; month--)
                ddlMonth.Items.Add(new ListItem(month.ToString()));
        }

        protected void btSearch_Click(object sender, EventArgs e)
        {
            PhoneService ps = new PhoneService();
            List<string> result;
            if (ddlSearchBy.Text == "Full Name")
            {
                string []words = txtSearchCriteria.Text.Split(' ');
                if (words.Length != 2)
                    lblResult.Text = "Please enter full Name";
                else
                {
                    result = ps.searchByFullName(words[0], words[1]);
                    if (result == null)
                    {
                        lblResult.Text = "No result found";
                        PanelResult.Visible = false;
                    }
                    else
                    {
                        lblResult.Text = "";
                        btEdit.Visible = true;
                        PanelResult.Enabled = false;
                        panelResult(result);
                    }
                }
            }
            else
            {
                if (txtSearchCriteria.Text.Length != 8)
                    lblResult.Text = "Phone Number Length Invalid";
                else
                {
                    result = ps.searchByPhone(txtSearchCriteria.Text);
                    if (result == null)
                    {
                        lblResult.Text = "No result found";
                        PanelResult.Visible = false;
                    }
                    else
                    {
                        lblResult.Text = "";
                        btEdit.Visible = true;
                        PanelResult.Enabled = false;
                        panelResult(result);
                    }
                }
            }
        }

        protected void ddlSearchBy_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ddlSearchBy.Text == "Last Name")
                lblSearchName.Text = "Last Name:";
            else 
                lblSearchName.Text = "Phone:";
        }

        protected void panelResult(List<string> result)
        {
            lblID.Text = result[0];
            txtFirstName.Text = result[1];
            txtLastname.Text = result[2];
            ddlAreaCode.SelectedValue = result[4];
            txtPhoneNumber.Text = result[5];
            txtAddress.Text = result[6];
            ddlState.Text = result[7];
            txtEmail.Text = result[8];            
            string [] date = result[9].Split('/');
            ddlDate.SelectedValue = date[1];
            ddlMonth.SelectedValue = date[0];
            string [] year = date[2].Split(' ');
            ddlYear.SelectedValue = year[0];
            PanelResult.Visible = true;
        }

        protected void btUpdate_Click(object sender, EventArgs e)
        {
            ValidityAreaCode v = new ValidityAreaCode();
            if (v.IsValid(ddlState.SelectedItem.Value, ddlAreaCode.SelectedItem.Value) == false)
            {
                lblResult.Text = "The selected area code is not valid in the selected state!";

            }
            else
            {
                PhoneService ac = new PhoneService();
                List<string> content = new List<string> { txtFirstName.Text, txtLastname.Text, ddlAreaCode.SelectedItem.Value, txtPhoneNumber.Text, txtAddress.Text, ddlState.SelectedItem.Value, txtEmail.Text, ddlYear.Text + "-" + ddlMonth.Text + "-" + ddlDate.Text, lblID.Text };
  

                bool updateStatus = ac.updateContact(content);
                if (updateStatus)
                {
                    lblResult.Text = "Contact Updated";
                }
                else
                {
                    lblResult.Text = "Unable to update contact information. Same name or number exists";
                }
            }
        }

        protected void btEdit_Click(object sender, EventArgs e)
        {
            PanelResult.Enabled = true;
            PanelResult.Visible = true;
        }
    }
}