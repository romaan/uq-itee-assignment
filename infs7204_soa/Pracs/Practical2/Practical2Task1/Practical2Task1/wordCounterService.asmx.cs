using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;


namespace Practical2Task1
{
    /// <summary>
    /// Summary description for webCounterService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name="WordCount", Description="Counting number of words")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class webCounterService : System.Web.Services.WebService
    {
        [WebMethod]
        public int countWord(string text)
        {
            string [] collection = text.Split(' ');
            return collection.Length;
        }
    }
}
