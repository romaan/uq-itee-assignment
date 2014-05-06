using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.IO;
using System.Text.RegularExpressions;

namespace Phonebook
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
        [WebMethod]
        public bool addContact(List<string> content)
        {
            DatabaseService ds = new DatabaseService();
            if (ds.fullNameExists(content[0], content[1]) == false && ds.phoneExists(content[4]) == false && ds.saveContact(content) != 0)
                return true;            
            else
                return false;
            
        }

        [WebMethod]
        public bool updateContact(List<string> content)
        {
            DatabaseService ds = new DatabaseService();
            //ds.fullNameExists(content[0], content[1]) == false && ds.phoneExists(content[3]) == false &&
            if (ds.updateContact(content) != 0) {                
                return true;
            }
            else
                return false;

        }

        [WebMethod]
        public List<string> searchByFullName(string firstName, string lastName) 
        {
            DatabaseService ds = new DatabaseService();
            if (ds.fullNameExists(firstName, lastName) == true)
                return ds.getContact(new string[]{firstName, lastName}, "Name");
                return null;
        }

        [WebMethod]
        public List<string> searchByPhone(string phone)
        {
            DatabaseService ds = new DatabaseService();
            if (ds.phoneExists(phone) == true)
                return ds.getContact(new string[] { phone }, "Phone");
            return null;
        }
        
    }
}
