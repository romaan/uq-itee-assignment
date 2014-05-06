using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Practical1
{
    /// <summary>
    /// Summary description for CalculateAgeService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class CalculateAgeService : System.Web.Services.WebService
    {

        [WebMethod]
        public int calculateAge(string dob)
        {
            int numDob, currentYear;
            int.TryParse(dob, out numDob);
            int.TryParse(DateTime.Now.Year.ToString(), out currentYear);
            return  currentYear - numDob;
        }
    }
}
