using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class Payment
    {
        public string cardtype { get; set; }
        public string cardname { get; set; }
        public string cardnumber { get; set; }
        public string securitynumber { get; set; }
        public string expirydate { get; set; }
    }

    public class PaymentModel
    {
        public string card_type { get; set; }
        public string card_name { get; set; }
        public string card_number { get; set; }
        public string security_number { get; set; }
        public string expiry_date { get; set; }
    }
}