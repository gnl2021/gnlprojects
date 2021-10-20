package Fibonnaci;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;


public class LineChartSample extends Application {
    public static ArrayList<Long> recur_Times_list;
    public static ArrayList<Long> iter_Times_list;


  public void start(Stage stage) {

      stage.setTitle("Line Chart Sample");
      int fib= recur_Times_list.size()-1;
      final NumberAxis yAxis = new NumberAxis(0,recur_Times_list.get(fib),1000);
      final NumberAxis xAxis = new NumberAxis(0,10,1);
      xAxis.setLabel("Fib Number");
      yAxis.setLabel("times/nanosec");
      final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Fibonacci Iterative times vs Recursive time");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Iterative times");
        for (int i=0; i< iter_Times_list.size();i++) {

            series1.getData().add(new XYChart.Data(i, iter_Times_list.get(i)));
        }




        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Recursive Times");
      for (int i=0; i< recur_Times_list.size();i++) {
          series2.getData().add(new XYChart.Data(i, recur_Times_list.get(i)));
      }



        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
      // Fibonacci newClassObj = new Fibonacci(10);
       //ArrayList<Long> recur_Times = newClassObj.getRecur_times_Fib();
       //ArrayList<Long> iter_Times = newClassObj.getIter_times_Fib();
       //recur_Times_list=recur_Times;
       // iter_Times_list=iter_Times;
       //System.out.printf("Fibonacci(%d) value is %4d and times for recursive is %4d nanoseconds\n", 1, iter_Times_list.get(1), recur_Times_list.get(1));
      //Application.launch(args);
    }
}