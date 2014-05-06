using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using CarRentalTestApp.Models;
namespace CarRentalTestApp
{
    /// <summary>
    /// Summary description for LINQDatabase
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class LINQDatabase : System.Web.Services.WebService
    {

        [WebMethod]
        public int SaveCustomer(Customer c)
        {
            try
            {
                LINQDBDataContext dc = new LINQDBDataContext();
                dc.Customers.InsertOnSubmit(c);
                dc.SubmitChanges();                        
            }
            catch (Exception)
            {
                return -1;
            }
            return 0;
        }

        [WebMethod]
        public ReviewModel GetReview(int vehicle_id)
        {
            LINQDBDataContext dc = new LINQDBDataContext();
            List<ReviewModel> rm = (from v in dc.Vehicles
                                    join mdl in dc.Models on v.model_code equals mdl.model_code
                                    join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                                    join mf in dc.Manufacturers on v.manufacturer_code equals mf.manufacturer_code
                                    where v.vehicle_id == vehicle_id
                                    select new ReviewModel
                                    {
                                        registration_number = v.registration_number,
                                        manufacturer_name = mf.manufacturer_name,
                                        model_code = v.model_code,
                                        body_style = mdl.body_style,
                                        automatic = mdl.automatic,
                                        passenger_capacity = mdl.passenger_capacity,
                                        vehicle_category_description = vc.vehicle_category_description,
                                        manufacturing_date = v.manufacturing_date,
                                        availability = v.availability,
                                        daily_rental_rate = v.daily_hire_rate
                                    }).ToList();

            return rm[0];
        }

        [WebMethod]
        public int SaveBooking(Booking b)
        {
            try
            {
                int j = -1;
                LINQDBDataContext dc = new LINQDBDataContext();

                IQueryable<int> d = from r in dc.Bookings
                                    orderby r.booking_id ascending
                                    select r.booking_id;
                foreach (int i in d)
                {
                    j = i;
                }
                
                b.booking_id = j + 1;

                dc.Bookings.InsertOnSubmit(b);
                dc.SubmitChanges();
            }
            catch (Exception)
            {
                return -1;
            }
            return b.booking_id;
        }
    }
}
