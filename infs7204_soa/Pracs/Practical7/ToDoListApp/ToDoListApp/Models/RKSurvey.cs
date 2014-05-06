using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;

namespace ToDoListApp.Models
{
    public class RKSurvey
    {
        public int id { get; set; }
        public string name { get; set; }
        public bool apple { get; set; }
        public bool mango { get; set; }
    }
}