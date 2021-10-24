package wordsCount;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;


public class SGUY extends Application {
    private static final String MY_URL_FILE = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    TextArea textArea;
    TextField input;
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox();
        VBox layout2 = new VBox();
        VBox layout3 = new VBox();
        VBox layoutP = new VBox();



        layout.setAlignment(Pos.TOP_CENTER);
        layout2.setAlignment(Pos.TOP_CENTER);
        layout3.setAlignment(Pos.TOP_CENTER);
        layoutP.setAlignment(Pos.CENTER);



        Scene scene = new Scene(layout, 300, 300);
        Scene scene2 = new Scene(layout2, 300, 300);
        Scene scene3 = new Scene(layout3, 300, 480);
        Scene sceneP = new Scene(layoutP, 300, 300);



        Label label1 = new Label("Demo");
        Label label2 = new Label("open Url");
        Label label3 = new Label("open file");
        Label labelP = new Label("print words count");
        Label labelP20 = new Label("print Top 20 words");

        Button buttonP = new Button("switch to main");
        buttonP.setOnAction(e -> primaryStage.setScene(sceneP));


        Button button = new Button("run Demo");
        Button back_button = new Button("switch to main");
        button.setOnAction(e -> primaryStage.setScene(scene));
        back_button.setOnAction(e -> primaryStage.setScene(sceneP));


        Button button2 = new Button("from url");
        Button back_button2 = new Button("switch to main");
        button2.setOnAction(e -> primaryStage.setScene(scene2));
        back_button2.setOnAction(e -> primaryStage.setScene(sceneP));

        Button button3 = new Button("from file");
        Button back_button3 = new Button("switch to main");
        button3.setOnAction(e -> primaryStage.setScene(scene3));
        back_button3.setOnAction(e -> primaryStage.setScene(sceneP));

        input = new TextField();



        TextField text = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();
        textArea = new TextArea();


        text2.setMaxWidth(100);

        text3.setMaxWidth(100);


        layout.getChildren().addAll(label1, button, textArea, back_button);
        layout2.getChildren().addAll(label2, button2, text2, back_button2);
        layout3.getChildren().addAll(label3, button3, text3, back_button3);
        layoutP.getChildren().addAll(button, button2, button3);

        EventHandler<ActionEvent> event = e -> {
            primaryStage.setScene(scene);
            primaryStage.show();
            readFile demo = new readFile(MY_URL_FILE);
           demo.cleanFile();
           demo.mySubstring("The Raven\n", "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***");
            demo.splittedWords();
            StringBuilder contents = new StringBuilder();
            String top20Demo;

            for (Pair<String, Integer> p : demo.getTop20Words()) {
                contents.append( p.getKey());
                contents.append("_____\t");
                contents.append(p.getValue());
                contents.append("\n");   }
            top20Demo=contents.toString();

            textArea.setPrefSize(640, 400);
            textArea.setText(top20Demo);

        };

        // when button is pressed
        button.setOnAction(event);





        primaryStage.setTitle("wordsCount");
        primaryStage.setScene(sceneP);
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}