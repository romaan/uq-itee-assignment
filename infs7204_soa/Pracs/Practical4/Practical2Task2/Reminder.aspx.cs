using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace Phonebook
{
    public partial class Reminder : System.Web.UI.Page
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

            int count = 0;
            lblReminder.Text = "";
            LINQDatabaseService ds = new LINQDatabaseService();
            IQueryable<ContactInformation> folks = ds.remindBirthday();
            foreach (ContactInformation person in folks) {
                lblReminder.Text += "<br/>First Name:" + person.FirstName + "                LastName:" + person.LastName + "              Email:" + person.Email;
                count++;
            }
            if (count == 0)
            {
                lblReminder.Text = "No Birthdays for today";
            }
        }
    }
}