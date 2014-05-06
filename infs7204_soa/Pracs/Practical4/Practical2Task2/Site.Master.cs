using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Phonebook
{
    public partial class SiteMaster : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {

                if ((int)this.Session["login"] > 0)
                    NavigationMenu.Items[3].Text = "Logout";
                else
                    NavigationMenu.Items[3].Text = "Login";
                

            }
            catch (Exception)
            {
                NavigationMenu.Items[3].Text = "Login";
            }
        }
    
    }
}
