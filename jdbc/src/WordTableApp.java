import javafx.application.Application ;
import javafx.scene.control.TableView ;
import javafx.scene.control.TableColumn ;
import javafx.scene.control.cell.PropertyValueFactory ;
import javafx.scene.layout.BorderPane ;
import javafx.scene.Scene ;
import javafx.stage.Stage ;

public class WordTableApp extends Application {
    private WordDataAccessor dataAccessor ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        dataAccessor = new WordDataAccessor("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost/wordOccurrences","root","Deschamps200*.*"); // provide driverName, dbURL, user, password...

        TableView<Word> wordTable = new TableView<>();
        TableColumn<Word, String> wordCol = new TableColumn<>("Word");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        TableColumn<Word, Integer> frequencyCol = new TableColumn<>("Frequency");
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));


        wordTable.getColumns().addAll(wordCol, frequencyCol);

        wordTable.getItems().addAll(dataAccessor.getWordList());

        BorderPane root = new BorderPane();
        root.setCenter(wordTable);
        Scene scene = new Scene(root, 200, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (dataAccessor != null) {
            dataAccessor.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
