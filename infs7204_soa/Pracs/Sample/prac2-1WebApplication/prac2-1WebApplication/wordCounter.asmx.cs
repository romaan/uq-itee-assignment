using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace prac2_1WebApplication
{
    /// <summary>
    /// Summary description for wordHighlighter
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class wordHighlighter : System.Web.Services.WebService
    {

        [WebMethod]
        public int wordCounter(string word, string text)
        {
            int result = 0;
                for (int j = 0; j < text.Length; j++)
                {
                    if (word[0] == text[j] && j+word.Length<=text.Length)
                    {
                        string temp = text.Substring(j, word.Length);
                        if (word == temp)
                        {
                            result++;
                            j += (word.Length-1);
                        }
                    }

                }
            return result;
        }
    }
}
