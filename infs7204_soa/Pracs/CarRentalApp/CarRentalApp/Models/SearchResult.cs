using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class SearchResult
    {
        public int vehicle_id { get; set; }
        public string registration_number { get; set; }
        public string manufacturer_name { get; set; }
        public string model_code { get; set; }
        public string manufacturing_date { get; set; }
        public decimal daily_hire_rate { get; set; }
        public int availability { get; set; }
    }
}