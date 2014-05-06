using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleReadString
{
    class Program
    {
        static void Main(string[] args)
        {
            ServiceReference1.Service1Client sc = null;
            try
            {
                sc = new ServiceReference1.Service1Client();
                Console.WriteLine("\nReading string:{0}", sc.GetStringBack());
            }
            catch (Exception)
            {
                Console.WriteLine("\nException caught");
            }
            Console.ReadLine();
        }
    }
}
