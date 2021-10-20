package Fibonnaci;

import java.io.IOException;
import java.io.Console;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Main.
 */
public class main {

    /**
     * These two arrays store the result of time complexity
     * For iterative and recursive methods
     * Values that will be passed to the LineChartSample class
     * To generate a comparative graph
     */
    public static ArrayList<Long> recur_Times_list;
    /**
     * The Iter times list.
     */
    public static ArrayList<Long> iter_Times_list;

    /**
     * This function require the input of a parameter n
     * Which is then used in the Fibonacci class
     * Also a graph object is created to receive the list parameters
     */
    public static void menu(){
        int n=0;
        boolean isInteger;
        do
        {
            try
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter integer value only  ");
                n=sc.nextInt();
                isInteger=false;
            }
            catch(Exception e)
            {
                // accept integer only.
                System.out.println("Enter only integer value.."+e);
                isInteger=true;
            }
        }
        while(isInteger);
        String choice;
        Fibonacci test = new Fibonacci(n);
        LineChartSample mygraph = new LineChartSample();
        mygraph.iter_Times_list=test.getIter_times_Fib();
        mygraph.recur_Times_list=test.getRecur_times_Fib();
        do {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println("::::::::::::::::::::::::::::::::");
            System.out.println("Print iterative versus recursive times(a)");
            System.out.println("Generate a comparative graph(b)");
            System.out.println("Quit the program (q)");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");

            Scanner data = new Scanner(System.in);

            choice = data.nextLine().toLowerCase();

            switch (choice) {
                case "a":

                    test.print_Fiblist();
                    System.out.println("Press a key to continue...");

                    //wait for a user input before
                    try {
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "b":
                    // call the LineChartSample class
                    new Thread() {
                        @Override
                        public void run() {
                            javafx.application.Application.launch(LineChartSample.class);
                        }
                    }.start();
                    break;

                case "q":
                    System.out.println("Goodbye...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("you didn't make a choice");
                    break;
            }
        } while (choice != "q");

    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
            menu();
        }
    }
