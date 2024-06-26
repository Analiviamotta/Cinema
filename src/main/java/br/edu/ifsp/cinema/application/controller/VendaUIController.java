package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ConsultarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.venda.CriarVendaUseCase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class VendaUIController {
    @FXML
    public TableColumn<Exibicao, Integer> cNumeroDeIngressosDisponíveis;
    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnAdicionarIngressos;

    @FXML
    private TableView<Exibicao> tableView;

    @FXML
    private TableColumn<Exibicao, String> cFilme;

    @FXML
    private TableColumn<Exibicao, LocalDateTime> cDataEHora;

    @FXML
    private TableColumn<Exibicao, Integer> cSala;

    @FXML
    private TableColumn<Exibicao, Integer> cDuracao;


    private ObservableList<Exibicao> tableData;

    @FXML
    public void initialize() {
        tableData = FXCollections.observableArrayList();

        loadExibicoes();
        tableView.setItems(tableData);

        cFilme.setCellValueFactory(cellData -> {

            Filme filme = cellData.getValue().getFilme();
            return new SimpleStringProperty(filme.getTitulo());
        });

        cDataEHora.setCellValueFactory(new PropertyValueFactory<>("horarioData"));
        cSala.setCellValueFactory(cellData -> {
            Sala sala = cellData.getValue().getSala();
            return new SimpleIntegerProperty(sala.getNumber()).asObject();
        });

        cDuracao.setCellValueFactory(new PropertyValueFactory<>("tempoDuracao"));
        cNumeroDeIngressosDisponíveis.setCellValueFactory(new PropertyValueFactory<>("qntIngressosDisponiveis"));
    }

    public void loadExibicoes() {
        tableData.addAll(ConsultarExibicaoUseCase.findAll());
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("MainUI");
    }

    public void addTickets(ActionEvent actionEvent) throws IOException {
        Exibicao selectedExibicao = tableView.getSelectionModel().getSelectedItem();
        IngressoUIController.setExibicao(selectedExibicao);

        HelloApplication.setRoot("IngressoUI");


    }

    public void save(ActionEvent actionEvent) {
        Exibicao selectedExibicao = tableView.getSelectionModel().getSelectedItem();


        List<Ingresso> ingressos = IngressoUIController.getIngressosSelecionados();


        CriarVendaUseCase.criarVenda(selectedExibicao.getId(), ingressos);


        try {
            HelloApplication.setRoot("VendaManagerUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVenda(Venda venda, UIMode uiMode) {

    }
}