﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalTestApp.Models
{
    public class Transfer
    {
        public int customer_id { get; set; }       
        public string first_name { get; set; }
        public string last_name { get; set; }
        public int age { get; set; }
        public char gender { get; set; }
        public string email { get; set; }       
        public string phone_number { get; set; }      
        public string address { get; set; }        
        public string city { get; set; }
        public string vehicle_id { get; set; }
        public string pickupdate { get; set;}
        public string dropoffdate {get; set; }
        public string returncity {get; set; }
        public string pickupcity { get; set; }
    }
}