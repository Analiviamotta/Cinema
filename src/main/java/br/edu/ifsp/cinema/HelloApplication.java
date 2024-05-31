package br.edu.ifsp.cinema;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.entities.sessao.SessaoDiaSemana;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Filme filme = new Filme("Titanic", FilmeGenero.DRAMA, "Sinopse1", "14");
        System.out.println("Informações do Filme:");
        System.out.println("Título: " + filme.getTitulo());
        System.out.println("Gênero: " + filme.getGenero());
        System.out.println("SInopse: " + filme.getSinopse());
        System.out.println("Classificação: " + filme.getClassificacaoIndicativa());
        System.out.println("Status: " + filme.getStatus());

        List<Assento> assentos = new ArrayList<>();

        Sala sala = new Sala(10, 10, 5, 300, assentos);

        Sessao sessao = new Sessao(SessaoDiaSemana.DOMINGO, LocalTime.of(18, 0), LocalTime.of(20, 0), filme);

        Exibicao exibicao = new Exibicao(sessao, sala, 100);

        Assento assento = new Assento(10, 2);
        Assento assento1 = new Assento(10, 3);

        BigDecimal valorIngresso = new BigDecimal("20.00");

        Ingresso ingresso = new Ingresso(assento, sessao, valorIngresso);
        Ingresso ingresso2 = new Ingresso(assento1, sessao, valorIngresso);
        Ingresso ingresso3 = new Ingresso(assento1, sessao, valorIngresso);

        Venda venda = new Venda(exibicao, "49", ingresso);
        venda.adicionarIngresso(ingresso2);
        venda.adicionarIngresso(ingresso3);

        System.out.println(venda.getPrecoTotal());

        System.out.println(exibicao.getQntIngressosDisponiveis());
    }
}