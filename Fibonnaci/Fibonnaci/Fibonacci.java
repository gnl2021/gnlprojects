
package Fibonnaci;
import java.util.ArrayList;

/**
 * The Fibonacci package
 * contains two classes, Fibonacci and LineChartSample
 * The Fibonacci classed is used to calculate the value and time taken
 * For the iterative and the recursive methods
 * LineChart simple is used to display the time comparative results
 * of the different methods
 *
 * @author Gregory Lauture
 * @version 1.0
 * @since 2021 -10-20
 */
public class Fibonacci {

    /**
     * Instantiates a new Fibonacci.
     *
     * @param n the n
     */
    public Fibonacci(Integer n) {
        setFib_number(n);
        iter_Fib(n);
        setRecur_Number_Fib(n);
       recur_Fib(n);
        setRecur_Number_Fib((int) recur_Fib(n));
        iter_and_recurv_Times();
    }

    /**
     * Gets iter list fib.
     *
     * @return the iter list fib
     */
    public ArrayList<Long> getIter_list_Fib() {
        return iter_list_Fib;
    }

    /**
     * Sets iter list fib.
     *
     * @param iter_list_Fib the iter list fib
     */
    public void setIter_list_Fib(ArrayList<Long> iter_list_Fib) {
        this.iter_list_Fib = iter_list_Fib;
    }

   private ArrayList<Long> iter_list_Fib;
    private ArrayList<Long> iter_times_Fib;

    /**
     * Gets recur list fib.
     *
     * @return the recur list fib
     */
    public ArrayList<Long> getRecur_list_Fib() {
        return recur_list_Fib;
    }

    /**
     * Sets recur list fib.
     *
     * @param recur_list_Fib the recur list fib
     */
    public void setRecur_list_Fib(ArrayList<Long> recur_list_Fib) {
        this.recur_list_Fib = recur_list_Fib;
    }

    private ArrayList<Long> recur_list_Fib;

    private ArrayList<Long> recur_times_Fib;

    /**
     * Gets recur times fib.
     *
     * @return the recur times fib
     */
    public ArrayList<Long> getRecur_times_Fib() {
        return recur_times_Fib;
    }

    /**
     * Sets recur times fib.
     *
     * @param recur_times_Fib the recur times fib
     */
    public void setRecur_times_Fib(ArrayList<Long> recur_times_Fib) {
        this.recur_times_Fib = recur_times_Fib;
    }

    /**
     * Gets recur number fib.
     *
     * @return the recur number fib
     */
    public int getRecur_Number_Fib() {
        return recur_Number_Fib;
    }

    /**
     * Sets recur number fib.
     *
     * @param recur_Number_Fib the recur number fib
     */
    public void setRecur_Number_Fib(int recur_Number_Fib) {
        this.recur_Number_Fib = recur_Number_Fib;
    }

    private int recur_Number_Fib;

    /**
     * Gets fib number.
     *
     * @return the fib number
     */
    public int getFib_number() {
        return fib_number;
    }

    /**
     * Sets fib number.
     *
     * @param n the n
     */
    public void setFib_number(int n) {

        this.fib_number = n;
    }

    private int fib_number;

    /**
     * Gets iter times fib.
     *
     * @return the iter times fib
     */
    public ArrayList<Long> getIter_times_Fib() {
        return iter_times_Fib;
    }

    /**
     * Sets iter times fib.
     *
     * @param iter_times_Fib the iter times fib
     */
    public void setIter_times_Fib(ArrayList<Long> iter_times_Fib) {
        this.iter_times_Fib = iter_times_Fib;
    }

    /**
     * iterative method to calculate the Fibonacci number
     * it returns an array of value
     *
     * @param n the n
     * @return the long
     */
    public long iter_Fib(int n) {
        //array to store the fibonacci numbers
        long iter_F[] = new long[n+2];
        iter_F[0]=0;
        iter_F[1]=1;
        int x = 1, y = 1, z=0;

        for (int i = 2; i <= n; i++) {
            z = x + y;
            x = y;
            y = z;

            iter_F[i] = x;
            }

        ArrayList<Long> iter_list_Fib = new ArrayList<>(iter_F.length);
        for (int i = 0; i <= n; i++) {
            iter_list_Fib.add(iter_F[i]);

        }
        setIter_list_Fib(iter_list_Fib);

        return iter_F[n];

    }

    /**
     * recursive method to calculate the Fibonacci number
     * it returns only the result value
     *
     * @param n the n
     * @return the long
     */
    public long recur_Fib(int n) {
        long recur_F[] = new long[n+2];
        recur_F[0]=0;
        recur_F[1]=1;

        if (n <=1){
        return n;}

        return recur_Fib(n-1) + recur_Fib(n-2);
    }

    /**
     * generate a list of result and time value
     * for both methods
     */
    public void iter_and_recurv_Times(){
        long startTime,endTime,totalTime;

        int i;
        int fib = getFib_number();
        ArrayList<Long> iter_Times = new ArrayList<>(fib);
        startTime = System.nanoTime();
        for(i=0; i<=fib; i++){
            long iter_T=iter_Fib(i);
            endTime   = System.nanoTime();
            totalTime = endTime - startTime;
            iter_Times.add(totalTime);
        }
        this.iter_times_Fib=iter_Times;
        ArrayList<Long> recur_Times = new ArrayList<>(fib);
        ArrayList<Long> recur_list_Fib = new ArrayList<>(fib);

        startTime = System.nanoTime();
        for(i=0; i<=fib; i++) {
            long recur_T = recur_Fib(i);
            recur_list_Fib.add(recur_T);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            recur_Times.add(totalTime);
        }
        this.recur_list_Fib=recur_list_Fib;

        this.recur_times_Fib=recur_Times;

    }

    /**
     * Function to print the comparative times of an iterative vs recursive method
     * of a Fibonacci number.
     */
    public void print_Fiblist() {
        // print iterative
        int fib_number = getFib_number();

        System.out.println("Fibonnaci number and their count");
        System.out.printf("Fibonacci(%d) value is %4d and times for iterative is %4d nanoseconds\n", fib_number, this.iter_list_Fib.get(fib_number), this.iter_times_Fib.get(fib_number));
        System.out.printf("Fibonacci(%d) value is %4d and times for recursive is %4d nanoseconds\n", fib_number, this.getRecur_Number_Fib(), this.recur_times_Fib.get(fib_number));



    }


    }





