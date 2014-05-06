using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Phonebook
{
    public partial class Login : System.Web.UI.Page
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                this.Session["login"] = -1;
                this.Session.Timeout = 0;
                this.Session.Abandon();              
            }
            catch (Exception)
            {
                
            }
        }

        protected void btLogin_Click(object sender, EventArgs e)
        {
            UserAccountService uas = new UserAccountService();
            int userid = uas.login(txtUserName.Text, txtPassword.Text);
            if (userid == -1)
            {
                lblResult.Text = "Incorrect username or password";
            }
            else
            {
                lblResult.Text = "";
                this.Session["login"] = userid;
                Response.Redirect("Default.aspx");
            }
        }
    }
}