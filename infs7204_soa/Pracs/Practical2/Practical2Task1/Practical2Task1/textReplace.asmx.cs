using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Text.RegularExpressions;

namespace Practical2Task1
{
    /// <summary>
    /// Summary description for textReplace
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/",Name="TextReplace", Description="Replacing word service")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class TextReplace : System.Web.Services.WebService
    {

        [WebMethod]
        public string textReplace(out int count, string source, string from, string to)
        {
             count = new Regex(from).Matches(source).Count;
            return source.Replace(from, to);
        }
    }
}
