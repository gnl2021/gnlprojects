 

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty ;
import javafx.beans.property.SimpleStringProperty ;
public class Word {
    private final StringProperty word = new SimpleStringProperty(this, "Word");
    public StringProperty wordProperty() {
        return word ;
    }
    public final String getWord() {
        return wordProperty().get();
    }
    public final void setWord(String word) {
        wordProperty().set(word);
    }

    private final IntegerProperty frequency = new SimpleIntegerProperty(this, "Frequency");
    public IntegerProperty frequencyProperty() {
        return frequency ;
    }
    public final Integer getFrequency() {
        return frequencyProperty().get();
    }
    public final void setFrequency(int frequency) {
        frequencyProperty().set(frequency);
    }





    public Word() {}

    public Word(String word, int frequency) {
        setWord(word);
        setFrequency(frequency);


    }

}
