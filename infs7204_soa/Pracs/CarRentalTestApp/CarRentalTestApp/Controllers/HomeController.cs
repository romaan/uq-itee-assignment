﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using CarRentalTestApp.Models;

namespace CarRentalTestApp.Controllers
{
    [HandleError]
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewData["Message"] = "Welcome to ASP.NET MVC!";

            return View();
        }

        public ActionResult About()
        {
            
            return View();
        }

        public ActionResult Search()
        {
            Search s = new Search();            
            IEnumerable<SelectListItem> vc = s.getCategory().Select(r => new SelectListItem
            {
                Text = r.vehicle_category_description,
                Value = r.vehicle_category_description
            });

            ViewData["ddlVehicleCategory"] = new SelectList(vc, "Text", "Value");
            IEnumerable<SelectListItem> bc = s.getBodyStyle().Select(r => new SelectListItem
            {
                Text = r.body_style,
                Value = r.body_style
            });

            ViewData["ddlBodyStyle"] = new SelectList(bc, "Text", "Value");
            IEnumerable<SelectListItem> pup = s.getPickUp().Select(r => new SelectListItem
            {
                Text = r.current_city,
                Value = r.current_city
            });

            ViewData["ddlPickUp"] = new SelectList(pup, "Text", "Value");
            SelectList returnCity = new SelectList(new Dictionary<string, string> 
             {
                 {"Adelaide", "Adelaide"},
                 {"Brisbane", "Brisbane"},
                 {"Melbourne", "Melbourne"},
                 {"Sydney", "Sydney"},
                 {"Perth", "Perth" }
             },
             "Value", "Key");
            ViewData["ddlReturnCity"] = returnCity;

            SelectList sort = new SelectList(new Dictionary<string, string> 
             {
                 {"Daily hire rate", "Daily hire rate"},
                 {"Manufacturer Name", "Manufacturer Name"},
                 {"Manufactuere Date", "Manufactuere Date"},                 
             },
              "Value", "Key");
            ViewData["sort"] = sort;


            return View();
        }

        [HttpPost]
        public ActionResult Results()
        {
            if (string.IsNullOrEmpty(Request.Params["pickupdate"]))
            {
                 ViewData["myerr"] = "*Pickup date required";
                return View();
            }
            if (string.IsNullOrEmpty(Request.Params["dropoffdate"]))
            {
                ViewData["myerr"] = "*Drop off date required";
                return View();
            }
            DateTime startDate, endDate;
            try
            {
                startDate = Convert.ToDateTime(Request.Params["pickupdate"]);
                endDate = Convert.ToDateTime(Request.Params["dropoffdate"]);
            }
            catch (Exception)
            {
                ViewData["myerr"] = "*Dates are not in valid mm/dd/yyyy format";
                return View();
            }

            if (endDate < startDate || startDate < DateTime.Today)
            {       ViewData["myerr"] = "*Journey start date is before end date and it is from current date";
                    return View();
            }

            Search r = new Search();
            List<SearchResult> rows = r.getResults(Request.Params["ddlVehicleCategory"].ToString(), Request.Params["ddlBodyStyle"].ToString(), Request.Params["ddlPickUp"].ToString(), Request.Params["sort"].ToString());
 
            if ( rows.Count() != 0)
            {
                ViewData["pickupdate"] = Request.Params["pickupdate"];
                ViewData["dropoffdate"] = Request.Params["dropoffdate"];
                ViewData["returncity"] = Request.Params["ddlReturnCity"];
                ViewData["pickupcity"] = Request.Params["ddlPickUp"];
                ViewData.Model = rows;
            }
            else
                ViewData.Model = null;
            return View();
                    
          // return  + Request.Params["ddlReturnCity"].ToString() + Request.Params["pickupdate"].ToString() + Request.Params["dropoffdate"].ToString();
        }

        public ActionResult Booking()
        {
            ViewData["vid"] = Request.Params["id"];
            ViewData["pickupdate"] = Request.Params["pickupdate"];
            ViewData["dropoffdate"] = Request.Params["dropoffdate"];
            ViewData["returncity"] = Request.Params["returncity"];
            ViewData["pickupcity"] = Request.Params["pickupcity"];
            return View();
        }

       [HttpPost]
        public ActionResult Booking(string customer_id, string first_name, string last_name, char gender, string age, string email, string phone_number, string address, string city, string id, string pickupdate, string dropoffdate, string returncity, string pickupcity)
        {           
            if (string.IsNullOrEmpty(customer_id))
                ModelState.AddModelError("customer_id"+id, "*Required Passport Number");
          
            if (string.IsNullOrEmpty(first_name))
                ModelState.AddModelError("first_name", "*Required First Name");
            if (string.IsNullOrEmpty(last_name))
                ModelState.AddModelError("last_name", "*Required Last Name");
            if (string.IsNullOrEmpty(age))
                ModelState.AddModelError("age", "*Required Age");
            if (string.IsNullOrEmpty(email))
                ModelState.AddModelError("email", "*Required Email");

            int anAge;
            int cid;
            if (!int.TryParse(age, out anAge))
                ModelState.AddModelError("age", "*Age should be number");
            if (!int.TryParse(customer_id, out cid))
                ModelState.AddModelError("customer_id", "*Passport should be number");

            if (anAge < 18 || anAge > 100)
                ModelState.AddModelError("age", "Age should be between 18 and 100");
            if (string.IsNullOrEmpty(phone_number))
                ModelState.AddModelError("phone_number", "*Required Phone number");
            if (string.IsNullOrEmpty(address))
                ModelState.AddModelError("address", "*Required Address");
            if (string.IsNullOrEmpty(city))
                ModelState.AddModelError("city", "*Required City");

            if (ModelState.IsValid)
            {
                Transfer t = new Transfer();
                t.customer_id = cid; t.first_name = first_name; t.last_name = last_name; t.age = anAge; t.gender = gender; t.email = email; t.address = address; t.city = city; t.phone_number = phone_number;
                t.vehicle_id = id; t.pickupdate = pickupdate; t.dropoffdate = dropoffdate; t.returncity = returncity; t.pickupcity = pickupcity;
                return RedirectToAction("Payment", t);                
            }
         
            return View();
        }

       public ActionResult Payment()
        {
            ViewData["vehicle_id"] = Request.Params["vehicle_id"];
            ViewData["customer_id"] = Request.Params["customer_id"];
            ViewData["first_name"] = Request.Params["first_name"];
            ViewData["last_name"] = Request.Params["last_name"];
            ViewData["age"] = Request.Params["age"];
            ViewData["gender"] = Request.Params["gender"];
            ViewData["email"] = Request.Params["email"];
            ViewData["phone_number"] = Request.Params["phone_number"];
            ViewData["address"] = Request.Params["address"];
            ViewData["city"] = Request.Params["city"];
            ViewData["pickupdate"] = Request.Params["pickupdate"];
            ViewData["dropoffdate"] = Request.Params["dropoffdate"];
            ViewData["pickupcity"] = Request.Params["pickupcity"];
            ViewData["returncity"] = Request.Params["returncity"];

            int vid;
            int.TryParse(ViewData["vehicle_id"].ToString(), out vid);
            DatabaseWebRef.LINQDatabase ld = new DatabaseWebRef.LINQDatabase();
            DatabaseWebRef.ReviewModel rm = ld.GetReview(vid);
            ViewData["registration_number"] = rm.registration_number;
            ViewData["manufacturer_name"] = rm.manufacturer_name;
            ViewData["model_code"] = rm.model_code;
            ViewData["body_style"] = rm.body_style;
            ViewData["automatic"] = rm.automatic;
            ViewData["passenger_capacity"] = rm.passenger_capacity;
            ViewData["vehicle_category_description"] = rm.vehicle_category_description;
            ViewData["manufacturing_date"] = rm.manufacturing_date;
            ViewData["availability"] = rm.availability;
            ViewData["daily_rental_rate"] = rm.daily_rental_rate;
            return View();
        }

       [HttpPost]
       public ActionResult Payment(string card_type, string card_name, string card_number, string security_number, string expiry_date, string customer_id, string first_name, string last_name, char gender, string age, string email, string phone_number, string address, string city, string vehicle_id, string pickupdate, string dropoffdate, string returncity, string pickupcity)
       {
           ViewData["vehicle_id"] = Request.Params["vehicle_id"];
           ViewData["customer_id"] = Request.Params["customer_id"];
           ViewData["first_name"] = Request.Params["first_name"];
           ViewData["last_name"] = Request.Params["last_name"];
           ViewData["age"] = Request.Params["age"];
           ViewData["gender"] = Request.Params["gender"];
           ViewData["email"] = Request.Params["email"];
           ViewData["phone_number"] = Request.Params["phone_number"];
           ViewData["address"] = Request.Params["address"];
           ViewData["city"] = Request.Params["city"];
           ViewData["pickupdate"] = Request.Params["pickupdate"];
           ViewData["dropoffdate"] = Request.Params["dropoffdate"];
           ViewData["pickupcity"] = Request.Params["pickupcity"];
           ViewData["returncity"] = Request.Params["returncity"];

           int vid;
           int.TryParse(vehicle_id, out vid);          
           DatabaseWebRef.LINQDatabase ld = new DatabaseWebRef.LINQDatabase();
           DatabaseWebRef.ReviewModel rm = ld.GetReview(vid);
           ViewData["registration_number"] = rm.registration_number;
           ViewData["manufacturer_name"] = rm.manufacturer_name;
           ViewData["model_code"] = rm.model_code;
           ViewData["body_style"] = rm.body_style;
           ViewData["automatic"] = rm.automatic;
           ViewData["passenger_capacity"] = rm.passenger_capacity;
           ViewData["vehicle_category_description"] = rm.vehicle_category_description;
           ViewData["manufacturing_date"] = rm.manufacturing_date;
           ViewData["availability"] = rm.availability;
           ViewData["daily_rental_rate"] = rm.daily_rental_rate;

           if (string.IsNullOrEmpty(card_type) || string.IsNullOrEmpty(card_number) || string.IsNullOrEmpty(card_name) || string.IsNullOrEmpty(expiry_date) || string.IsNullOrEmpty(security_number))
           {
               ModelState.AddModelError("error", "Please fill all the fields");
           }
           else if (ModelState.IsValid)
           {
               CardVerification cv = new CardVerification();
               CardVerification.ReturnIndicator ri = cv.CheckCC(card_number);
               if (ri.CardValid && ri.CardType == card_type)
               {
                   Receipt t = new Receipt();
                   t.customer_id = customer_id; t.first_name = first_name; t.last_name = last_name; t.age = age; t.gender = gender; t.email = email; t.address = address; t.city = city; t.phone_number = phone_number;
                   t.vehicle_id = vehicle_id; t.pickupdate = pickupdate; t.dropoffdate = dropoffdate; t.returncity = returncity; t.pickupcity = pickupcity;
                   t.card_type = card_type; t.card_number = card_number; t.card_name = card_name; t.security_number = security_number; t.expiry_date = expiry_date;
                   return RedirectToAction("Receipt", t);
               }
               else
               {
                   ModelState.AddModelError("error", "Incorrect card details. Please enter again");
               }
           }
           return View();
       }

        [HttpGet]
        public ActionResult Review()
        {
            int id;
            int.TryParse(Request.Params["vehicle_id"], out  id);
            Review r = new Review();
            ViewData["id"] = id;
            ViewData["pickupdate"] = Request.Params["pickupdate"];
            ViewData["dropoffdate"] = Request.Params["returnDate"];
            ViewData["returncity"] = Request.Params["returncity"];
            ViewData["pickupcity"] = Request.Params["pickupcity"];
            return View(r.getReview(id));
        }

        public ActionResult Receipt(string card_type, string card_name, string card_number, string security_number, string expiry_date, string customer_id, string first_name, string last_name, char gender, int age, string email, string phone_number, string address, string city, int vehicle_id, string pickupdate, string dropoffdate, string returncity, string pickupcity)
        {
            ViewData["vehicle_id"] = Request.Params["vehicle_id"];
            ViewData["customer_id"] = Request.Params["customer_id"];
            ViewData["first_name"] = Request.Params["first_name"];
            ViewData["last_name"] = Request.Params["last_name"];
            ViewData["age"] = Request.Params["age"];
            ViewData["gender"] = Request.Params["gender"];
            ViewData["email"] = Request.Params["email"];
            ViewData["phone_number"] = Request.Params["phone_number"];
            ViewData["address"] = Request.Params["address"];
            ViewData["city"] = Request.Params["city"];
            ViewData["pickupdate"] = Request.Params["pickupdate"];
            ViewData["dropoffdate"] = Request.Params["dropoffdate"];
            ViewData["pickupcity"] = Request.Params["pickupcity"];
            ViewData["returncity"] = Request.Params["returncity"];
          
                int cid;
                int.TryParse(customer_id, out cid);
                DatabaseWebRef.Customer c = new DatabaseWebRef.Customer
                {
                    customer_id = cid,
                    first_name = first_name,
                    last_name = last_name,
                    age = age,
                    gender = gender,
                    email = email,
                    phone_number = phone_number,
                    address = address,
                    city = city
                };
                DatabaseWebRef.LINQDatabase s = new DatabaseWebRef.LINQDatabase();
                s.SaveCustomer(c);


                int vid = vehicle_id;
                int.TryParse(customer_id, out cid);     
                DatabaseWebRef.Booking b = new DatabaseWebRef.Booking
                {
                    booking_status_code = "1",
                    customer_id = cid,
                    vehicle_id = vid,
                    pickup_city = pickupcity,
                    return_city = returncity,
                    pickup_date = Convert.ToDateTime(pickupdate),
                    return_date = Convert.ToDateTime(dropoffdate)
                };
                b.booking_id = s.SaveBooking(b);

                Payment p = new Payment();
                p.cardtype = card_type; p.cardnumber = card_number; p.cardname = card_name; p.expirydate = expiry_date; p.securitynumber = security_number;
                ParentModel pm = new ParentModel();
                pm.customer = c;
                pm.booking = b;
                pm.reviewmodel = s.GetReview(vehicle_id);
                pm.payment = p;

                DateTime end = Convert.ToDateTime(ViewData["dropoffdate"]);
                DateTime start = Convert.ToDateTime(ViewData["pickupdate"]);
                double d = ((end - start).TotalDays + 1 ) * Convert.ToDouble(pm.reviewmodel.daily_rental_rate);
            
                CarRentalTestApp.SendEmailService ses = new CarRentalTestApp.SendEmailService();
                String aString = "<br/>Customer ID:" + pm.customer.customer_id + "<br/>Customer First Name:" + pm.customer.first_name + "<br/>Customer Last Name:" + pm.customer.last_name + "<br/>Age:" + pm.customer.age + "<br/>Gender:" + pm.customer.gender + "<br/>Email:" + pm.customer.email + "<br/>Phone Number:" + pm.customer.phone_number + "<br/>Address:" + pm.customer.address + "<br/>City:" + pm.customer.city;
                aString += "<br/>Payment Card Type:" + pm.payment.cardtype + "<br/>Payment Card Number:" + pm.payment.cardnumber + "<br/>Payment Card Name:" + pm.payment.cardname + "<br/>Expiry Date:" + pm.payment.expirydate + "<br/>Security Number:" + pm.payment.securitynumber;
                aString += "<br/>Booking ID:" + pm.booking.booking_id + "<br/>Pickup Date:" + pm.booking.pickup_date + "<br/>Return Date:" + pm.booking.return_date + "<br/>Pickup City:" + pm.booking.pickup_city + "<br/>Drop off city:" + pm.booking.return_city;
                aString += "<br/>Vehicle ID:" + pm.reviewmodel.registration_number +"<br/>Manufacturing Date:"+ pm.reviewmodel.manufacturing_date+ "<br/>Model:" + pm.reviewmodel.model_code + "<br/>Body style:" + pm.reviewmodel.body_style + "<br/>Passenger Capacity:" + pm.reviewmodel.passenger_capacity + "<br/>Vehicle Category Description:" + pm.reviewmodel.vehicle_category_description + "<br/> Daily Rental:" + pm.reviewmodel.daily_rental_rate + "<br/>Total Cost:" + d.ToString();
                ses.SendEmail(pm.customer.email, aString );
                return View(pm);
            }
        }
    }