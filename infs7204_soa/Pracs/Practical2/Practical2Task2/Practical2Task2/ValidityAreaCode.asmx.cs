using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Practical2Task2
{
    /// <summary>
    /// Summary description for ValidityAreaCode
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class ValidityAreaCode : System.Web.Services.WebService
    {

        [WebMethod]
        public bool IsValid(string state, string code)
        {
            if (((state == "NSW" || state == "ACT") && code == "02") ||
                ((state == "VIC" || state == "TAS") && code == "03") ||
                ((state == "QLD" && code == "07")) ||
                ((state == "WA" || state == "SA" || state == "NT") && code == "08"))
                return true;
            return false;
        }
    }
}
