package wordsCount;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class wordsGui extends Application{
    Button openUrl, openFile, print, clear;
    TextArea textArea;
    TextField input;
    Label status;
    @Override
    public void start(Stage stage) throws Exception {
//creating a BorderPane
        BorderPane root = new BorderPane();

        //defining a label
        Label labelOne = new Label("File:");

        //initializing input text field
        input = new TextField();

        //creating open button, adding event listener to call openFile() method
        openUrl = new Button("openUrl");
        openFile = new Button("openFile");
        openUrl.setOnAction(e -> openFile());
        openFile.setOnAction(e -> openFile());
        //creating save button, adding event listener to call saveFile() method
        print = new Button("Print");
        print.setOnAction(e -> saveFile());

        //creating clear button, adding event listener to clear text area
        clear = new Button("Clear");
        clear.setOnAction(e -> {
            textArea.clear();
            status.setText("Status: cleared");
        });

        //creating an HBox with label, text field and buttons
        //using 10 as spacing between components
        HBox box = new HBox(10, labelOne, input, openUrl, openFile, print, clear);
        box.setPadding(new Insets(10));
        //adding box to the top
        root.setTop(box);


        //creating text area and adding to center
        textArea = new TextArea();
        root.setCenter(textArea);

        //creating status label, adding to bottom
        status = new Label("Status:");
        root.setBottom(status);

        //setting up and displaying a scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("wordCounters");
        stage.show();
    }

    //event handler method to open a file
    public void openFile() {
        //opening file whose name is given in input text field
        File file = new File(input.getText());
        try {
            Scanner sc = new Scanner(file);
            String text = "";
            while (sc.hasNext()) {
                text += sc.nextLine() + "\n";
            }
            sc.close();
            textArea.setText(text.trim());
            status.setText("Status: File loaded");
        } catch (Exception ex) {
            //exception occurred. updating status
            status.setText("Status: File does not exist");
            textArea.setText("");
        }
    }

    //event handler method to save contents of text area to a file
    public void saveFile() {
        //opening file whose name is given in input text field
        File file = new File(input.getText());
        try {
            //opening a print writer, writing txt area contents to file and saving it
            PrintWriter writer = new PrintWriter(file);
            writer.println(textArea.getText());
            writer.close();
            status.setText("Status: File saved");
        } catch (Exception ex) {
            //exception occurred. updating status
            status.setText("Status: File does not exist");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}