using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class ParentModel
    {
        public Customer customer { get; set; }
        public ReviewModel reviewmodel { get; set; }
        public Manufacturer manufacturer { get; set; }
        public CarRentalApp.Booking booking { get; set; }
        public Models.Payment payment { get; set; }
    }
}