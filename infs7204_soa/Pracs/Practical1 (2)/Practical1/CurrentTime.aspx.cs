﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Practical1
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Time.CurrentTimeService currentTimeService = new Time.CurrentTimeService();
            lblCurrentTime.Text = "Welcome! Current Time: "+currentTimeService.currentTime();
        }
    }
}