using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalApp.Models
{
    public class Model
    {
        public string model_code { get; set; }
        public string body_style { get; set; }
        public bool automatic { get; set; }
        public int passenger_capacity { get; set; }
    }
}