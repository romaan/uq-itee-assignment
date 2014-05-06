using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Practical1
{
    /// <summary>
    /// Summary description for CurrentTimeService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name="CurrentTimeService", Description="Current Time Service Description")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class CurrentTimeService : System.Web.Services.WebService
    {

        [WebMethod]
        public string currentTime()
        {
            return DateTime.Now.TimeOfDay.Hours+":"+DateTime.Now.TimeOfDay.Minutes+":"+DateTime.Now.TimeOfDay.Seconds;
        }
    }
}
