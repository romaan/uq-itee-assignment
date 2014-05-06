using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace CarRentalTestApp
{
    /// <summary>
    /// Summary description for CardVerification
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class CardVerification : System.Web.Services.WebService
    {
        [WebMethod]
        public ReturnIndicator CheckCC(string CardNumber)
        {
            ReturnIndicator ri = new ReturnIndicator();
            ri.CardValid = false;


            byte[] number = new byte[16]; // number to validate

            // Remove non-digits
            int len = 0;
            for (int i = 0; i < CardNumber.Length; i++)
            {
                if (char.IsDigit(CardNumber, i))
                {
                    if (len == 16) return ri; // number has too many digits
                    number[len++] = byte.Parse(CardNumber[i].ToString());
                }
            }

            // Use Luhn Algorithm to validate
            int sum = 0;
            for (int i = len - 1; i >= 0; i--)
            {
                if (i % 2 == len % 2)
                {
                    int n = number[i] * 2;
                    sum += (n / 10) + (n % 10);
                }
                else
                    sum += number[i];
            }

            ri.CardValid = (bool)(sum % 10 == 0);
            if ((ri.CardValid == true))
            {
                switch (CardNumber.Substring(0, 1))
                {
                    case "3":
                        ri.CardType = "AMEX/Diners Club/JCB";
                        break;
                    case "4":
                        ri.CardType = "VISA";
                        break;
                    case "5":
                        ri.CardType = "MasterCard";
                        break;
                    case "6":
                        ri.CardType = "Discover";
                        break;
                    default:
                        ri.CardType = "Unknown";
                        break;
                }
            }
            else
            {
                ri.CardType = "NONE";
            }
            return ri;
        }


        public class ReturnIndicator
        {
            public string CardType;
            public bool CardValid;
        }
    }
}
