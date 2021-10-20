module Fibonnaci {
    requires javafx.graphics;
    requires javafx.controls;
    opens Fibonnaci to javafx.graphics;
    exports Fibonnaci;
}