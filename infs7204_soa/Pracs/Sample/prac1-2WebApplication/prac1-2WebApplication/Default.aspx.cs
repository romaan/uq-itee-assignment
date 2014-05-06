using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace prac1_2WebApplication
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            HttpCookie DOBCookie = new HttpCookie("DOBCookie");
            DOBCookie = Request.Cookies["DOBCookie"];
            // Read the cookie information and display it.
            if (DOBCookie != null)
            {
                if (DateTime.Now.Day.ToString() + "/" + DateTime.Now.Month.ToString() == DOBCookie.Value)
                    Response.Redirect("RemindPage.aspx", true);
            }
        }

        protected void btnSubmit_Click(object sender, EventArgs e)
        {
            AreaCodeValidatorRef.AreaCodeValidatorWebService ac = new AreaCodeValidatorRef.AreaCodeValidatorWebService();
            if (ac.IsValid(DropDownListAreaCode.SelectedValue, DropDownListState.SelectedValue))
            {
                lblAreaCodeError.Text = "";
                if (CheckBoxRemind.Checked)
                {
                    //add Date of Birth in a Cookie
                    HttpCookie DOBCookie = new HttpCookie("DOBCookie", DropDownListDay.SelectedValue + "/" + DropDownListMonth.SelectedValue);
                    // Set the cookie expiration date.
                    DOBCookie.Expires = DateTime.Now.AddMonths(1);
                    // Add the cookie.
                    Response.Cookies.Add(DOBCookie);
                    //Response.Write("<p> The cookie has been written.");
                }
                else
                {
                    //Delete Date of Birth in a Cookie
                    HttpCookie DOBCookie = new HttpCookie("DOBCookie");
                    // Set the cookie expiration date.
                    DOBCookie.Expires = DateTime.Now.AddMonths(-1);
                    // Add the cookie.
                    Response.Cookies.Add(DOBCookie);
                    //Response.Write("<p> The cookie has been written.");
                }
                //save in file
                AddSearchRef.AddSearchWebService a = new AddSearchRef.AddSearchWebService();
                a.AddContact(txtboxFirstname.Text, txtboxLastname.Text, DropDownListAreaCode.SelectedValue, txtboxPhoneNO.Text, txtboxAddr.Text, DropDownListState.SelectedValue, txtboxEmail.Text, DropDownListDay.SelectedValue + "/" + DropDownListMonth.SelectedValue + "/" + DropDownListYear.SelectedValue);
                //
                Response.Redirect("SearchPage.aspx", true);
            }
            else
                lblAreaCodeError.Text = "The selected area code is not valid in the selected state!";
        }
    }
}
