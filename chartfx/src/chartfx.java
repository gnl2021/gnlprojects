import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class chartfx extends Application {
    public void start(Stage stage) {
        //Defining the x an y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        //Setting labels for the axes
        xAxis.setLabel("fibNumber");
        yAxis.setLabel("Times (nanosec)");
        //Creating a line chart
        LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);
        //Preparing the data points for the line1
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("Jan", 7.0));
        series1.getData().add(new XYChart.Data("Feb", 6.9));
        series1.getData().add(new XYChart.Data("March", 9.5));
        series1.getData().add(new XYChart.Data("April", 14.5));
        series1.getData().add(new XYChart.Data("May", 18.2));
        series1.getData().add(new XYChart.Data("June", 21.5));
        series1.getData().add(new XYChart.Data("July", 25.2));
        series1.getData().add(new XYChart.Data("August", 26.5));
        series1.getData().add(new XYChart.Data("Sep", 23.3));
        series1.getData().add(new XYChart.Data("Oct", 18.3));
        series1.getData().add(new XYChart.Data("Nov", 13.9));
        series1.getData().add(new XYChart.Data("Dec", 9.6));
        //Preparing the data points for the line2
        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data("Jan", -0.2));
        series2.getData().add(new XYChart.Data("Feb", 0.8));
        series2.getData().add(new XYChart.Data("March", 5.7));
        series2.getData().add(new XYChart.Data("April", 11.3));
        series2.getData().add(new XYChart.Data("May", 17.0));
        series2.getData().add(new XYChart.Data("June", 22.0));
        series2.getData().add(new XYChart.Data("July", 24.8));
        series2.getData().add(new XYChart.Data("August", 24.1));
        series2.getData().add(new XYChart.Data("Sep", 20.1));
        series2.getData().add(new XYChart.Data("Oct", 14.1));
        series2.getData().add(new XYChart.Data("Nov", 8.6));
        series2.getData().add(new XYChart.Data("Dec", 2.5));

        //Setting the name to the line (series)
        series1.setName("iterative");
        series2.setName("recursive");
        //Setting the data to Line chart
        linechart.getData().addAll(series1, series2);
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(linechart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene
        Scene scene = new Scene(pane, 595, 350);
        stage.setTitle("Line Chart");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}