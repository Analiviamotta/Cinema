package br.edu.ifsp.cinema.application.controller;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.exibicao.CriarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.exibicao.EditarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.ConsultarDadosFilmeUseCase;
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

import static br.edu.ifsp.cinema.application.main.Main.consultarDadosFilmeUseCase;


public class ExibicaoUIController {

    @FXML
    private TextField txtNumeroDaSala;

    @FXML
    private DatePicker dtDataEHorario;

    @FXML
    private TextField txtDuracao;

//    @FXML
//    private TableView<Filme> tableView;
//
//    @FXML
//    private TableColumn<Filme, String> cTituloDoFilme;
//
//    @FXML
//    private TableColumn<Filme, String> cClassificacaoIndicativa;

    @FXML
    private TextField txtQuantidadeIngressos;

    @FXML
    private TextField txtFilme;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    private Exibicao exibicao;

    static ConsultarDadosFilmeUseCase ConsultarDadosFilmeUseCase;


    public ExibicaoUIController() {

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
                return;
            }

            LocalDate data = dtDataEHorario.getValue();
            LocalDateTime horarioData = data.atStartOfDay();
            exibicao.setHorarioData(horarioData);

            exibicao.setTempoDuracao(Duration.ofMinutes(Long.parseLong(txtDuracao.getText())));
            exibicao.setQntIngressosDisponiveis(Integer.parseInt(txtQuantidadeIngressos.getText()));

            Filme filme = findFilmeByTitulo(txtFilme.getText());
            if (filme != null) {
                exibicao.setFilme(filme);
            } else {
                System.out.println("Filme não encontrado!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Filme findFilmeByTitulo(String titulo) {
        Optional<Filme> filmeOpt = consultarDadosFilmeUseCase.findByTitulo(titulo);
        if (filmeOpt.isPresent()) {
            return filmeOpt.get();
        } else {
            System.out.println("Nenhum filme com esse título foi encontrado");
            return null;
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
        List<Sala> salas = ConsultarSalasUseCase.findAll();
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
            txtFilme.setText(exibicao.getFilme().getTitulo());
//            if (!tableView.getColumns().contains(cTituloDoFilme)) {
//                tableView.getColumns().add(cTituloDoFilme);
//            }
//            if (!tableView.getColumns().contains(cClassificacaoIndicativa)) {
//                tableView.getColumns().add(cClassificacaoIndicativa);
//            }
            txtQuantidadeIngressos.setText(String.valueOf(exibicao.getQntIngressosDisponiveis()));
        }
    }

    public void setExibicao(Exibicao exibicao, UIMode uiMode) throws IOException {
        this.exibicao = exibicao;
        setEntityIntoView();
        if(uiMode == UIMode.UPDATE){
            configUpdateMode();
        }
    }

    private void configUpdateMode() {
        //dtDataEHorario.setDisable(true);
    }


    @FXML
    public void save(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        if(exibicao.getId() == null){
            CriarExibicaoUseCase.insert(exibicao);
            HelloApplication.setRoot("ExibicaoManeger");
        }
        else{
            EditarExibicaoUseCase.update(exibicao);
            HelloApplication.setRoot("ExibicaoManeger");
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("ExibicaoManeger");
    }


}