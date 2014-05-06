using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class Booking
    {
        public int booking_id { get; set; }
        public string booking_status_code { get; set; }
        public int customer_id { get; set; }
        public int vehicle_id { get; set; }
        public DateTime pickup_date { get; set; }
        public DateTime return_date { get; set; }
        public string pickup_city { get; set; }
        public string return_city { get; set; }
    }
}