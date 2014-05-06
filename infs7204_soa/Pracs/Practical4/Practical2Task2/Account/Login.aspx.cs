using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;

namespace Phonebook.Account
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            RegisterHyperLink.NavigateUrl = "Register.aspx?ReturnUrl=" + HttpUtility.UrlEncode(Request.QueryString["ReturnUrl"]);
        }


        protected void ButtonLogin_Click(object sender, EventArgs e)
        {
          /*  int count = 0;
                 UserAccountService uas = new UserAccountService();
                 IQueryable uid = uas.login(LoginUser.UserName, LoginUser.Password);
                 foreach (LoginInformation row in uid)
                 {
                     count++;
                     this.Session["login"] = row.ID;
                 }
                 if (count == 1)
                 {
                     FormsAuthentication.RedirectFromLoginPage(LoginUser.UserName, LoginUser.RememberMeSet);
                 }*/

        }
    }
}
