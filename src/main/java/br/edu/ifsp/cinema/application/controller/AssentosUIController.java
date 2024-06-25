package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.sala.ConsultarAssentoSala;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class AssentosUIController {

    @FXML
    private TableView<Assento> tableView;

    @FXML
    private TableColumn<Assento, Integer> cColuna;

    @FXML
    private TableColumn<Assento, Integer> cLinha;

    @FXML
    private Button btnVoltar;

    private ConsultarAssentoSala consultarAssentoSala;
    private Sala sala;

    public AssentosUIController() {
    }

    @FXML
    public void initialize() {
        cColuna.setCellValueFactory(new PropertyValueFactory<>("coluna"));
        cLinha.setCellValueFactory(new PropertyValueFactory<>("linha"));
    }

    public void setSala(Sala sala) {
        this.sala = sala;
        loadAssentos();
    }

    private void loadAssentos() {
        if (sala != null) {
            List<Assento> assentos = consultarAssentoSala.consultarAssentos(sala.getId());
            tableView.setItems(FXCollections.observableArrayList(assentos));
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("salaUI");
    }

}
