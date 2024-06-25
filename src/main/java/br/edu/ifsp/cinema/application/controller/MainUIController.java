package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainUIController {
    @FXML
    private void openFilme(ActionEvent event) {
        try {
            HelloApplication.setRoot("FilmeManeger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openVenda(ActionEvent event) {
        try {
            HelloApplication.setRoot("VendaManagerUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSala(ActionEvent event) {
        try {
            HelloApplication.setRoot("SalaManager");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openExibicao(ActionEvent event) {
        try {
            HelloApplication.setRoot("ExibicaoManeger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
