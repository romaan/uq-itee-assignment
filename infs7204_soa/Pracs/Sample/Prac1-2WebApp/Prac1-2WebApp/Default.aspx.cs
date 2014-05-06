using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Prac1_2WebApp
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            AgeWebRef.WebService1 w=new AgeWebRef.WebService1();
            Label1.Text = "Your are " + w.CalculateAge(Convert.ToInt32(TextBox1.Text)).ToString() +"years old!";
        }
    }
}
