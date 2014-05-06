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
        public bool addContact(string txtFirstName, string txtLastname, string ddlTitle, 
                               string ddlAreaCode, string txtPhoneNumber, string txtAddress, 
                               string ddlState, string txtEmail, string txtComment,
                               bool chkRemember, DateTime dob, int userID)
        {
            LINQDatabaseService ds = new LINQDatabaseService();
            if (ds.fullNameExists(txtFirstName, txtLastname, userID) == false && ds.phoneExists(txtPhoneNumber, userID) == false && 
                ds.saveContact( txtFirstName,  txtLastname,  ddlTitle, 
                                ddlAreaCode,  txtPhoneNumber,  txtAddress, 
                                ddlState,  txtEmail,  txtComment,
                                chkRemember, dob, userID))
                return true;            
            else
                return false;
            
        }

        [WebMethod]
        public bool updateContact(string txtFirstName, string txtLastname, string ddlAreaCode, string txtPhoneNumber, string txtAddress, string ddlState, string txtEmail, DateTime dob, int lblID)
        {
            LINQDatabaseService ds = new LINQDatabaseService();            
            return ds.updateContact(txtFirstName, txtLastname, ddlAreaCode, txtPhoneNumber, txtAddress, ddlState, txtEmail, dob, lblID);                
        }

        [WebMethod]
        public IQueryable<ContactInformation> searchByFullName(string firstName, string lastName, int userID) 
        {
            LINQDatabaseService ds = new LINQDatabaseService();
            if (ds.fullNameExists(firstName, lastName, userID) == true)
                return ds.getContact(new string[] {firstName, lastName}, "Name", userID);
            return null;
        }

        [WebMethod]
        public IQueryable<ContactInformation> searchByPhone(string phone, int userID)
        {
            LINQDatabaseService ds = new LINQDatabaseService();
            if (ds.phoneExists(phone, userID) == true)
                return ds.getContact(new string[] { phone }, "Phone", userID);
            return null;
        }
        
    }
}
