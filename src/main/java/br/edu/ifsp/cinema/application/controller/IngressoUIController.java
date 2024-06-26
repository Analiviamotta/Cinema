package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.usecases.venda.CriarVendaUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IngressoUIController {
    @FXML
    private TableView<Ingresso> tableView;

    @FXML
    private TableColumn<Ingresso, String> cAssento;

    @FXML
    private TableColumn<Ingresso, Exibicao> cExibicao;

    @FXML
    private TableColumn<Ingresso, BigDecimal> cPreco;

    @FXML
    private TableColumn<Ingresso, String> cDataHora;

    @FXML
    private TableColumn<Ingresso, Integer> cSala;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnImprimir;

    private ObservableList<Ingresso> tableData;
    private static List<Ingresso> ingressosSelecionados;

    private static Exibicao exibicaoSelecionada;

    public static void setExibicao(Exibicao exibicao) {
        exibicaoSelecionada = exibicao;
    }

    @FXML
    public void initialize() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);

        cAssento.setCellValueFactory(new PropertyValueFactory<>("assento"));
        cExibicao.setCellValueFactory(new PropertyValueFactory<>("exibicao"));
        cPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        cDataHora.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        cSala.setCellValueFactory(new PropertyValueFactory<>("sala"));

        ingressosSelecionados = new ArrayList<>();
    }

    public void backToPreviousScene() throws IOException {
        HelloApplication.setRoot("VendaUI");
    }

    public void print() {

    }

    public void salvarIngressos() {

        this.ingressosSelecionados = tableView.getSelectionModel().getSelectedItems();
        ingressosSelecionados.addAll(ingressosSelecionados);


        try {
            HelloApplication.setRoot("VendaUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ingresso> getIngressosSelecionados() {
        return ingressosSelecionados;
    }
}