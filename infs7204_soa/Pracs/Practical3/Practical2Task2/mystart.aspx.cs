using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace Phonebook
{
    public partial class mystart : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            DatabaseService ds = new DatabaseService();
            List<List<string>> folks = ds.remindBirthday();
            if (folks.Count != 0)
            {
                Response.Redirect("Reminder.aspx");
            }
            else
            {
                Response.Redirect("Default.aspx");
            }
        }
    }
}