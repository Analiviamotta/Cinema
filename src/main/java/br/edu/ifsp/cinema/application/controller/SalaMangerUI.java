package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.ConsultarSalasUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.EditarSalaUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.ExcluirSalaUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class SalaMangerUI {

    @FXML
    private TableView<Sala> tableView;

    @FXML
    private TableColumn<Sala, Integer> cNumero;

    @FXML
    private TableColumn<Sala, Integer> cNumLinhas;

    @FXML
    private TableColumn<Sala, Integer> cNumColunas;

    @FXML
    private TableColumn<Sala, Integer> cCapacidade;

    @FXML
    private TableColumn<Sala, String> cStatus;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnInativar;

    @FXML
    private Button btnAtivar;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    private EditarSalaUseCase editarSalaUseCase;
    private ExcluirSalaUseCase excluirSalaUseCase;
    private ObservableList<Sala> tableData;

    @FXML
    public void initialize() {
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindColumnsToValueSources() {
        cNumero.setCellValueFactory(new PropertyValueFactory<>("number"));
        cNumLinhas.setCellValueFactory(new PropertyValueFactory<>("numLinhas"));
        cNumColunas.setCellValueFactory(new PropertyValueFactory<>("numColunas"));
        cCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Sala> salas = ConsultarSalasUseCase.findAll();
        tableData = FXCollections.observableArrayList(salas);
        tableView.setItems(tableData);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("MainUI");
    }

    public void addSala(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("SalaUI");
    }

    public void active(ActionEvent actionEvent) {
        Sala sala = tableView.getSelectionModel().getSelectedItem();
        if (sala != null) {
            sala.setStatus(SalaStatus.valueOf("Ativo"));
            EditarSalaUseCase.update(sala);
            loadDataAndShow();
        }
    }

    public void delete(ActionEvent actionEvent) {
        Sala sala = tableView.getSelectionModel().getSelectedItem();
        if (sala != null) {
            ExcluirSalaUseCase.remove(sala);
            loadDataAndShow();
        }
    }

    public void inactive(ActionEvent actionEvent) {
        Sala sala = tableView.getSelectionModel().getSelectedItem();
        if (sala != null) {
            sala.setStatus(SalaStatus.valueOf("Inativo"));
            EditarSalaUseCase.update(sala);
            loadDataAndShow();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        showSalaInMode(UIMode.UPDATE);
    }

    private void showSalaInMode(UIMode uiMode) throws IOException {
        Sala sala = tableView.getSelectionModel().getSelectedItem();
        if (sala != null) {
            HelloApplication.setRoot("SalaUI");

            SalaUIfxml controller = (SalaUIfxml) HelloApplication.getController();
            controller.setSala(sala, uiMode);
        }
    }
}