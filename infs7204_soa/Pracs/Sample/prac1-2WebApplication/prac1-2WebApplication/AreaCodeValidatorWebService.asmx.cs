using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace prac1_2WebApplication
{
    /// <summary>
    /// Summary description for AreaCodeValidatorWebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class AreaCodeValidatorWebService : System.Web.Services.WebService
    {

        [WebMethod]
        public bool IsValid(string ac, string st)
        {
            switch (ac)
            {
                case "02":
                    {
                        if (st == "NSW" || st == "ACT")
                            return true;
                        break;
                    }
                case "03":
                    {
                        if (st == "VIC" || st == "TAS")
                            return true;
                        break;
                    }
                case "07":
                    {
                        if (st == "QLD")
                            return true;
                        break;
                    }
                case "08":
                    {
                        if (st == "WA" || st == "SA" || st=="NT")
                            return true;
                        break;
                    }
            }
            return false;
        }
    }
}
