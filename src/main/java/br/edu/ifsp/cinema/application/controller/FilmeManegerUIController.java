package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.filme.ConsultarFilmesUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.InativarFilmeUseCase;
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

public class FilmeManegerUIController {
    @FXML
    private TableView<Filme> tableView;

    @FXML
    private TableColumn<Filme, String> cTitulo;
    @FXML
    private TableColumn<Filme, String> cGenero;
    @FXML
    private TableColumn<Filme, String> cSinopse;
    @FXML
    private TableColumn<Filme, String> cClassificacaoIndicativa;
    @FXML
    private TableColumn<Filme, String> cStatus;

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnDetalhar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnAtivar;

    private ObservableList<Filme> tableData;

    @FXML
    public void initialize() {
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindColumnsToValueSources() {
        cTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        cGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        cSinopse.setCellValueFactory(new PropertyValueFactory<>("sinopse"));
        cClassificacaoIndicativa.setCellValueFactory(new PropertyValueFactory<>("classificacaoIndicativa"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Filme> filmes = ConsultarFilmesUseCase.findAll();
        tableData.clear();
        tableData.addAll(filmes);

    }
    public void addFilme(ActionEvent actionEvent) {
    }

    public void editFilme(ActionEvent actionEvent) {
    }

    public void backToPreviousScene(ActionEvent actionEvent) {
    }

    public void inactivateFilme(ActionEvent actionEvent) {
       Filme filme = tableView.getSelectionModel().getSelectedItem();
       if(filme != null){
           InativarFilmeUseCase.inativarFilme(filme.getId());
           loadDataAndShow();
       }

    }

    private void showFilmeInMode(UIMode uiMode) throws IOException {
        Filme filme = tableView.getSelectionModel().getSelectedItem();
        if(filme != null) HelloApplication.setRoot("filmeUI");

    }
    public void deleteFilme(ActionEvent actionEvent) {
    }

    public void active(ActionEvent actionEvent) {
    }
}
