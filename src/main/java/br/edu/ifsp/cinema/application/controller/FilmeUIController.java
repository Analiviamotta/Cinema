package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.usecases.filme.CriarFilmeUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.EditarFilmeUseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;

public class FilmeUIController {
    @FXML
    private TextField txtTitulo;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private TextArea txtSinopse;

    @FXML
    private TextField txtClassificacaoIndicativa;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    private Filme filme;

    @FXML public void initialize(){cbGenero.getItems().setAll(Arrays.toString(FilmeGenero.values()));
    }

    private void getEntityToView(){
        if(filme == null){
            filme = new Filme();
        }
        filme.setTitulo(txtTitulo.getText());
        filme.setSinopse(txtSinopse.getText());
        filme.setClassificacaoIndicativa(txtClassificacaoIndicativa.getText());
        filme.setGenero(FilmeGenero.valueOf(cbGenero.getValue()));
    }

    private void setEntityIntoView(){
        txtTitulo.setText(filme.getTitulo());
        txtSinopse.setText(filme.getSinopse());
        txtClassificacaoIndicativa.setText(filme.getClassificacaoIndicativa());
        cbGenero.setValue(filme.getGenero().toString());
    }
    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        if(String.valueOf(filme.getId()) == null){
            CriarFilmeUseCase.insert(filme);
        }
        else{
            EditarFilmeUseCase.update(filme);
        }
        HelloApplication.setRoot("FilmeManeger");
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("FilmeManeger");
    }

    public void setFilme(Filme filme, UIMode uiMode) {
        this.filme = filme;
        setEntityIntoView();
        if(uiMode == UIMode.UPDATE){
            configUpdateMode();
        }
    }

    private void configUpdateMode() {
        txtTitulo.setDisable(true);
    }

}