using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace prac2_1WebApplication
{
    /// <summary>
    /// Summary description for wordReplace
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class wordReplace : System.Web.Services.WebService
    {

        [WebMethod]
        public string replace(string oldWord, string newWord, string text)
        {
            text=text.Replace(oldWord, newWord);
            return text;
        }
    }
}
