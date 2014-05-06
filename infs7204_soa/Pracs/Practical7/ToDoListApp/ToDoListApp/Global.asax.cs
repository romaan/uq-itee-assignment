using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using System.Web.Security;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using Microsoft.IdentityModel.Web;
using Microsoft.IdentityModel.Web.Configuration;

namespace ToDoListApp
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
        

        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
                "Default", // Route name
                "{controller}/{action}/{id}", // URL with parameters
                new { controller = "Home", action = "Index", id = UrlParameter.Optional } // Parameter defaults
            );

        }

        private void OnServiceConfigurationCreated(object sender, ServiceConfigurationCreatedEventArgs e)
        {
            List<CookieTransform> sessionTransforms =
                new List<CookieTransform>(
                    new CookieTransform[] 
            {
                new DeflateCookieTransform(), 
                new RsaEncryptionCookieTransform(
                    e.ServiceConfiguration.ServiceCertificate),
                new RsaSignatureCookieTransform(
                    e.ServiceConfiguration.ServiceCertificate)  
            });
             
            SessionSecurityTokenHandler sessionHandler =
             new SessionSecurityTokenHandler(sessionTransforms.AsReadOnly());
           
                e.ServiceConfiguration.SecurityTokenHandlers.AddOrReplace(
                sessionHandler);
        }

        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            RegisterGlobalFilters(GlobalFilters.Filters);
            RegisterRoutes(RouteTable.Routes);
            FederatedAuthentication.ServiceConfigurationCreated += OnServiceConfigurationCreated;
        }
    }
}