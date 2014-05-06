using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalTestApp.Models
{
    public class Payment
    {
        public string cardtype { get; set; }
        public string cardname { get; set; }
        public string cardnumber { get; set; }
        public string securitynumber { get; set; }
        public string expirydate { get; set; }
    }
}