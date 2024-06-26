package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.sala.CriarSalaUseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SalaUIfxml {
    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtnumLinhas;

    @FXML
    private TextField txtnumColunas;

    @FXML
    private TextField txtCapacidade;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAssentos;

    private Sala sala;

    private CriarSalaUseCase criarSalaUseCase;

    public  SalaUIfxml( ) {
    }

    private void getEntityToView() {
        if (sala == null) {
            sala = new Sala();
        }
        try {
            sala.setNumber(Integer.parseInt(txtNumero.getText()));
            sala.setNumLinhas(Integer.parseInt(txtnumLinhas.getText()));
            sala.setNumColunas(Integer.parseInt(txtnumColunas.getText()));
            sala.setCapacidade(Integer.parseInt(txtCapacidade.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEntityIntoView() {
        if (sala != null) {
            txtNumero.setText(String.valueOf(sala.getNumber()));
            txtnumLinhas.setText(String.valueOf(sala.getNumLinhas()));
            txtnumColunas.setText(String.valueOf(sala.getNumColunas()));
            txtCapacidade.setText(String.valueOf(sala.getCapacidade()));
        }
    }

    public void setSala(Sala sala, UIMode uiMode) {
        this.sala = sala;
        setEntityIntoView();
        if(uiMode == UIMode.UPDATE){
            configViewMode();
        }
    }

    private void configViewMode() {
    }

    @FXML
    public void btnSave(ActionEvent actionEvent) {
        getEntityToView();
        if(String.valueOf(sala.getId()) != null){
            CriarSalaUseCase.insert(sala);
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("SalaManager");
    }

    @FXML
    public void btnOpenAssentosScene(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/cinema/application/view/AssentosUI.fxml"));
        Parent root = loader.load();

        AssentosUIController assentosController = loader.getController();
        assentosController.setSala(sala);

        Stage stage = (Stage) btnAssentos.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


}