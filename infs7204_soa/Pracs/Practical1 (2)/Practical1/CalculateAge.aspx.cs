using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace Practical1
{
    public partial class CurrentAge : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)        {

        }

        protected void btCalcAge_Click(object sender, EventArgs e)
        {
            Age.CalculateAgeService calcAgeService = new Age.CalculateAgeService();
            lblAge.Text = "You are:" + calcAgeService.calculateAge(txtDoB.Text) + " years old!";
        }
    }
}