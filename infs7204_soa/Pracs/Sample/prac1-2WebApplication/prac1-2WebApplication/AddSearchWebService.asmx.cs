using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.IO;

namespace prac1_2WebApplication
{
    /// <summary>
    /// Summary description for AddSearchWebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class AddSearchWebService : System.Web.Services.WebService
    {

        [WebMethod]
        public void AddContact(string fn, string ln, string ac, string phn, string addr,string st,string em, string dob)
        {
            FileStream fs = File.Create("C:/[MyFiles]/contact.txt");
            StreamWriter sw = new StreamWriter(fs);
            sw.WriteLine(fn + "#" + ln + "#" + ac + "#" + phn + "#" + addr + "#" + st + "#" + em + "#" + dob);
            sw.Close();
            fs.Close();
        }
        [WebMethod]
        public string SearchByName(string ln)
        {
            string contactInfo = "";
            string phoneNO = "";
            string lastname = "";
            FileStream fs = File.OpenRead("C:/[MyFiles]/contact.txt");
            StreamReader sr = new StreamReader(fs);
            contactInfo = sr.ReadLine();
            sr.Close();
            fs.Close();
            int indexCount = 0;
            for (int i = 0; i < contactInfo.Length; i++)
            {
                if (contactInfo[i] == '#')
                {
                    indexCount++;
                    if (indexCount != 2)
                        lastname = "";
                    else
                        break;
                }
                else
                    lastname += contactInfo[i].ToString();
            }
            if (lastname == ln)
            {
                indexCount = 0;
                for (int i = 0; i < contactInfo.Length; i++)
                {
                    if (contactInfo[i] == '#')
                    {
                        indexCount++;
                        if (indexCount != 4)
                            phoneNO = "";
                        else
                            break;
                    }
                    else
                        phoneNO += contactInfo[i].ToString();
                }
                return phoneNO;
            }
            else
                return "";
        }

        [WebMethod]
        public string SearchByPhone(string phn)
        {
            string contactInfo = "";
            string phoneNO = "";
            string lastname = "";
            FileStream fs = File.OpenRead("C:/[MyFiles]/contact.txt");
            StreamReader sr = new StreamReader(fs);
            contactInfo = sr.ReadLine();
            sr.Close();
            fs.Close();
            int indexCount = 0;
            for (int i = 0; i < contactInfo.Length; i++)
            {
                if (contactInfo[i] == '#')
                {
                    indexCount++;
                    if (indexCount != 4)
                        phoneNO = "";
                    else
                        break;
                }
                else
                    phoneNO += contactInfo[i].ToString();
            }
            if (phoneNO == phn)
            {
                indexCount = 0;
                for (int i = 0; i < contactInfo.Length; i++)
                {
                    if (contactInfo[i] == '#')
                    {
                        indexCount++;
                        if (indexCount != 2)
                            lastname = "";
                        else
                            break;
                    }
                    else
                        lastname += contactInfo[i].ToString();
                }
                return lastname;
            }
            else
                return "";
        }
    }
}
