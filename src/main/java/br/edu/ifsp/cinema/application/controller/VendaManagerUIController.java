package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.venda.CreateVendaReportUseCase;
import br.edu.ifsp.cinema.domain.usecases.venda.CancelarVendaUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class VendaManagerUIController {
    @FXML
    public TableColumn<Venda, Double> CPreco;
    @FXML
    private TableView<Venda> tableView;

    @FXML
    private TableColumn<Venda, Integer> cId;

    @FXML
    private TableColumn<Venda, LocalDate> cData;

    @FXML
    private TableColumn<Venda, Exibicao> cExibicao;



    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnFiltrar;

    @FXML
    private DatePicker dateDataInicial;

    @FXML
    private DatePicker dateDataFinal;

    private ObservableList<Venda> tableData;

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void loadDataAndShow() {
        List<Venda> vendas = CreateVendaReportUseCase.findAllByPeriod(null, null);
        tableData.clear();
        tableData.addAll(vendas);
    }

    private void bindColumnsToValueSources() {
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cData.setCellValueFactory(new PropertyValueFactory<>("data"));
        cExibicao.setCellValueFactory(new PropertyValueFactory<>("exibicao"));
        CPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("MainUI");
    }

    public void add(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("VendaUI");
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        showVendaInMode(UIMode.UPDATE);
    }

    public void cancel(ActionEvent actionEvent) {
        Venda venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {
            CancelarVendaUseCase.cancel(venda.getId());
            loadDataAndShow();
        }
    }

    public void print(ActionEvent actionEvent) {
        Venda venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {

        }
    }

    public void filter(ActionEvent actionEvent) {
        LocalDate dataInicial = dateDataInicial.getValue();
        LocalDate dataFinal = dateDataFinal.getValue();

        List<Venda> vendas = CreateVendaReportUseCase.findAllByPeriod(dataInicial, dataFinal);
        tableData.clear();
        tableData.addAll(vendas);
    }

    private void showVendaInMode(UIMode uiMode) throws IOException {
        Venda venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {
            HelloApplication.setRoot("VendaUI");

            VendaUIController controller = (VendaUIController) HelloApplication.getController();
            controller.setVenda(venda, uiMode);
        }
    }
}
