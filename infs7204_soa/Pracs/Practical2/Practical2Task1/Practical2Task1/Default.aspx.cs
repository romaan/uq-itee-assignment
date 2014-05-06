using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Practical2Task1.wordCounterWebRef;
using Practical2Task1.wordOccurrenceCounterWebRef;
using Practical2Task1.textReplaceWebRef;

namespace Practical2Task1
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Panel1.Visible = false;
            Panel2.Visible = false;
        }

        protected void btSubmit_Click(object sender, EventArgs e)
        {
            lblWordCount.Text = ddlFunctionality.SelectedValue;
            if (ddlFunctionality.SelectedValue == "Word Count")
            {
                WordCount wc = new WordCount();
                lblWordCount.Text = "Total words in Text:"+wc.countWord(txtMain.Text);
                Panel1.Visible = false;
                Panel2.Visible = false;
                lblWordCount.Visible = true;
            }
            else if (ddlFunctionality.SelectedValue == "Word Occurence Count")
            {
                WordOccurence wo = new WordOccurence();
                int count = wo.wordOccurenceCounter(txtMain.Text,txtSearchWord.Text);
                if (count == 0 ) 
                {
                    lblwordsearchresult.Text = "No words found";
                }
                else 
                {
                    lblwordsearchresult.Text = count+" words found";
                }
                Panel1.Visible = true;
                Panel2.Visible = false;
                lblWordCount.Visible = false;
            }
            else if (ddlFunctionality.SelectedValue == "Replace Text")
            {

                TextReplace tr = new TextReplace();
                int count = 0;
                txtMain.Text = tr.textReplace(out count, txtMain.Text, txtSearchWord.Text, txtReplaceWord.Text);
                if (count == 0)
                {
                    lblwordsearchresult.Text = "No word matched";
                }
                else
                {
                    lblwordsearchresult.Text = count + "words replaced";
                }
                Panel2.Visible = true;
                Panel1.Visible = true;
                lblWordCount.Visible = false;
            }

        }

        protected void ddlFunctionality_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ddlFunctionality.SelectedValue == "Word Count")
            {
                Panel1.Visible = false;
                Panel2.Visible = false;
            }
            else if (ddlFunctionality.SelectedValue == "Word Occurence Count")
            {
                Panel1.Visible = true;
                Panel2.Visible = false;
            }
            else if (ddlFunctionality.SelectedValue == "Replace Text")
            {
                Panel2.Visible = true;
                Panel1.Visible = true;
            }

        }

    }
}
