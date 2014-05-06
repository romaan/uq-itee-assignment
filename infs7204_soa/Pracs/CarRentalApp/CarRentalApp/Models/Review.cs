using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class ReviewModel
    {
        public string registration_number { get; set; }
        public string manufacturer_name { get; set; }
        public string model_code { get; set; }
        public string body_style { get; set; }
        public bool automatic { get; set; }
        public int passenger_capacity { get; set; }
        public string vehicle_category_description { get; set; }
        public string manufacturing_date { get; set; }
        public int availability { get; set; }
        public decimal daily_rental_rate { get; set; }
        public decimal? engine_power { get; set; }
    }
}