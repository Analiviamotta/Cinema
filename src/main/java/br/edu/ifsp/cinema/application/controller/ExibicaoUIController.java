package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.exibicao.CriarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.CriarFilmeUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.ConsultarDadosSalaUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.ConsultarSalasUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public class ExibicaoUIController {

    @FXML
    private TextField txtNumeroDaSala;

    @FXML
    private DatePicker dtDataEHorario;

    @FXML
    private TextField txtDuracao;

    @FXML
    private TableView<Filme> tableView;

    @FXML
    private TableColumn<Filme, String> cTituloDoFilme;

    @FXML
    private TableColumn<Filme, String> cClassificacaoIndicativa;

    @FXML
    private TextField txtQuantidadeIngressos;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    private Exibicao exibicao;
    private ConsultarSalasUseCase consultarSalasUseCase;

    public ExibicaoUIController(ConsultarSalasUseCase consultarSalasUseCase) {
        this.consultarSalasUseCase = consultarSalasUseCase;
    }

    private void getEntityToView() {
        if (exibicao == null) {
            exibicao = new Exibicao();
        }
        try {
            int numeroSala = Integer.parseInt(txtNumeroDaSala.getText());
            Sala sala = findSalaByNumber(numeroSala);
            if (sala != null) {
                exibicao.setSala(sala);
            } else {
                System.out.println("Sala não encontrada!");
            }

            LocalDate data = dtDataEHorario.getValue();
            LocalDateTime horarioData = data.atStartOfDay(); 

            exibicao.setHorarioData(horarioData);
            exibicao.setTempoDuracao(Duration.ofMinutes(Long.parseLong(txtDuracao.getText())));
            exibicao.setQntIngressosDisponiveis(Integer.parseInt(txtQuantidadeIngressos.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findNumberOfRoom(ActionEvent actionEvent) {
        try {
            int numeroSala = Integer.parseInt(txtNumeroDaSala.getText());
            Sala sala = findSalaByNumber(numeroSala);
            if (sala != null) {
                exibicao.setSala(sala);
            } else {
                System.out.println("Sala não encontrada!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Número da sala inválido!");
        }
    }

    private Sala findSalaByNumber(int numeroSala) {
        List<Sala> salas = consultarSalasUseCase.findAll();
        for (Sala sala : salas) {
            if (sala.getNumber() == numeroSala) {
                return sala;
            }
        }
        return null;
    }

    private void setEntityIntoView() {
        if (exibicao != null) {
            txtNumeroDaSala.setText(String.valueOf(exibicao.getSala().getNumber()));
            dtDataEHorario.setValue(exibicao.getHorarioData().toLocalDate());
            txtDuracao.setText(String.valueOf(exibicao.getTempoDuracao().toMinutes()));
            if (!tableView.getColumns().contains(cTituloDoFilme)) {
                tableView.getColumns().add(cTituloDoFilme);
            }
            if (!tableView.getColumns().contains(cClassificacaoIndicativa)) {
                tableView.getColumns().add(cClassificacaoIndicativa);
            }
            txtQuantidadeIngressos.setText(String.valueOf(exibicao.getQntIngressosDisponiveis()));
        }
    }

    public void setExibicao(Exibicao exibicao, UIMode uiMode) {
        this.exibicao = exibicao;
        setEntityIntoView();
        if(uiMode == UIMode.UPDATE){
            configViewMode();
        }
    }

    private void configViewMode() {
    }


    public void save(ActionEvent actionEvent) {
        getEntityToView();
        if(String.valueOf(exibicao.getId()) == null){
            CriarExibicaoUseCase.insert(exibicao);
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("ExibicaoManeger");
    }


}
