using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class Vehicle
    {
        public int vehicle_id { get; set; }
        public string registration_number { get; set; }
        public string manufacturer_code { get; set; }
        public string model_code { get; set; }
        public string vehicle_category_code { get; set; }
        public string manufacturing_date { get; set; }
        public double daily_hire_date { get; set; }
        public int availability { get; set; }
        public string current_city { get; set; }
    }
}