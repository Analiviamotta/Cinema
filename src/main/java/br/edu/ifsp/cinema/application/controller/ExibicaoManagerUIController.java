package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ConsultarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExcluirExibicaoUseCase;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ExibicaoManagerUIController {

    @FXML
    private TableView<Exibicao> tableView;

    @FXML
    private TableColumn<Exibicao, Filme> cFilme;

    @FXML
    private TableColumn<Exibicao, Sala> cSala;

    @FXML
    private TableColumn<Exibicao, LocalDateTime> cDataEHora;

    @FXML
    private TableColumn<Exibicao, Duration> cDuracao;

    @FXML
    private TableColumn<Exibicao, Integer> cQuantidadeDeIngressosDeDisponiveis;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnInativar;

    @FXML
    private Button btnDeletar;

    private ObservableList<Exibicao> tableData;

    @FXML
    private void intitalize(){
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void loadDataAndShow() {
        List<Exibicao> exibicoes = ConsultarExibicaoUseCase.findAll();
        tableData.clear();
        tableData.addAll(exibicoes);

    }

    private void bindColumnsToValueSources() {
        cFilme.setCellValueFactory(new PropertyValueFactory<>("filme"));
        cSala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        cDataEHora.setCellValueFactory(new PropertyValueFactory<>("dataEHora"));
        cDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));
        cQuantidadeDeIngressosDeDisponiveis.setCellValueFactory(new PropertyValueFactory<>("quantidadeDeIngressosDisponiveis"));

    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("MainUI");
    }

    public void addExibicao(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("exibicaoUI");
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        showExibicaoInMode(UIMode.UPDATE);
    }

    public void inactive(ActionEvent actionEvent) {
        Exibicao exibicao = tableView.getSelectionModel().getSelectedItem();
        if(exibicao != null){
            InativarFilmeUseCase.inativarFilme(exibicao.getId());
            loadDataAndShow();
        }
    }

    private void showExibicaoInMode(UIMode mode) throws IOException {
        Exibicao selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            HelloApplication.setRoot("exibicaoUI");
            ExibicaoUIController controller = (ExibicaoUIController) HelloApplication.getController();
            controller.setExibicao(selectedItem, mode);
        }

    }

    public void delete(ActionEvent actionEvent) {
        Exibicao selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ExcluirExibicaoUseCase.remove(selectedItem);
            loadDataAndShow();
        }
    }
}
