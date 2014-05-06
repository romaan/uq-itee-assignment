using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalTestApp.Models
{
    public class Receipt
    {
        public string customer_id { get; set; }
        public string first_name { get; set; }
        public string last_name { get; set; }
        public string age { get; set; }
        public char gender { get; set; }
        public string email { get; set; }
        public string phone_number { get; set; }
        public string address { get; set; }
        public string city { get; set; }
        public string vehicle_id { get; set; }
        public string pickupdate { get; set; }
        public string dropoffdate { get; set; }
        public string returncity { get; set; }
        public string pickupcity { get; set; }
        public string card_type { get; set; }
        public string card_name { get; set; }
        public string card_number { get; set; }
        public string security_number { get; set; }
        public string expiry_date { get; set; }
    }
}