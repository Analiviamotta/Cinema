module br.edu.ifsp.cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires java.sql;
    requires sqlite.jdbc;

    opens br.edu.ifsp.cinema.domain.entities.filme to javafx.base;

    opens br.edu.ifsp.cinema.application.view to javafx.fxml;
    exports br.edu.ifsp.cinema.application.view;
    exports br.edu.ifsp.cinema.application.controller;
    opens br.edu.ifsp.cinema.application.controller to javafx.fxml;
}
