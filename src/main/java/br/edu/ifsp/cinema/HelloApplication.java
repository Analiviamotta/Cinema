package br.edu.ifsp.cinema;

import br.edu.ifsp.cinema.application.main.repository.*;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ConsultarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.exibicao.CriarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;
import br.edu.ifsp.cinema.domain.usecases.filme.CriarFilmeUseCase;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;
import br.edu.ifsp.cinema.domain.usecases.relatorios.RelatorioVendasUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.CriarSalaUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.export.PDFExporter;
import br.edu.ifsp.cinema.domain.usecases.utils.export.PDFExporterIngressos;
import br.edu.ifsp.cinema.domain.usecases.venda.CreateVendaReportUseCase;
import br.edu.ifsp.cinema.domain.usecases.venda.CriarVendaUseCase;
import br.edu.ifsp.cinema.domain.usecases.venda.VendaDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        //launch();

        Filme filme = new Filme("Titanic", FilmeGenero.DRAMA, "Sinopse1", "14");
        System.out.println("Informações do Filme:");
        System.out.println("Título: " + filme.getTitulo());
        System.out.println("Gênero: " + filme.getGenero());
        System.out.println("SInopse: " + filme.getSinopse());
        System.out.println("Classificação: " + filme.getClassificacaoIndicativa());
        System.out.println("Status: " + filme.getStatus());

        List<Assento> assentos = new ArrayList<>();

        Sala sala = new Sala(10, 10, 5, 300, assentos);

        Duration duracao = Duration.ofMinutes(120);
        System.out.println(duracao);

        Exibicao exibicao1 = new Exibicao(sala, filme, LocalDateTime.of(2024, 7, 3, 14, 30),duracao, 100);

        Assento assento = new Assento(10, 2);
        Assento assento1 = new Assento(10, 3);
        Assento assento2 = new Assento(10, 4);
        Assento assento3 = new Assento(10, 5);
        Assento assento4 = new Assento(10, 6);
        Assento assento5 = new Assento(10, 7);


        BigDecimal valorIngresso = new BigDecimal("20.00");

        Ingresso ingresso = new Ingresso(assento, exibicao1, valorIngresso);
        Ingresso ingresso2 = new Ingresso(assento1, exibicao1, valorIngresso);
        Ingresso ingresso3 = new Ingresso(assento2, exibicao1, valorIngresso);
        Ingresso ingresso4 = new Ingresso(assento3, exibicao1, valorIngresso);
        Ingresso ingresso5= new Ingresso(assento4, exibicao1, valorIngresso);
        Ingresso ingresso6 = new Ingresso(assento5, exibicao1, valorIngresso);

        System.out.println(exibicao1.getQntIngressosDisponiveis());

        List<Ingresso> ingressos = new ArrayList<>();
        ingressos.add(ingresso);
        ingressos.add(ingresso2);

        SalaDAO salaDAO = new InMemorySalaDAO();
        ExibicaoDAO exibicaoDAO = new InMemoryExibicaoDAO();
        VendaDAO vendaDAO = new InMemoryVendaDAO();
        FilmeDAO filmeDAO = new InMeMemoryFilmeDAO();

        CriarFilmeUseCase criarFilmeUseCase = new CriarFilmeUseCase(filmeDAO);
        Filme filme1 = criarFilmeUseCase.insert(filme);

        CriarSalaUseCase criarSalaUseCase = new CriarSalaUseCase(salaDAO);
        Sala sala1 = criarSalaUseCase.insert(sala);

        CriarExibicaoUseCase criarExibicaoUseCase = new CriarExibicaoUseCase(exibicaoDAO);
        criarExibicaoUseCase.insert(exibicao1);

        ConsultarExibicaoUseCase consultarExibicaoUseCase = new ConsultarExibicaoUseCase(exibicaoDAO);

        CriarVendaUseCase criarVendaUseCase = new CriarVendaUseCase(vendaDAO, consultarExibicaoUseCase);
        criarVendaUseCase.criarVenda(exibicao1.getId(), List.of(ingresso2, ingresso3));
        criarVendaUseCase.criarVenda(exibicao1.getId(), List.of(ingresso));

        PDFExporter exportadorpdf1 = new PDFExporter("relatorio_vendas.pdf");

        RelatorioVendasUseCase relatorio1 = new RelatorioVendasUseCase(vendaDAO);

        List<Venda> vendasRelatorio = relatorio1.findAllByPeriod(LocalDate.of(2024, Month.JANUARY, 9), LocalDate.of(2024,Month.AUGUST, 10 ));
        exportadorpdf1.generatesExportableReport(vendasRelatorio);

        PDFExporterIngressos exportadorIngressos1 = new PDFExporterIngressos("ingressos1.pdf");
        exportadorIngressos1.generatesExportableReport(ingressos);

        System.out.println(exibicao1.getQntIngressosDisponiveis());


    }
}