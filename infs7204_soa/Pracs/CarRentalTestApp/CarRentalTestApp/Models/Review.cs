using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarRentalTestApp.Models
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
    }

    public class Review
    {
        public ReviewModel getReview(int vehicle_id)
        {
            LINQDBDataContext dc = new LINQDBDataContext();
            List<ReviewModel> rm = (from v in dc.Vehicles
                                    join mdl in dc.Models on v.model_code equals mdl.model_code
                                    join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                                    join mf in dc.Manufacturers on v.manufacturer_code equals mf.manufacturer_code
                                    where v.vehicle_id == vehicle_id
                                    select new ReviewModel { registration_number = v.registration_number, manufacturer_name = mf.manufacturer_name, model_code = v.model_code, body_style = mdl.body_style,
                                    automatic = mdl.automatic, passenger_capacity = mdl.passenger_capacity, vehicle_category_description = vc.vehicle_category_description, manufacturing_date = v.manufacturing_date,
                                    availability = v.availability, daily_rental_rate = v.daily_hire_rate}).ToList();
            
            return rm[0];
        }
    } 
}