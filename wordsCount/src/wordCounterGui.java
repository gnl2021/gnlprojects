package wordsCount;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
/**
 * The program use javafx libraries to display a basic gun
 * which accept a input from a Url or local file
 * then display the result for all the words and their counts
 * Also the top 20 words and their counts
 *
 *
 * @author Gregory Lauture
 * @version 1.0
 * @since 2021 -10-27
 */

public class wordCounterGui extends Application {
    private static final String MY_URL_FILE = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    TextField input;
    TextArea printData;
    Label printLabel;


    static String mytextStart;
    static String mytextEnd;
    static Label status;
    static boolean fileFound;
    static boolean textlimit;
    static boolean isDemo;


    String allwords;
    String top20;

    static Button buttonD1 = new Button("word counts");
   static Button buttonD20 = new Button("top20");
    static Button myTextLimit = new Button("Set Text limits");



    public static void main(String[] args) {
        launch(args);
    }



    @Override
    /**
     * The star method is the main gui menu
     */
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox();
        Button btnDemo = new Button();
        btnDemo.setText("Demo");
        btnDemo.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                showDemo();
            }
        });


        input = new TextField();

        Button btnUrl = new Button();
        btnUrl.setText("Open Url");
        btnUrl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showUrl();
            }
        });

        Button btnFile = new Button();
        btnFile.setText("Open File");
        btnFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFile();

            }
        });
        myTextLimit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Assume success always!

                textLimit();
            }
        });
        // A layout container for UI controls
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(btnDemo, input, btnUrl, btnFile, myTextLimit);

        // Top level container for all view content
        Scene scene = new Scene(layout, 300, 250);

        // primaryStage is the main top level window created by platform
        primaryStage.setTitle("My WordGui Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The Demo class test
     */
    public void showDemo() {
        Stage stage = new Stage();

        printData = new TextArea();
        printLabel = new Label();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label Demo_L = new Label("Demo");
        readFile demo = new readFile(MY_URL_FILE);
        if (demo != null){
            isDemo=true;
            status= new Label("Demo file");
        }
        getfileData(demo);

        buttonD1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Assume success alwaysys
                printData.setText(allwords);
                printLabel.setText("All Words");
                printData();
            }
        });
        buttonD20.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                printData.setText(top20);
                printLabel.setText("Top Twenty Words");
                printData();
            }
        });

        box.getChildren().addAll(Demo_L, buttonD1, buttonD20);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * request an url ibput to display the results
     */
    public void showUrl() {
        Stage stage = new Stage();

        printData = new TextArea();
        printLabel = new Label();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label Url_L = new Label("My Url File");
        readFile myUrl = new readFile(input.getText());
        if (myUrl != null){
            isDemo=false;
            status= new Label("Url file");
        }
        getfileData(myUrl);
        buttonD1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Assume success alwaysys
                printData.setText(allwords);
                printLabel.setText("All Words");
                printData();
            }
        });
        buttonD20.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                printData.setText(top20);
                printLabel.setText("Top Twenty Words");

                printData();
            }
        });

        box.getChildren().addAll(Url_L, buttonD1, buttonD20);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * request a file input to display the results
     */
    public void showFile() {
        Stage stage = new Stage();

        printData = new TextArea();
        printLabel = new Label();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label myFile_L = new Label("My Local File");
        readFile myFile = null;
        try {
            myFile = new readFile(Paths.get(input.getText()));
        } catch (IOException e) {
            System.out.println("File not Found");
        } finally {
            System.out.println(myFile.fileFound);
            if (myFile != null) {
                myFile.fileFound=true;
                isDemo=false;
                status= new Label("Local file opened");
                getfileData(myFile);
            }


        }

        buttonD1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Assume success alwaysys
                printData.setText(allwords);
                printLabel.setText("All Words");
                printData();
            }
        });
        buttonD20.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                printData.setText(top20);
                printLabel.setText("Top Twenty Words");

                printData();
            }
        });

        box.getChildren().addAll(myFile_L, buttonD1, buttonD20);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * this method will generate the limits of the text
     */
    public void textLimit() {
        Stage stage = new Stage();

        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label label = new Label("My file ");

        TextField textStart = new TextField();
        textStart.setPromptText("enter start word");
        TextField textEnd = new TextField();
        textEnd.setPromptText("enter end word");

        Button btntextLimit = new Button();
        btntextLimit.setText("submit");
        Button btncancel = new Button();
        btncancel.setText("Cancel");

        btntextLimit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Assume success always!
                mytextStart = textStart.getText();
                mytextEnd = textEnd.getText();

                stage.close(); // return to main window
            }
        });

        btncancel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textlimit=false;
                stage.close(); // return to main window
            }
        });


        box.getChildren().addAll(label, textStart, textEnd, btntextLimit, btncancel);
        Scene scene = new Scene(box, 250, 150);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to print the resulting data
     */

    public void printData() {
        Stage stage = new Stage();

        VBox box = new VBox();
        box.setPadding(new Insets(10));


        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label label = new Label("My file ");

        Button btntextLimit = new Button();
        btntextLimit.setText("submit");

        btntextLimit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Assume success always!

                stage.close(); // return to main window
            }
        });

        box.getChildren().addAll(label, printLabel, printData);
        Scene scene = new Scene(box, 250, 150);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to sort the data
     */
    public void getfileData(readFile r) {

                r.cleanFile();
                if (isDemo){
                r.mySubstring("The Raven\n", "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***");
                }

                if (!isDemo) {
                    if (mytextStart != null) {
                        r.mySubstring(mytextStart, mytextEnd);
                    }
                }
        if (!textlimit) {
            if (mytextStart != null) {

            }
        }
        r.splittedWords();



                StringBuilder contents = new StringBuilder();
                StringBuilder contentsTwo = new StringBuilder();

                for (Pair<String, Integer> p : r.getWordsCount()) {
                    StringWriter writer = new StringWriter();
                    PrintWriter out = new PrintWriter(writer);
                    out.printf("%12s _____%2d \n", p.getKey(), p.getValue());
                    String output = writer.toString();
                    contents.append(output).toString();
                }
                allwords = contents.toString();

                for (Pair<String, Integer> p : r.getTop20Words()) {
                    StringWriter writer = new StringWriter();
                    PrintWriter out = new PrintWriter(writer);
                    out.printf("%12s _____%2d \n", p.getKey(), p.getValue());
                    String output = writer.toString();
                    contentsTwo.append(output).toString();
                }
                top20 = contentsTwo.toString();


    }
}