package primeNumServer;
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * <pre>
 * Client side of a prime number check server
 * based on various sources from the web
 * will send a number to the server part
 * and display a message depending on the boolean response value
 * </pre>
 * @author Gregory Lauture
 * @version 1.0
 * @since 2021 -12-06
 */
public class Client extends Application {
  // IO streams
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Panel p to hold the label and text field
    BorderPane paneForTextField = new BorderPane();
    paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
    paneForTextField.setStyle("-fx-border-color: green");
    paneForTextField.setLeft(new Label("Enter a number: "));
    
    TextField tf = new TextField();
    tf.setAlignment(Pos.BOTTOM_RIGHT);
    paneForTextField.setCenter(tf);
    
    BorderPane mainPane = new BorderPane();
    // Text area to display contents
    TextArea ta = new TextArea();
    mainPane.setCenter(new ScrollPane(ta));
    mainPane.setTop(paneForTextField);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(mainPane, 450, 200);
    primaryStage.setTitle("Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    tf.setOnAction(e -> {
      try {
        // Get the radius from the text field
        int mynumber = Integer.parseInt(tf.getText().trim());

        // Send the radius to the server
        toServer.writeInt(mynumber);
        toServer.flush();
  
        // Get area from the server
        boolean isPrime = fromServer.readBoolean();

        // Display to the text area
        if(isPrime) {
          ta.appendText("You entered " + mynumber + "\n");
          ta.appendText(mynumber + " is prime" +'\n');
        }

        if(!isPrime) {
          ta.appendText("You entered " + mynumber + "\n");
          ta.appendText(mynumber + " is not prime" +'\n');
        }
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    });
  
    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("127.0.0.1", 8000);


      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
      ta.appendText(ex.toString() + '\n');
    }
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
