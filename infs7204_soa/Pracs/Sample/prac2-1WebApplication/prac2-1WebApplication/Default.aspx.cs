using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace prac2_1WebApplication
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                txtboxFirstWord.Visible = txtboxSecondWord.Visible = lblFirstWord.Visible=lblSecondWord.Visible =RequiredFieldValidator3.Visible=RequiredFieldValidator4.Visible= false;
            }
        }

        protected void btnSubmit_Click(object sender, EventArgs e)
        {
            switch (DropDownListOperation.SelectedValue)
            {
                case "Word Count":
                    {
                        textCounterRef.TextCounterWebService t = new textCounterRef.TextCounterWebService();
                        lblWordCount.Text = "Total words in text: " + t.textCounter(txtboxMain.Text).ToString();
                        break;
                    }
                case "Word Occurrence Count":
                    {
                        wordCounterRef.wordHighlighter w = new wordCounterRef.wordHighlighter();
                        if (w.wordCounter(txtboxFirstWord.Text, txtboxMain.Text) == 0)
                        {
                            lblFoundError.Text = txtboxFirstWord.Text + " not found in the text!";
                            lblWordOccur.Text = "";
                        }
                        else
                        {
                            lblFoundError.Text = "";
                            lblWordOccur.Text = w.wordCounter(txtboxFirstWord.Text, txtboxMain.Text).ToString() + " matches found!";
                        }
                        break;
                    }
                case "Replace":
                    {
                        wordCounterRef.wordHighlighter w = new wordCounterRef.wordHighlighter();
                        if (w.wordCounter(txtboxFirstWord.Text, txtboxMain.Text) == 0)
                        {
                            lblFoundError.Text = txtboxFirstWord.Text + " not found in the text!";
                            lblWordOccur.Text = "";
                        }
                        else
                        {
                            lblFoundError.Text = "";
                            lblWordOccur.Text = w.wordCounter(txtboxFirstWord.Text, txtboxMain.Text).ToString() + " matches replaced!";
                            wordReplaceRef.wordReplace r = new wordReplaceRef.wordReplace();
                            txtboxMain.Text = r.replace(txtboxFirstWord.Text, txtboxSecondWord.Text, txtboxMain.Text);
                        }
                        break;
                    }
            }//switch end
        }

        protected void DropDownListOperation_TextChanged(object sender, EventArgs e)
        {
            switch (DropDownListOperation.SelectedValue)
            {
                case "Word Count":
                    {
                        lblFoundError.Text = lblWordOccur.Text = "";
                        txtboxFirstWord.Visible =lblFirstWord.Visible=txtboxSecondWord.Visible=lblSecondWord.Visible =RequiredFieldValidator3.Visible=RequiredFieldValidator4.Visible= false;
                        break;
                    }
                case "Word Occurrence Count":
                    {
                        lblFoundError.Text = lblWordOccur.Text = "";
                        txtboxFirstWord.Visible=lblFirstWord.Visible =RequiredFieldValidator3.Visible= true;
                        txtboxSecondWord.Visible =lblSecondWord.Visible=RequiredFieldValidator4.Visible= false;
                        break;
                    }
                case "Replace":
                    {
                        lblFoundError.Text = lblWordOccur.Text = "";
                        txtboxFirstWord.Visible= lblFirstWord.Visible = txtboxSecondWord.Visible =lblSecondWord.Visible=RequiredFieldValidator3.Visible=RequiredFieldValidator4.Visible= true;
                        break;
                    }
            }
        }
    }
}
