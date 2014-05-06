using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Text.RegularExpressions;

namespace Practical2Task1
{
    /// <summary>
    /// Summary description for wordOccurrenceCounter
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name="WordOccurence", Description="Counting particular word occurences")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class wordOccurrenceCounter : System.Web.Services.WebService
    {

        [WebMethod]
        public int wordOccurenceCounter(string text, string word)
        {
            return new Regex(word).Matches(text).Count;
        }
    }
}
