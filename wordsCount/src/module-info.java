module wordsCount {

    requires javafx.graphics;
    requires javafx.controls;
    opens wordsCount to javafx.graphics;
    exports wordsCount;
}
