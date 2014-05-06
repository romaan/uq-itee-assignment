using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Phonebook
{
    /// <summary>
    /// Summary description for UserAccountService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name = "UserAccount", Description = "User Account")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class UserAccountService : System.Web.Services.WebService
    {
        [WebMethod]
        public int login(string username, string password)
        {
            LINQUserInformationDataContext ui = new LINQUserInformationDataContext();
            IQueryable<int> count = (from p in ui.LoginInformations
                         where p.UserName == username && p.Password == password
                         select p.ID);
            
            foreach (int element in count)
            {
               return element;
            }
            return -1;
        }

        [WebMethod]
        public bool UserExists(string username)
        {
            LINQUserInformationDataContext ui = new LINQUserInformationDataContext();
            var count = (from p in ui.LoginInformations
                         where p.UserName == username
                         select p).Count();

            if (count > 0)
                return true;
            return false;
        }

        [WebMethod]
        public bool CreateAccount(string username, string password)
        {
            if (userExists(username) != 0)
                return false;
            try
            {
                LINQUserInformationDataContext ui = new LINQUserInformationDataContext();
                LoginInformation li = new LoginInformation();
                {
                    li.UserName = username;
                    li.Password = password;                         
                };
                ui.LoginInformations.InsertOnSubmit(li);
                ui.SubmitChanges();
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }

        private int userExists(string username)
        {
            LINQUserInformationDataContext ui = new LINQUserInformationDataContext();
            var count = (from p in ui.LoginInformations
                         where p.UserName == username
                         select p).Count();
            return count;
        }
    }
}
