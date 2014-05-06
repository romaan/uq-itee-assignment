using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Phonebook.Account
{
    public partial class Register : System.Web.UI.Page
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                if ((int)this.Session["login"] > 0)
                    Response.Redirect("Default.aspx");
            }
            catch (Exception)
            {

            }
        }

        protected void btRegister_Click(object sender, EventArgs e)
        {
            UserAccountService uas = new UserAccountService();
            if (uas.UserExists(txtUserName.Text) || txtPassword.Text != txtConfirm.Text)
                lblResult.Text = "UserName already exists or Password Mistach";
            else
            {
                uas.CreateAccount(txtUserName.Text, txtPassword.Text);
                Response.Redirect("Login.aspx");
            }
        }
    }
}