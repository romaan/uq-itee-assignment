using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Phonebook
{
    /// <summary>
    /// Summary description for LINQDatabaseService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/",Name="LINQDatabaseService", Description="Linq DB Service")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class LINQDatabaseService : System.Web.Services.WebService
    {

        [WebMethod]
        public bool fullNameExists(string firstName, string lastName, int userID)
        {        
            LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
            var count = (from p in ci.ContactInformations
                                 where p.FirstName == firstName && p.LastName == lastName && p.UserID == userID
                                 select p).Count();
           
            if (count > 0)
                return true;
            return false;
        }

        [WebMethod]
        public bool phoneExists(string phone, int userID)
        {
            LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
            var count = (from p in ci.ContactInformations
                         where p.PhoneNumber == phone && p.UserID == userID
                         select p).Count();

            if (count > 0)
                return true;
            return false;
        }
        
        [WebMethod]
        public bool saveContact(string txtFirstName, string txtLastname, string ddlTitle, 
                               string ddlAreaCode, string txtPhoneNumber, string txtAddress, 
                               string ddlState, string txtEmail, string txtComment,
                               bool chkRemember, DateTime dob, int userID)
        {

            try
            {
                LINQContactInformationDataContext cid = new LINQContactInformationDataContext();
                ContactInformation ci = new ContactInformation
                {
                    FirstName = txtFirstName,
                    LastName = txtLastname,
                    Title = ddlTitle,
                    AreaCode = ddlAreaCode,
                    PhoneNumber = txtPhoneNumber,
                    Address = txtAddress,
                    State = ddlState,
                    Email = txtEmail,
                    Comment = txtComment,
                    RememberDob = chkRemember,
                    Dob = dob,
                    UserID = userID
                };
                cid.ContactInformations.InsertOnSubmit(ci);
                cid.SubmitChanges();
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }

        
        [WebMethod]
        public bool updateContact(string txtFirstName, string txtLastname, string ddlAreaCode, string txtPhoneNumber, string txtAddress, string ddlState, string txtEmail, DateTime dob, int lblID)
        {
            try
            {
                LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
                IQueryable<ContactInformation> results = (from p in ci.ContactInformations
                                             where p.ID == lblID
                                             select p);

                foreach (ContactInformation result in results)
                {
                    result.FirstName = txtFirstName;
                    result.LastName = txtLastname;
                    result.AreaCode = ddlAreaCode;
                    result.PhoneNumber = txtPhoneNumber;
                    result.Address = txtAddress;
                    result.State = ddlState;
                    result.Email = txtEmail;
                    result.Dob = dob;                    
                    ci.SubmitChanges();
                }
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }
        
        [WebMethod]
        public IQueryable<ContactInformation> getContact(string[] value, string type, int userID)
        {
            if (type == "Name")
            {
                LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
                var result = (from p in ci.ContactInformations
                              where p.FirstName == value[0] && p.LastName == value[1] && p.UserID == userID
                              select p);
                return result;
            }
            else if (type == "Phone")
            {
                LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
                var result = (from p in ci.ContactInformations
                              where p.PhoneNumber == value[0]
                              select p);
                return result;
            }
            return null;
        }

        [WebMethod]
        public IQueryable<ContactInformation> remindBirthday()
        {
            LINQContactInformationDataContext ci = new LINQContactInformationDataContext();
            var result = (from p in ci.ContactInformations
                          where p.RememberDob == true && p.Dob.Month == DateTime.Now.Month && p.Dob.Day == DateTime.Now.Day && p.UserID == (int)this.Session["login"]
                          select p);

            return result;                       
        }

    }
}
