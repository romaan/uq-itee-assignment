using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Data;
using System.Data.Sql;
using System.Data.SqlClient;

namespace Phonebook
{
   
    /// <summary>
    /// Summary description for DatabaseService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/", Name="DatabaseService", Description="Accessing database")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class DatabaseService : System.Web.Services.WebService
    {
        string connString = "Data Source=.\\SQLEXPRESS;AttachDbFilename=C:\\Users\\Roman\\Documents\\Visual Studio 2010\\Projects\\Practical3\\Practical2Task2\\App_Data\\Contacts.mdf;Integrated Security=True;User Instance=True";

        [WebMethod]
        public bool fullNameExists(string firstName, string lastName)
        {
            if (isPresent("SELECT ID FROM ContactInformation WHERE FirstName='"+firstName+"' and LastName='"+lastName+"'"))
                return true;
            return false;
        }

        [WebMethod]
        public bool phoneExists(string phone)
        {
            if (isPresent("SELECT ID FROM ContactInformation WHERE PhoneNumber like '%" + phone +"%'"))
                return true;
            return false;
        }

        [WebMethod]
        public int saveContact(List<string> content)
        {
            int i = 0;
            string query = "INSERT INTO ContactInformation (FirstName, LastName, Title, AreaCode, PhoneNumber, Address, State, Email, Dob, RememberDob, Comment) VALUES(";
            foreach (string element in content)
            {
                i++;          
                query += "'"+element+"'";
                if (i == 11)
                    query += ")";
                else
                    query += ",";
                       
            }

            return insert(query); ;
        }

        [WebMethod]
        public int updateContact(List<string> content)
        {
            string query = "UPDATE ContactInformation SET FirstName='" + content[0] + "', LastName='" + content[1] + "', AreaCode='" + content[2] + "', PhoneNumber='" + content[3] + "', Address='" + content[4] + "', State='" + content[5] + "', Email='" + content[6] + "', Dob='" + content[7] + "' WHERE ID='"+ content[8]+"'";
            return insert(query); 
        }

        [WebMethod]
        public List<string> getContact(string[] value, string type)
        {
            if (type == "Name")
                return getData("SELECT * FROM ContactInformation WHERE FirstName='"+value[0]+"' and LastName='"+value[1]+"'");
            else if (type == "Phone")
                return getData("SELECT * FROM ContactInformation WHERE PhoneNumber='" + value[0] + "'");
            return null;
        }

        [WebMethod]
        public List<List<string>> remindBirthday()
        {
            return getMultiData("SELECT FirstName, LastName, Email FROM ContactInformation WHERE (RememberDob = 'true') AND (DATEPART(month, Dob) = DATEPART(month, Dob)) AND (DATEPART(day, Dob) = DATEPART(day, GETDATE()))");
        }

        private bool isPresent(string query)
        {
            bool flag = false;
            try
            {
                SqlConnection thisConnection = new SqlConnection(connString);
                thisConnection.Open();
                SqlCommand thisCommand = thisConnection.CreateCommand();
                thisCommand.CommandText = query;
                SqlDataReader thisReader = thisCommand.ExecuteReader();
                if (thisReader.HasRows)
                {
                    //Console.WriteLine("\t{0}\t{1}", thisReader["CustomerID"], thisReader["CompanyName"]);
                    flag = true;
                }
                thisReader.Close();
                thisConnection.Close();
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.Message);
            }
            return flag;
        }

        private int insert(string query)
        {
            int rowsAffected = 0;
            try
            {
                SqlConnection thisConnection = new SqlConnection(connString);
                thisConnection.Open();
                SqlCommand thisCommand = thisConnection.CreateCommand();
                thisCommand.CommandText = query;
                rowsAffected = thisCommand.ExecuteNonQuery();           
                thisConnection.Close();
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.Message);
            }
            return rowsAffected;
        }

        private List<string> getData(string query)
        {
            List<string> data = null;
            try
            {
                SqlConnection thisConnection = new SqlConnection(connString);
                thisConnection.Open();
                SqlCommand thisCommand = thisConnection.CreateCommand();
                thisCommand.CommandText = query;
                SqlDataReader thisReader = thisCommand.ExecuteReader();
                if (thisReader.Read())
                {
                    //Console.WriteLine("\t{0}\t{1}", thisReader["CustomerID"], thisReader["CompanyName"]);
                    int i = 0;
                    data = new List<string>();
                    while (i < thisReader.FieldCount)
                    {
                        data.Add(thisReader[i++].ToString());                        
                    }
                }
                thisReader.Close();
                thisConnection.Close();
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.Message);
            }
            return data;
        }

        private List<List<string>> getMultiData(string query)
        {
            List<List<string>> data = new List<List<string>>();
            List<string> row = null;
            try
            {
                SqlConnection thisConnection = new SqlConnection(connString);
                thisConnection.Open();
                SqlCommand thisCommand = thisConnection.CreateCommand();
                thisCommand.CommandText = query;
                SqlDataReader thisReader = thisCommand.ExecuteReader();
                while (thisReader.Read())
                {
                    int i = 0;
                    row = new List<string>();
                    while (i < thisReader.FieldCount)
                    {
                        row.Add(thisReader[i++].ToString());
                    }
                    data.Add(row);
                }
                thisReader.Close();
                thisConnection.Close();
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.Message);
            }
            return data;
        }
    }
}
