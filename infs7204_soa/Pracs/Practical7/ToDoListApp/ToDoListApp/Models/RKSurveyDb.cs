using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;

namespace ToDoListApp.Models
{
    public class RKSurveyDb: DbContext
    {
        public DbSet<RKSurvey> rksurveylist { get; set; }
    }
}