import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;



public class readFile {
    public String getOrigTEXT() {
        return origTEXT;
    }

    public void setOrigTEXT(String origTEXT) {
        this.origTEXT = origTEXT;
    }

    private String origTEXT;

    public String getMyCleanText() {
        return myCleanText;
    }

    public void setMyCleanText(String myCleanText) {
        this.myCleanText = myCleanText;
    }

    private String myCleanText;

    public String[] getMyWords() {
        return myWords;
    }

    public void setMyWords(String[] myWords) {
        this.myWords = myWords;
    }

    private String[] myWords;
    private ArrayList<Pair<String, Integer>> top20Words;

    public ArrayList<Pair<String, Integer>> getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(ArrayList<Pair<String, Integer>> wordsCount) {
        this.wordsCount = wordsCount;
    }

    private ArrayList<Pair<String, Integer>> wordsCount;


    public ArrayList<Pair<String, Integer>> getTop20Words() {
        return top20Words;
    }

    public void setTop20Words(ArrayList<Pair<String, Integer>> top20Words) {
        this.top20Words = top20Words;
    }

    public void printStats() {
        String choice;
        if (this.top20Words.size()==0){
            System.out.println("Empty file");
            System.exit(1);
        }
            else
        do {
            System.out.println("::::::::::::::::::::::::::::::::");
            System.out.println("Word Counters");
            System.out.println("::::::::::::::::::::::::::::::::");
            System.out.println("All words count descending list (a)");
            System.out.println("Top 20 words (b)");
            System.out.println("Quit the program (q)");
            System.out.println("::::::::::::::::::::::::::::::::");

            Scanner data = new Scanner(System.in);
            Scanner myPath = new Scanner(System.in);

            choice = data.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("All the words and their counts");
                    for (Pair<String, Integer> p :this.wordsCount ) {
                        System.out.printf("%12s _____%2d \n", p.getKey(), p.getValue());
                    }
                    break;
                case "b":
                    System.out.println("The top twenty words are");

                    for (Pair<String, Integer> p : this.top20Words) {
                        System.out.printf("%12s _____%2d \n", p.getKey(), p.getValue());
                    }
                    break;
                case "q":
                    System.out.println("Goodbye...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    break;
            }
        }while (choice != "q");
    }


    public readFile(String theUrl) {
        StringBuilder contents = new StringBuilder();
        // Use try and catch to avoid the exceptions
        try {
            URL url = new URL(theUrl); // creating a url object
            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object

            // wrapping the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // reading from the urlconnection using the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    contents.append(line + "\n");
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Url error");
            System.out.println("Exiting the program");
            System.exit(0);//terminating the program if the file is not found.
        }
        setOrigTEXT(contents.toString());

    }


    public String cleanFile() {// function to remove the html tags
        String temp = getOrigTEXT();
        String cleanOne = temp.replaceAll("\\<.*?\\>", "");// remove html tags
        String cleanTwo = cleanOne.replaceAll("(?m)^[ \t]*\r?\n", "");// remove empty lines
        String mycleanText2 = cleanTwo.replaceAll(Pattern.quote("&mdash"), "—");
        String mycleanText3 = mycleanText2.replaceAll("\\s{2,}", "\n");// remove extra blank spaces
        setMyCleanText(mycleanText3);
        return mycleanText3;
    }

    public String mySubstring(String startIndex, String endIndex) {// staring index as a string, ending index has a string
        String tempText = getMyCleanText();// create a string from the string to check;
        int pos = tempText.lastIndexOf(startIndex);// determine the start position
        int pos2 = tempText.indexOf(endIndex);// determine the end position
        String subText = tempText.substring(pos, pos2);
        return this.myCleanText = subText;
    }

    public void splittedWords() {
        String tempWords = getMyCleanText().toLowerCase();//all words to lowercase to minimise duplicates
        String mysplittedWords[] = tempWords.split("[\\s.;,?:!()\"]+");
        //a map to verify if the data is present
        Map<String, Integer> wordsMap = new HashMap<>();

        for (String Word : mysplittedWords) {
            String tempWord = Word.trim();
            Word = tempWord;
        }
        this.myWords = mysplittedWords;
        //populate the map with the string and their value counts
        for (String Word : mysplittedWords) {
            //check if the string exist
            if (Word.length() > 0) {
                if (wordsMap.containsKey(Word)) {
                    wordsMap.put(Word, wordsMap.get(Word) + 1);
                } else {
                    wordsMap.put(Word, 1);

                }
            }
        }
  //Create an Array List of pairs from the map
        ArrayList<Pair<String, Integer>> wordCounts = new ArrayList<Pair<String, Integer>>(wordsMap.size());
        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            //add the values from the map
            wordCounts.add((new Pair<String, Integer>(entry.getKey(), entry.getValue())));
        }
        //sort based on the count value
        wordCounts.sort(Comparator.<Pair<String, Integer>>comparingInt(Pair::getValue).thenComparingInt(Pair::getValue));
        Collections.reverse(wordCounts);// reverse the count order to have a descending list.
        setWordsCount(wordCounts);// list with all the words sorted from most occurences
        // get an array list with the top 20 words
        if (wordCounts.size()<=20){
            ArrayList<Pair<String, Integer>> topTwenty = new ArrayList<Pair<String, Integer>>(wordCounts);
            setTop20Words(topTwenty);
        }
        else if((wordCounts.size()>20)){
        ArrayList<Pair<String, Integer>> topTwenty = new ArrayList<Pair<String, Integer>>(wordCounts.subList(0, 20));
        //check for value count the same as the last item
        Integer lastVal = topTwenty.get(19).getValue();
        int i;
        for (i = 20; i < wordCounts.size(); i++) {
            int lastDuplicate = wordCounts.get(i).getValue();
            if (lastDuplicate == lastVal) {
                topTwenty.add(wordCounts.get(i));
                setTop20Words(topTwenty);
            }
        }
        }
    }

    public readFile(Path file) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.toFile()));

            StringBuilder myText = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                myText.append(line).append("\n");
            }
            setOrigTEXT(myText.toString());
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println("Exiting the program");
            System.exit(0);//terminating the program if the file is not found.
        }

    }
}