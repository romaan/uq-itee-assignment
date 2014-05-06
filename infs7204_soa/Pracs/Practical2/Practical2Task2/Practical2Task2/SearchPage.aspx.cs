using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Practical2Task2
{
    public partial class SearchPage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (ddlSearchBy.Text == "Last Name")
                lblSearchName.Text = "Last Name:";
            else
                lblSearchName.Text = "Phone:";
        }

        protected void btSearch_Click(object sender, EventArgs e)
        {
            PhoneService ps = new PhoneService();
            String result;
            if (ddlSearchBy.Text == "Last Name")
            {
                result = ps.searchByName(txtSearchCriteria.Text);
                if (result == null)
                    lblResult.Text = "No result found";
                else
                    lblResult.Text = "Name:" + txtSearchCriteria.Text + "<br/>Phone:" + result;
            }
            else
            {
                result = ps.searchByPhone(txtSearchCriteria.Text);
                if (result == null)
                    lblResult.Text = "No result found";
                else
                    lblResult.Text = "Name:" + result + "<br/>Phone:" + txtSearchCriteria.Text;
            }
        }

        protected void ddlSearchBy_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ddlSearchBy.Text == "Last Name")
                lblSearchName.Text = "Last Name:";
            else 
                lblSearchName.Text = "Phone:";
        }
    }
}