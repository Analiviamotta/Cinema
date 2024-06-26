package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.sala.ConsultarAssentoSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

    private ObservableList<Assento> tableData;

    private ConsultarAssentoSala consultarAssentoSala;
    private Sala sala;

    public AssentosUIController() {
    }

    @FXML
    public void initialize() {
        tableView.setItems(tableData);
        cColuna.setCellValueFactory(new PropertyValueFactory<>("coluna"));
        cLinha.setCellValueFactory(new PropertyValueFactory<>("linha"));
    }

    public void setSala(Sala sala) {
        this.sala = sala;
        loadAssentos();
    }

    private void loadAssentos() {
        if (sala != null) {
            List<Assento> assentos = ConsultarAssentoSala.consultarAssentos(sala.getId());
            if (assentos != null) {
                tableView.setItems(FXCollections.observableArrayList(assentos));
            }
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        System.out.println("Voltando para a cena anterior...");
        HelloApplication.setRoot("SalaManager");
        System.out.println("Cena anterior carregada com sucesso.");

    }

}