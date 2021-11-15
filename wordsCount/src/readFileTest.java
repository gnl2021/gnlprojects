package wordsCount;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * this test is intended as an integration unit
 * * not a simple method testing
 */
class readFileTest {

    /**
     * The My url text.
     */
    readFile myUrlText = new readFile("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
    /**
     * The Text sample.
     */
    static String textSample = "";
    /**
     * The Compare words.
     */
    String compareWords = "Ah, distinctly I remember it was in the bleak December,\n" +
            "And each separate dying ember wrought its ghost upon the floor.\n" +
            "Eagerly I wished the morrow;—;vainly I had sought to borrow\n" +
            "From my books surcease of sorrow—;sorrow for the lost Lenore—;\n" +
            "For the rare and radiant maiden whom the angels name Lenore—;\n" +
            "Nameless here for evermore.\n";


    /**
     * Read url.
     */
    @Before
    public void readUrl() {

    }

    /**
     * Filefound.
     */
    @DisplayName("Boolean fileFound to check if the file exist")
    @Test
    void filefound() {
        boolean expected = true;
        boolean found = myUrlText.fileFound;
        Assertions.assertEquals(expected, found);
    }

    /**
     * My substring.
     */
    @DisplayName("my subString to verify if a text section is accurate")
    @Test
    void mySubstring() {
        myUrlText.cleanFile();
        myUrlText.mySubstring("Ah", "And the silken");
        String myWords = myUrlText.getMyCleanText();
        textSample = myWords;
        System.out.printf(myWords);

        Assertions.assertEquals(compareWords, myWords);
    }

    /**
     * Splitted words.
     */
    @Test
    @DisplayName("my splittedWords to check for a specific word and the count ")
    void splittedWords() {
        myUrlText.setMyCleanText(compareWords);
        myUrlText.splittedWords();
        String textS= "the";
        String textS2= "upon";
        Integer val = 6;
        Integer val2 = 1;



//verify a specific element is found in the all words list
        Assertions.assertEquals(textS, myUrlText.getWordsCount().get(0).getKey());
        Assertions.assertEquals(val, myUrlText.getWordsCount().get(0).getValue());
        Assertions.assertEquals(textS2, myUrlText.getWordsCount().get(40).getKey());
        Assertions.assertEquals(val2, myUrlText.getWordsCount().get(40).getValue());

        //verify a specific element is found in the top 20 list

        String w20textS= "the";
        String w20textS2= "was";
        Integer w20val = 6;
        Integer w20val2 = 1;

        Assertions.assertEquals(w20textS, myUrlText.getTop20Words().get(0).getKey());
        Assertions.assertEquals(w20val, myUrlText.getTop20Words().get(0).getValue());
        Assertions.assertEquals(w20textS2, myUrlText.getTop20Words().get(19).getKey());
        Assertions.assertEquals(w20val2, myUrlText.getTop20Words().get(19).getValue());


        }


    }

