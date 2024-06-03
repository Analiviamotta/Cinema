module br.edu.ifsp.cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;


    opens br.edu.ifsp.cinema to javafx.fxml;
    exports br.edu.ifsp.cinema;
}