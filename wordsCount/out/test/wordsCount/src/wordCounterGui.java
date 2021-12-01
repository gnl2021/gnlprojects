package wordsCount;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

/**
 * The program use javafx libraries to display a basic gui,
 * which accept a input from a Url or local file,
 * then display the result for all the words and their counts.
 * Also the top 20 words and their counts
 * Added database functionality
 * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/main WordCounterGui.png" alt="Main">
 *
 * @author Gregory Lauture
 * @version 2.0 on 2021-12-01
 * @since 2021 -10-27
 */



public class wordCounterGui extends Application {
    private jdbc wordData ;
    private TableView wordTable = new TableView();


    private static final String MY_URL_FILE = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    /**
     * requires the input of a Url or a local file path for the start of the multiple operations.
     * It terminates the program in case of error.
     */
    TextField input;
    /**
     * It will display the query result of the button pressed.
     */
    TextArea printData;
    /**
     * It identifies th query, whether for all the words or the top 20..
     */
    Label printLabel;
    Label dataBase;



    /**
     * It is used to limit the text with a beginning string.
     */
    static String mytextStart;
    /**
     * It is used to limit the text with a end string.
     */
    static String mytextEnd;
    /**
     * It will display which option is performed, whether demo or other for the print menu.
     */
    static Label status;
    /**
     * It's used to check if the user intend to limit the text.
     */
    static boolean textlimit;
    /**
     * It is used to check whether to use the demo mode of operations.
     */
    static boolean isDemo;


    /**
     * String used to store all the words.
     */
    String allwords;
    /**
     * String used to store the top twenty words.
     */
    String top20;

    /**
     * Upon press, it will display all the words and their counts.
     */
    static Button buttonD1 = new Button("word counts");
    /**
     * Upon press, it will display the top twenty words.
     */
    static Button buttonD20 = new Button("top20");
    /**
     * The user choice to limit the text used .
     */
    static Button myTextLimit = new Button("Set Text limits");
    static Button buttonData = new Button("Database");

    static Map<String, Integer> wordsMap = new HashMap<>();





    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }



    @Override
    /**
     * The start method open the main gui menu
     */
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox();
        Button btnDemo = new Button();
        btnDemo.setText("Demo");
        btnDemo.setOnAction(event -> showDemo());
        input = new TextField();
        Button btnUrl = new Button();
        btnUrl.setText("Open Url");
        btnUrl.setOnAction(event -> showUrl());
        Button btnFile = new Button();
        btnFile.setText("Open File");
            btnFile.setOnAction(event -> showFile());
        myTextLimit.setOnAction(event -> {
            // Assume success always!
            textLimit();
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
     *  * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/mainDemo1.png" alt="Demo">
     */

    public void showDemo() {
        Stage stage = new Stage();

        printData = new TextArea();
        printLabel = new Label();
        dataBase= new Label();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label Demo_L = new Label("Demo");
        readFile demo = new readFile(MY_URL_FILE);
            isDemo=true;
            status= new Label("Demo file");
        getfileData(demo);

        buttonD1.setOnAction(event -> {
            // Assume success always
            printData.setText(allwords);
            printLabel.setText("All Words");
            printData();
        });
        buttonD20.setOnAction(event -> {
            printData.setText(top20);
            printLabel.setText("Top Twenty Words");
            printData();
        });
        buttonData.setOnAction(event -> {
            dataBase.setText("Open Database");
            try {
                jdbcOps();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        box.getChildren().addAll(Demo_L, buttonD1, buttonD20, buttonData);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The method request an url input. It will use to create a url object and display the results.
     * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/mainMyUrl.png" alt="MyUrl">
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
            isDemo=false;
            status= new Label("Url file");
        getfileData(myUrl);
        buttonD1.setOnAction(event -> {
            // Assume success always
            printData.setText(allwords);
            printLabel.setText("All Words");
            printData();
        });
        buttonD20.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                printData.setText(top20);
                printLabel.setText("Top Twenty Words");
                printData();
            }
        });
        buttonData.setOnAction(event -> {
            dataBase.setText("Open Database");
            try {
                jdbcOps();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        box.getChildren().addAll(Url_L, buttonD1, buttonD20, buttonData);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * request a file input to display the results
     *     <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/file enter.png" alt= "enter file">
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
            myFile.fileFound=true;
        } catch (IOException e) {
            out.println("File not Found");
        } finally {
            if (myFile==null){
                out.println("null object");
            }
        else if (myFile!=null){

            out.println("my file is"+myFile.fileFound);

                myFile.fileFound=true;
                isDemo=false;
                status= new Label("Local file opened");
                getfileData(myFile);}


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

        buttonData.setOnAction(event -> {
            dataBase.setText("Open Database");
            try {
                jdbcOps();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        box.getChildren().addAll(myFile_L, buttonD1, buttonD20, buttonData);
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * this method will generate the limits of the text
     * it requires to enter a url or file path first to work
     * It may generate errors depending on the structure of the text
     * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/textlimit.png" alt ="text limit">
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

        btntextLimit.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent event) {
                // Assume success always!
                mytextStart = textStart.getText();
                mytextEnd = textEnd.getText();

                stage.close(); // return to main window
            }
        });

        btncancel.setOnAction(event -> {
            textlimit=false;
            stage.close(); // return to main window
        });


        box.getChildren().addAll(label, textStart, textEnd, btntextLimit, btncancel);
        Scene scene = new Scene(box, 250, 150);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to print the resulting data.
     * Based on user selection, it will print all the words or the top twenty
     * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/printallwords.png" alt="print all words">
     * <img src="/Users/impdmacbook/Documents/GitHub/gnlprojects/projects/gnlprojects/wordsCount/doc-files/printtop20.png" alt="print top 20">
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
     *
     * @param r is a file object or Url object used for operations
     */
    public void getfileData(readFile r) {
        assert r!= null;

                r.cleanFile();
                if (isDemo){
                r.mySubstring("The Raven\n", "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***");
                }

                if (!isDemo) {
                    if (mytextStart != null) {
                        r.mySubstring(mytextStart, mytextEnd);
                    }
                    if (!textlimit) {
                        if (mytextStart == null) {

                        }
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

                // populate the map to use fill the database
        for (Pair<String, Integer> p : r.getWordsCount()) {
            wordsMap.put(p.getKey(), p.getValue());

        }
                allwords = contents.toString();

               for (Pair<String, Integer> p : r.getTop20Words()) {
                    StringWriter writer2 = new StringWriter();
                    PrintWriter out2 = new PrintWriter(writer2);
                    out2.printf("%12s _____%2d \n", p.getKey(), p.getValue());
                    String output2 = writer2.toString();
                    contentsTwo.append(output2).toString();
                }
                top20 = contentsTwo.toString();


    }

    /**
     * method that use the jdbc class to add database functionality to the main gui
     * @throws Exception
     */

    
    public void jdbcOps()throws Exception{
        /** Create a new object to get the data from the database.
         * fill it with data if it's empty
         */

        Scene scene = new Scene(new Group());
        Stage stage = new Stage();

        stage.setTitle("Table View Sample");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Word Database");
        label.setFont(new Font("Arial", 20));
        wordData = new jdbc();
        wordData.createTables();
        wordData.clearTables();

        boolean check= wordData.filldata();
        //fill the database for the first time if it's empty

        if (!check){
            for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
                wordData.insertWord(entry.getKey(), entry.getValue());
            }
        }

        TableColumn<Word, String> wordCol = new TableColumn<>("Word");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        TableColumn<Word, Integer> frequencyCol = new TableColumn<>("Frequency");
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        wordTable.getColumns().addAll(wordCol, frequencyCol);
        wordTable.getItems().addAll(wordData.getWordList());
        //Constrain the table to fit the view
        wordTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        wordCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        frequencyCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width


        TableView<Word> SwordTable = new TableView<>();
        TableColumn<Word, String> SwordCol = new TableColumn<>("Word");
        SwordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        TableColumn<Word, Integer> SfrequencyCol = new TableColumn<>("Frequency");
        SfrequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        //Constrain the table to fit the view

        SwordTable.getColumns().addAll(SwordCol, SfrequencyCol);
        SwordTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        SwordCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        SfrequencyCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        Stage stage2 = new Stage();


        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        AtomicReference<VBox> Sbox = new AtomicReference<>(new VBox());
        Sbox.get().setPadding(new Insets(10));



        // How to center align content in a layout manager in JavaFX
        vbox.setAlignment(Pos.CENTER);

        input = new TextField();


        Button searchData= new Button();
        searchData.setText("search");

        Button addData= new Button();
        addData.setText("add/update");

        Button clearData= new Button();
        clearData.setText("clear all records");

        searchData.setOnAction (event -> {
            SwordTable.getItems().clear();//clear the tableview after every search
            status.setText("");
            StringWriter writer2 = new StringWriter();
            PrintWriter out2 = new PrintWriter(writer2);
            String myword=input.getText();
            StringBuilder checkWord = new StringBuilder();
            if (wordData.checkWord(myword)==false){

                out2.printf("Word %s is not in database\n", myword);
                String output2 = writer2.toString();
                checkWord.append(output2).toString();
                status.setText(checkWord.toString());}
            else {
                out2.printf("Word %s is in database\n", myword);
                String output2 = writer2.toString();
                checkWord.append(output2).toString();
                status.setText(checkWord.toString());}
            try {
                SwordTable.getItems().addAll(wordData.selectWordList(myword));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Sbox.set(new VBox());
            Sbox.get().getChildren().add(SwordTable);
            Scene scene2 = new Scene(Sbox.get(), 250, 400);
                stage2.setScene(scene2);
                stage2.show();


        });

        addData.setOnAction(event -> {
            status.setText("");
            StringWriter writer2 = new StringWriter();
            PrintWriter out2 = new PrintWriter(writer2);
            String myword=input.getText();
            StringBuilder checkWord = new StringBuilder();
            if (wordData.checkWord(myword)==false){
                if (wordData.checkWord(myword)==false)
                    wordData.insertWord(myword, 1);
                out2.printf("Word %s is not in database\n", myword);
                String output2 = writer2.toString();
                checkWord.append(output2).toString();
                status.setText(checkWord.toString());}
            else {
                wordData.updateWord(myword);
                out2.printf("Word %s is in database\n", myword);
                String output2 = writer2.toString();
                checkWord.append(output2).toString();
                status.setText(checkWord.toString());}

        });

        clearData.setOnAction(event -> {

            stage2.close();
        });



        vbox.getChildren().addAll(label, input,searchData,status,wordTable);
        scene = new Scene(vbox);
        stage.setScene(scene);

        stage.show();
}
}