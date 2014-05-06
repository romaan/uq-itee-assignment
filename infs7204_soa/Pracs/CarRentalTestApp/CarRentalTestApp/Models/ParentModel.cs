using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CarRentalTestApp.Models;

namespace CarRentalTestApp.Models
{
    public class ParentModel
    {
        public DatabaseWebRef.Customer customer { get; set;}
        public DatabaseWebRef.ReviewModel reviewmodel { get; set; }
        public DatabaseWebRef.Manufacturer manufacturer { get; set; }
        public DatabaseWebRef.Booking booking { get; set; }
        public Payment payment { get; set; }
    }
}