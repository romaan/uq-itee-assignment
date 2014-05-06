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
            try
            {                
                if ((int)this.Session["login"] < 0)
                    Response.Redirect("Login.aspx");
            }
            catch (Exception)
            {
                Response.Redirect("Login.aspx");
            }

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
            IQueryable<ContactInformation> result;
            if (ddlSearchBy.Text == "Full Name")
            {
                string []words = txtSearchCriteria.Text.Split(' ');
                if (words.Length != 2)
                    lblResult.Text = "Please enter full Name";
                else
                {
                    result = ps.searchByFullName(words[0], words[1], (int)this.Session["login"]);
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
                    result = ps.searchByPhone(txtSearchCriteria.Text, (int)this.Session["login"]);
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
            if (ddlSearchBy.SelectedItem.Value == "Full Name")
                lblSearchName.Text = "Full Name:";
            else 
                lblSearchName.Text = "Phone:";
        }

        protected void panelResult(IQueryable<ContactInformation> results)
        {
            foreach (ContactInformation result in results)
            {
                lblID.Text = result.ID.ToString();
                txtFirstName.Text = result.FirstName;
                txtLastname.Text = result.LastName;
                ddlAreaCode.SelectedValue = result.AreaCode;
                txtPhoneNumber.Text = result.PhoneNumber;
                txtAddress.Text = result.Address;
                ddlState.Text = result.State;
                txtEmail.Text = result.Email;
                string[] date = result.Dob.ToString().Split('/');
                ddlDate.SelectedValue = date[1];
                ddlMonth.SelectedValue = date[0];
                string[] year = date[2].Split(' ');
                ddlYear.SelectedValue = year[0];
                PanelResult.Visible = true;
            }
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
                int date, month, year, id;
                int.TryParse(ddlDate.SelectedItem.Value, out date);
                int.TryParse(ddlMonth.SelectedItem.Value, out month);
                int.TryParse(ddlYear.SelectedItem.Value, out year);
                int.TryParse(lblID.Text, out id);
                bool updateStatus = ac.updateContact(txtFirstName.Text, txtLastname.Text, ddlAreaCode.SelectedItem.Value, txtPhoneNumber.Text, txtAddress.Text, ddlState.SelectedItem.Value, txtEmail.Text, new DateTime(year, month, date), id);
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