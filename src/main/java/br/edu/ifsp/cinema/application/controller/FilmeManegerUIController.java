package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.filme.AtivarFilmeUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.ConsultarFilmesUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.ExcluirFilmeUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.InativarFilmeUseCase;
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
    public void addFilme(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("filmeUI");

    }

    public void editFilme(ActionEvent actionEvent) throws IOException {
        showFilmeInMode(UIMode.UPDATE);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("MainUI");
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
        if(filme != null) {
            HelloApplication.setRoot("filmeUI");

            FilmeUIController controller = (FilmeUIController) HelloApplication.getController();
            controller.setFilme(filme, uiMode);

        }

    }
    public void deleteFilme(ActionEvent actionEvent) {
        Filme filme = tableView.getSelectionModel().getSelectedItem();
        if(filme != null){
            ExcluirFilmeUseCase.remove(filme);
            loadDataAndShow();
        }
    }

    public void active(ActionEvent actionEvent) {
        Filme filme = tableView.getSelectionModel().getSelectedItem();
        if(filme != null){
            AtivarFilmeUseCase.ativarFilme(filme.getId());
            loadDataAndShow();
        }
    }
}
