 package primeNumServer;

 import java.io.*;
 import java.net.*;
 import java.util.Date;
 import javafx.application.Application;
 import javafx.application.Platform;
 import javafx.scene.Scene;
 import javafx.scene.control.ScrollPane;
 import javafx.scene.control.TextArea;
 import javafx.stage.Stage;

 /**
 * <pre>
 * Server side of a prime number check server
 * Must be started prior to receive the client input
 * based on various sources from the web
 * will receive a number from a client
 * and send a boolean as response value
 * </pre>
 * @author Gregory Lauture
 * @version 1.0
 * @since 2021 -12-06
 */
public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {


    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
  
        while (true) {
          // Receive radius from the client
          int mynumber = inputFromClient.readInt();
  
          // Compute area
          boolean prime = isPrime( mynumber) ;
  
          // Send area back to the client
          outputToClient.writeBoolean(prime);
  
          Platform.runLater(() -> {
            ta.appendText("Number received from client of check prime number is : "
              + mynumber + '\n');
            if (prime){
            ta.appendText(mynumber + " is prime \n");}
            if (!prime){
              ta.appendText(mynumber +  " is not prime \n");}

          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * will check if the number is prime and return a boolean as response
   * @param mynumber
   * @return
   */
  boolean isPrime(int mynumber) {
    boolean flag = false;
    if (mynumber == 0 || mynumber == 1) {
       flag = false;
    }
      for (int i = 2; i <= mynumber / 2; ++i) {
        // condition for prime number
        if (mynumber % i == 0) {
           flag = false;
          break;
        }
        if (mynumber % i != 0) {
           flag = true;
        }
      }

    return flag;

  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
