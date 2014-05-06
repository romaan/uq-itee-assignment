using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace CarRentalTestApp.Models
{


    public class Search
    {
        LINQDBDataContext dc;

        public IEnumerable<VehicleCategory> getCategory()
        {
            dc = new LINQDBDataContext();
            List<VehicleCategory> vc = (from vct in dc.VehicleCategories
                                        select new VehicleCategory { vehicle_category_code = vct.vehicle_category_code.ToString(), vehicle_category_description = vct.vehicle_category_description.ToString() }).ToList();
           
            return vc;
        }

        public IEnumerable<Model> getBodyStyle()
        {
            dc = new LINQDBDataContext();
            List<Model> bs = (from mdt in dc.Models                                      
                              select new Model { body_style = mdt.body_style}).Distinct().ToList();

            return bs;
        }

        public IEnumerable<Vehicle> getPickUp()
        {
            dc = new LINQDBDataContext();
            List<Vehicle> pickup = (from v in dc.Vehicles
                              select new Vehicle { current_city = v.current_city }).Distinct().ToList();
            return pickup;
        }

        public List<SearchResult> getResults(String vehiclecategory, String bodystyle, String avail_city, String sort)
        {
            dc = new LINQDBDataContext();
            List<SearchResult> r;
            if (sort == "Daily hire rate")  
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby v.daily_hire_rate ascending
                     select new SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate }).ToList();
            else if (sort == "Manufacturer Name")
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby m.manufacturer_name ascending
                     select new SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate }).ToList();
            else
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby v.manufacturing_date ascending
                     select new SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate }).ToList();


            return r;
        }
        

    }

    public class SearchResult
    {
        public int vehicle_id { get; set; }
        public string registration_number { get; set; }
        public string manufacturer_name { get; set; }
        public string model_code { get; set; }
        public string manufacturing_date { get; set; }
        public decimal daily_hire_rate { get; set; }
    }
    public class VehicleCategory
    {
        public string vehicle_category_code { get; set; }
        public string vehicle_category_description { get; set; }
    }

    public class Model
    {
        public string model_code { get; set; }
        public string body_style { get; set; }
        public bool automatic { get; set; }
        public int passenger_capacity { get; set; }
    }

    public class CustomerDetails
    {
        [Required]
        public int customer_id { get; set; }
        [Required]
        public string first_name { get; set; }
        [Required]
        public string last_name { get; set; }
        [Required]
        public int age { get; set; }
        [Required]
        public char gender { get; set; }

        public string email { get; set; }
        [Required]
        public string phone_number { get; set; }
        [Required]
        public string address { get; set; }
        [Required]
        public string city { get; set; }
    }

}