package wordsCount;
import java.io.IOException;


/**
 * The type Word counter.
 */
public class wordCounter {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        menu myMenu= new menu();
        myMenu.menu();
    }
}