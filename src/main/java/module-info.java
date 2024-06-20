module br.edu.ifsp.cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires java.sql;
    requires sqlite.jdbc;


    opens br.edu.ifsp.cinema to javafx.fxml;
    exports br.edu.ifsp.cinema;
}