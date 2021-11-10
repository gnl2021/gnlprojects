import java.util.Random;
import java.util.Scanner;

/**
 * Program to calculate the performance
 * of a parallel array sum vs a single thread sum
 * from an array of 200 millions random number between 1 and 10
 * This implementation is heavily inspired from various example founds on the web
 * @author Gregory Lauture
 *  * @version 1.0
 *  * @since 2021 -10-27
 */
public class SumCalcThread extends Thread {
    private static int[] sumArr;// length of the array
    private  int low, high, partial;

    /**
     * Constructor method to calculate sum
     * relying on multiple threads
     * @param sumArr
     * @param low
     * @param high
     */
    public SumCalcThread(int[] sumArr, int low, int high) {
        this.sumArr = sumArr;
        this.low = low;
        this.high = Math.min(high, sumArr.length);
    }

    @Override
    public void run() {
        partial = simpleSum(sumArr, low, high);
    }


    public static int simpleSum(int[] sumArr)
    {
        return simpleSum(sumArr, 0, sumArr.length);
    }

    /** this a one thread only sum calculator
     *
     * @param sumArr
     * @param low
     * @param high
     * @return
     */
    public static int simpleSum(int[] sumArr, int low, int high)
    {
        int result = 0;

        for (int i = low; i < high; i++) {
            result += sumArr[i];
        }

        return result;
    }

    /**
     * get a partial sum to use on a joint thread
     * @return
     */
    public int getPartialSum()
    {
        return partial;
    }

    public static int parallelThrSum(int[] sumArr) {
        return parallelThrSum(sumArr, Runtime.getRuntime().availableProcessors());
    }


    /**
     * principal method to calculate sum
     * using available threads
     * the array is divided so the sums
     * can be calculated in parallel by the number
     * of available threads
     * @param sumArr
     * @param threadCount
     * @return
     */
    public static int parallelThrSum(int[] sumArr, int threadCount) {
        int size = (int) Math.ceil(sumArr.length * 1.0 / threadCount);// subdivide the array by the number of threads

        SumCalcThread[] ThrSums = new SumCalcThread[threadCount];
        //running a thread to calculate a partial sum

        for (int i = 0; i < threadCount; i++) {
            ThrSums[i] = new SumCalcThread(sumArr, i * size, (i + 1) * size);
            ThrSums[i].start();
            System.out.printf("Active threads %d \n", SumCalcThread.activeCount()-2);//because main is always added and the first iteration of the program also


        }

        int result = 0;
        try {
            for (SumCalcThread sum : ThrSums) {
                sum.join();
                result += sum.getPartialSum();
            }
        } catch (InterruptedException e) { }



        return result;



    }


    public static void main(String[] args){
        Random rand = new Random();
        int[] sumArr = new int[200000000];
        // fill the array with random number
        for (int i=0; i < sumArr.length; i++ ) {
            sumArr[i] = rand.nextInt(10) + 1;// a random number between 1 and 10
        }
// add a switch menu choice
            long startTime = System.currentTimeMillis();
        String choice;
        do {
            System.out.println("::::::::::::::::::::::::::::::::::::");
            System.out.println("\tArray Sum Calculator ");
            System.out.println("::::::::::::::::::::::::::::::::::::");
            System.out.println("\tSimple Sum (a)");
            System.out.println("\tSum with user threads (b)" );
            System.out.println("\tSum with automatic threads (c)  ");
            System.out.println("\tQuit the program (q)");
            System.out.println("::::::::::::::::::::::::::::::::::::");

            Scanner input = new Scanner(System.in);

            choice = input.nextLine().toLowerCase();
            int nThreads;


            switch (choice) {
                case "a":
                    startTime = System.currentTimeMillis();
                    System.out.println( simpleSum(sumArr) );
                    System.out.printf("is Sum for the array of length  %d with 1 thread: \n", sumArr.length);
                    System.out.printf("\tdone in: " + (System.currentTimeMillis() - startTime) + "ms \n" );
                    break;
                case "b":
                    System.out.println("Enter the number of threads");
                    nThreads = Integer.parseInt(input.next());
                    startTime = System.currentTimeMillis();
                    System.out.println(SumCalcThread.parallelThrSum(sumArr, nThreads));
                    System.out.printf("is Sum for the array using  %s parallel threads: \n", nThreads);
                    System.out.println("\tdone in: " + (System.currentTimeMillis() - startTime) + "ms \n" );
                    break;
                case "c":
                    startTime = System.currentTimeMillis();
                    System.out.println("Sum for the array using automatic threads: \t" + SumCalcThread.parallelThrSum(sumArr));
                    System.out.println("\tdone in: " + (System.currentTimeMillis() - startTime) + "ms \n" );
                    break;
                case "q":
                    System.out.println("Goodbye...");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    break;
            }
        } while (!choice.equals("q"));
    }
}


