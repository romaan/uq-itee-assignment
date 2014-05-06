using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace prac1_2WebApplication
{
    public partial class SearchPage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                PanelResult.Visible=PanelPhoneNoSearch.Visible = false;
                PanelLastnameSearch.Visible = true;
            }
        }

        protected void btnSearch_Click(object sender, EventArgs e)
        {
            PanelResult.Visible = true;
            AddSearchRef.AddSearchWebService s = new AddSearchRef.AddSearchWebService();
            switch (DropDownList1.SelectedValue)
            {
                case "Phone Number":
                    {
                        if (s.SearchByPhone(txtboxSearchPhoneNO.Text) != "")
                            lblResultFN.Text = s.SearchByPhone(txtboxSearchPhoneNO.Text);
                        else
                            lblResultFN.Text = "Not Found!";
                        lblResultPhN.Text = txtboxSearchPhoneNO.Text;
                        break;
                    }
                case "Last name":
                    {
                        if (s.SearchByName(txtboxSearchLastname.Text) != "")
                            lblResultPhN.Text = s.SearchByName(txtboxSearchLastname.Text);
                        else
                            lblResultPhN.Text = "Not Found!";
                        lblResultFN.Text = txtboxSearchLastname.Text;
                        break;
                    }
            }
        }

        protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
        {
            switch (DropDownList1.SelectedValue)
            {
                case "Phone Number":
                    {
                        PanelPhoneNoSearch.Visible = true;
                        PanelLastnameSearch.Visible = false;
                        break;
                    }
                case "Last name":
                    {
                        PanelPhoneNoSearch.Visible = false;
                        PanelLastnameSearch.Visible = true;
                        break;
                    }
            }
        }
    }
}