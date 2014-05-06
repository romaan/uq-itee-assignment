using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace CarRentalApp
{
    /// <summary>
    /// Summary description for DatabaseService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class DatabaseService : System.Web.Services.WebService
    {
        LSQLDataContext dc;

        [WebMethod]
        public IEnumerable<Models.VehicleCategory> getCategory()
        {
            dc = new LSQLDataContext();
            List<Models.VehicleCategory> vc = (from vct in dc.VehicleCategories
                                        select new Models.VehicleCategory { vehicle_category_code = vct.vehicle_category_code.ToString(), vehicle_category_description = vct.vehicle_category_description.ToString() }).ToList();

            return vc;
        }

        [WebMethod]
        public IEnumerable<Models.Model> getBodyStyle()
        {
            dc = new LSQLDataContext();
            List<Models.Model> bs = (from mdt in dc.Models
                              select new Models.Model { body_style = mdt.body_style }).Distinct().ToList();

            return bs;
        }

        [WebMethod]
        public IEnumerable<Models.Vehicle> getPickUp()
        {
            dc = new LSQLDataContext();
            List<Models.Vehicle> pickup = (from v in dc.Vehicles
                                    select new Models.Vehicle { current_city = v.current_city }).Distinct().ToList();
            return pickup;
        }

        [WebMethod]
        public List<Models.SearchResult> getResults(String vehiclecategory, String bodystyle, String avail_city, String sort, DateTime startDate, DateTime endDate)
        {
            dc = new LSQLDataContext();
            List<Models.SearchResult> r;
            List<Models.SearchResult> filtered = new List<Models.SearchResult>();
            if (sort == "Daily hire rate")
            {
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby v.daily_hire_rate ascending
                     select new Models.SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate, availability = v.availability }).ToList();                              
            }
            else if (sort == "Manufacturer Name")
            {
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby m.manufacturer_name ascending
                     select new Models.SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate }).ToList();
            }
            else
            {
                r = (from v in dc.Vehicles
                     join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                     join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                     join mdl in dc.Models on v.model_code equals mdl.model_code
                     where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city
                     orderby v.manufacturing_date ascending
                     select new Models.SearchResult { vehicle_id = v.vehicle_id, registration_number = v.registration_number, manufacturer_name = m.manufacturer_name, model_code = v.model_code, manufacturing_date = v.manufacturing_date, daily_hire_rate = v.daily_hire_rate }).ToList();
            }

            List<Models.VehicleCount> rem = (from b in dc.Bookings
                                             join v in dc.Vehicles on b.vehicle_id equals v.vehicle_id
                                             join m in dc.Manufacturers on v.manufacturer_code equals m.manufacturer_code
                                             join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                                             join mdl in dc.Models on v.model_code equals mdl.model_code
                                             where vc.vehicle_category_description == vehiclecategory && mdl.body_style == bodystyle && v.current_city == avail_city && ((startDate >= b.pickup_date && startDate <= b.return_date) || (endDate >= b.pickup_date && endDate <= b.return_date))
                                             group b by b.vehicle_id into x
                                             select new Models.VehicleCount { vehicle_id = x.Key, book_count = x.Count() }).ToList();

            foreach (Models.SearchResult sr in r)
            {
                bool notinlist = true;
                foreach (Models.VehicleCount vc in rem)
                    if (sr.vehicle_id == vc.vehicle_id)
                    {
                        if (vc.book_count < sr.availability)
                        {
                            filtered.Add(sr);
                        }
                        notinlist = false;
                    }
                if (notinlist)
                    filtered.Add(sr);
            }

            return filtered;
        }

        [WebMethod]
        public Models.ReviewModel getReview(int vehicle_id)
        {
            dc = new LSQLDataContext();
            List<Models.ReviewModel> rm = (from v in dc.Vehicles
                                    join mdl in dc.Models on v.model_code equals mdl.model_code
                                    join vc in dc.VehicleCategories on v.vehicle_category_code equals vc.vehicle_category_code
                                    join mf in dc.Manufacturers on v.manufacturer_code equals mf.manufacturer_code
                                    where v.vehicle_id == vehicle_id
                                    select new Models.ReviewModel
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
                                        daily_rental_rate = v.daily_hire_rate,
                                        engine_power = mdl.engine_power
                                    }).ToList();

            return rm[0];
        }

        [WebMethod]
        public int SaveCustomer(Customer c)
        {
            try
            {

                dc = new LSQLDataContext();
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
        public int SaveBooking(Booking b)
        {
            try
            {
                int j = -1;
                dc = new LSQLDataContext();

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

        [WebMethod]
        public int getBookingCount(int vehicle_id)
        {
            try
            {
                dc = new LSQLDataContext();
                int aCount = (from r in dc.Bookings
                                          where r.vehicle_id == vehicle_id
                                          select r.booking_id).Count();
                return aCount;
            }
            catch (Exception)
            {
                return 0;
            }
        }

        [WebMethod]
        public List<Models.Booking> getBookingList(int passport)
        {
            List<Models.Booking> booklist;
            try
            {
                dc = new LSQLDataContext();
                booklist = (from b in dc.Bookings
                            where b.customer_id == passport && b.pickup_date >= DateTime.Now
                            select new Models.Booking { customer_id = b.customer_id, booking_id = b.booking_id, booking_status_code = b.booking_status_code, vehicle_id = b.vehicle_id, pickup_date = b.pickup_date, return_date = b.return_date, pickup_city = b.pickup_city, return_city = b.return_city }).ToList();
            }
            catch (Exception)
            {
                return null;
            }
            return booklist;
        }

        [WebMethod]
        public int getBookingListCount(int passport)
        {
            int booklist;
            try
            {
                dc = new LSQLDataContext();
                booklist = (from b in dc.Bookings
                           where b.customer_id == passport && b.pickup_date >= DateTime.Now
                           select new Booking { }).Count();
            }
            catch (Exception)
            {
                return 0;
            }
            return booklist;
        }

        [WebMethod]
        public bool deleteBooking(int booking_id)
        {
            try
            {
                dc = new LSQLDataContext();
                var bk = (from b in dc.Bookings
                          where b.booking_id == booking_id
                          select b);
           
                dc.Bookings.DeleteAllOnSubmit(bk);
                dc.SubmitChanges();
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }
        
       [WebMethod]
        public Booking getBookingDetails(int booking_id)
       {
           try
           {
               dc = new LSQLDataContext();
               IQueryable<Booking> bk = (from b in dc.Bookings
                         where b.booking_id == booking_id
                         select b);
               return bk.First<Booking>();
           }
                catch (Exception)
            {
                return null;
            }
       }

       [WebMethod]
       public bool checkRestriction(int customer_id, DateTime start_date)
       {
           try
           {
               dc = new LSQLDataContext();
               int bk = (from b in dc.Bookings
                                         where b.customer_id == customer_id && b.pickup_date == start_date
                                         select b).Count();
               if (bk < 2)
                   return true;
               return false;
           }
           catch (Exception)
           {
               return false;
           }          
       }
    }
}
