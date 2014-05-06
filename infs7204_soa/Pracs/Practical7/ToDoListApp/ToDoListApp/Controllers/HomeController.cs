using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ToDoListApp.Models;
using System.Web.Mvc;
using System.Web.Security;
using Microsoft.IdentityModel.Protocols.WSFederation;
using Microsoft.IdentityModel.Web;
using Microsoft.IdentityModel.Claims;
using System.Threading;

namespace ToDoListApp.Controllers
{ 
    public class HomeController : Controller
    {
        private RKSurveyDb db = new RKSurveyDb();

        //
        // GET: /Home/

        public ViewResult Index()
        {
            //String myid = "";
            //IClaimsPrincipal icp = Thread.CurrentPrincipal as IClaimsPrincipal;
            //IClaimsIdentity ici = icp.Identity as IClaimsIdentity;            
            //foreach (Claim c in ici.Claims)
                //myid += c.ClaimType + "-" + c.Value + "<br/>";
            ViewData["myid"] = User.Identity.Name;
            return View(db.rksurveylist.ToList());
        }

        public ViewResult LogOff()
        {
            WSFederationAuthenticationModule fam = FederatedAuthentication.WSFederationAuthenticationModule;
            try
            {
                FormsAuthentication.SignOut();
            }
            finally
            {
                fam.SignOut(true);
            }
            return View();
        }
        //
        // GET: /Home/Details/5

        public ViewResult Details(int id)
        {
            RKSurvey rksurvey = db.rksurveylist.Find(id);
            return View(rksurvey);
        }

        //
        // GET: /Home/Create

        public ActionResult Create()
        {
            ViewData["myid"] = User.Identity.Name;
            RKSurvey model = new RKSurvey
            {
                name = User.Identity.Name
            };

            return View(model);
        } 

        //
        // POST: /Home/Create

        [HttpPost]
        public ActionResult Create(RKSurvey rksurvey)
        {
            if (ModelState.IsValid)
            {
                db.rksurveylist.Add(rksurvey);
                db.SaveChanges();
                return RedirectToAction("Index");  
            }

            return View(rksurvey);
        }
        
        //
        // GET: /Home/Edit/5
 
        public ActionResult Edit(int id)
        {
            RKSurvey rksurvey = db.rksurveylist.Find(id);
            return View(rksurvey);
        }

        //
        // POST: /Home/Edit/5

        [HttpPost]
        public ActionResult Edit(RKSurvey rksurvey)
        {
            if (ModelState.IsValid)
            {
                db.Entry(rksurvey).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(rksurvey);
        }

        //
        // GET: /Home/Delete/5
 
        public ActionResult Delete(int id)
        {
            RKSurvey rksurvey = db.rksurveylist.Find(id);
            return View(rksurvey);
        }

        //
        // POST: /Home/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {            
            RKSurvey rksurvey = db.rksurveylist.Find(id);
            db.rksurveylist.Remove(rksurvey);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}