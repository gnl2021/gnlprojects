package wordsCount;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 */
public class menu {
    private static final String MY_URL_FILE = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
Boolean fileFound;
    public void menu() throws IOException {
        String choice;
        do {
            System.out.println("::::::::::::::::::::::::::::::::");
            System.out.println("Word Counters");
            System.out.println("::::::::::::::::::::::::::::::::");
            System.out.println("Read file from url (a)");
            System.out.println("Read file from file path (b)");
            System.out.println("Run the demo (d)");
            System.out.println("Quit the program (q)");
            System.out.println("::::::::::::::::::::::::::::::::");

            Scanner data = new Scanner(System.in);
            Scanner myPath = new Scanner(System.in);

            choice = data.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("Enter the url");
                    String myUrl = data.nextLine();
                    readFile My_UrlTEXT = new readFile(myUrl);
                    My_UrlTEXT.cleanFile();
                    My_UrlTEXT.splittedWords();
                    My_UrlTEXT.printStats();
                    break;
                case "b":
                    System.out.println("Enter the file path");
                    String myfile = myPath.next();
                    readFile My_FileTEXT = new readFile(Paths.get(myfile));
                    fileFound=My_FileTEXT.fileFound;
                    if (fileFound) {
                        My_FileTEXT.cleanFile();
                        System.out.println("Do you want to limit your text{y/n)");
                        String choice2 = data.nextLine().toLowerCase();
                        switch (choice2) {
                            case "y":
                                System.out.println("Enter the title");
                                String title = data.nextLine();
                                System.out.println("Enter the end");
                                String endText = data.nextLine();
                                My_FileTEXT.mySubstring(title, endText);
                                break;
                            case "n":
                                break;
                            default:
                                break;
                        }
                        My_FileTEXT.splittedWords();
                        My_FileTEXT.printStats();
                    }
                    break;
                case "d":
                    readFile myDemo = new readFile(MY_URL_FILE);
                    myDemo.cleanFile();
                    myDemo.mySubstring("The Raven\n", "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***");
                    myDemo.splittedWords();
                    myDemo.printStats();
                    break;
                case "q":
                    System.out.println("Goodbye...");
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    break;


            }
        } while (choice != "q");
    }
}