using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Text.RegularExpressions;

namespace prac2_1WebApplication
{
    /// <summary>
    /// Summary description for TextCounterWebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class TextCounterWebService : System.Web.Services.WebService
    {

        [WebMethod]
        public int textCounter(string strText)
        {
            int result = 0;
            //it first replaces every possible option with a space character
            strText = strText.Replace('\n', ' ');
            strText = strText.Replace('\r', ' ');
            strText=strText.Replace("\r\n"," ");
            strText = strText.Replace('\t', ' ');
            strText = strText.Replace('.', ' ');
            strText = strText.Replace(',', ' ');
            strText = strText.Replace(';', ' ');
            strText = strText.Replace('!', ' ');
            strText = strText.Replace('?', ' ');
            //and then plits the whole string based on the space character
            foreach (string s in strText.Split(' '))
            {
                if (s != "")
                    result++;
            }
            return result;
        }
    }
}
