//Ant and greed

using System;
using System.Collections.Generic;
//using System.Linq;
//using System.Text;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("Enter the count of steps: ");
            long step = (long)Convert.ToInt64(Console.ReadLine());

            Console.Write("Enter the size of array: ");
            long arraySize = (long)Convert.ToInt64(Console.ReadLine());

            string direction = "up";
            long rows = arraySize, columns = arraySize, counter = 0, X = rows / 2, Y = columns / 2;
            bool[,] array = new bool[rows, columns];
            bool breakIF;

            

            for (long m = 0; m < step; m++) {
                breakIF = false;
                if (array[X, Y] != true) {
                    array[X, Y] = true;
                    switch (direction) {
                        case "up": X += 1; Y += 0; direction = "right"; break;
                        case "down": X += -1; Y += 0; direction = "left"; break;
                        case "left": X += 0; Y += -1; direction = "up"; break;
                        case "right": X += 0; Y += 1; direction = "down"; break;
                    }
                    counter++;
                    breakIF = true;
                }

                if (array[X, Y] && breakIF != true) {
                    array[X, Y] = false;
                    switch (direction) {
                        case "up": X += -1; Y += 0; direction = "left"; break;
                        case "down": X += 1; Y += 0; direction = "right"; break;
                        case "left": X += 0; Y += 1; direction = "down"; break;
                        case "right": X += 0; Y += -1; direction = "up"; break;
                    }
                    counter--;
                }
            }

            Console.WriteLine("Black squares count: " + counter.ToString());
        }
        
    }
}
