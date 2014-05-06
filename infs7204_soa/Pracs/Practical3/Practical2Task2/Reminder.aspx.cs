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
            lblReminder.Text = "";
            DatabaseService ds = new DatabaseService();
            List<List<string>> folks = ds.remindBirthday();
            if (folks.Count == 0)
            {
                lblReminder.Text = "No Birthdays for today";
            }
            else
            {
                for (int i = 0; i < folks.Count; i++)
                {
                    List<string> person = folks[i];
                    lblReminder.Text = lblReminder.Text + "<br/>First Name:" + person[0] + " LastName:" + person[1] + "     Email:" + person[2];
                }
            }
        }
    }
}