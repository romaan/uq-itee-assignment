using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.IO;
using System.Text.RegularExpressions;

namespace Practical2Task2
{
    /// <summary>
    /// Summary description for AddContact
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name="PhoneService", Description="Saving contacts in file")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class PhoneService : System.Web.Services.WebService
    {
                FileStream aStream;        
        [WebMethod]
        public int addContact(List<string> content)
        {
            FileStream aStream;
            StreamWriter sw;
                aStream = new FileStream("C:\\Info\\info.txt", FileMode.Append);
                sw = new StreamWriter(aStream);
            foreach (string anInfo in content)
            {
                sw.Write(":"+anInfo);
            }
            sw.WriteLine(":");
            sw.Close();
            aStream.Close();
        
            
            return 0;
        }

        [WebMethod]
        public string searchByName(string lastName)
        {
            return search(lastName, "lastname");
        }

        [WebMethod]
        public string searchByPhone(string phone)
        {
            return search(phone, "phone");
        }

        private string search(string lastName, string type)
        {

            String aLine;
            String result = null;
                try {
                aStream = new FileStream("C:\\Info\\info.txt", FileMode.OpenOrCreate);
               
                StreamReader sw = new StreamReader(aStream);
                while ((aLine = sw.ReadLine()) != null)
                {
                    String[] info = aLine.Split(':');
                    for(int i = 0; i < info.Length; i++)
                        if (type == "lastname" && info[2] == lastName) {
                            result = info[3] + " " + info[4];
                        }
                        else if (type == "phone" && info[4] == lastName)
                        {
                            result =  info[1] + " " + info[2];
                        }
                }
                sw.Close();
                aStream.Close();
                }
                catch (Exception ex)
                {
              
                }             
            return result;
        }
    }
}
