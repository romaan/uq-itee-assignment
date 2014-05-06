using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using System.IO;

namespace prac1_2WebApplication
{
    public partial class RemindPage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string contactInfo = "";
            string fullname = "";
            FileStream fs = File.OpenRead("C:/[MyFiles]/contact.txt");
            StreamReader sr = new StreamReader(fs);
            contactInfo = sr.ReadLine();
            sr.Close();
            fs.Close();
            int i;
            for (i = 0; i < contactInfo.Length && contactInfo[i]!='#'; i++)
                    fullname += contactInfo[i].ToString();
            fullname+=" ";
            for (int j = i+1; j < contactInfo.Length && contactInfo[j]!='#'; j++)
                    fullname += contactInfo[j].ToString();
            lblRemindName.Text = fullname;
        }
    }
}