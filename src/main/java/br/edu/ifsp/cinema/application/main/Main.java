package br.edu.ifsp.cinema.application.main;

import br.edu.ifsp.cinema.application.main.repository.inmemory.InMemoryVendaDAO;
import br.edu.ifsp.cinema.application.main.repository.sqlite.dao.ExhibitionDaoSqlite;
import br.edu.ifsp.cinema.application.main.repository.sqlite.dao.MovieDaoSqlite;
import br.edu.ifsp.cinema.application.main.repository.sqlite.dao.RoomDaoSqlite;
import br.edu.ifsp.cinema.application.main.repository.sqlite.util.DatabaseBuilder;
import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.domain.usecases.exibicao.*;
import br.edu.ifsp.cinema.domain.usecases.filme.*;
import br.edu.ifsp.cinema.domain.usecases.relatorios.RelatorioVendasUseCase;
import br.edu.ifsp.cinema.domain.usecases.sala.*;
import br.edu.ifsp.cinema.domain.usecases.venda.*;

public class Main {
    public static AtivarFilmeUseCase ativarFilmeUseCase;
    public static ConsultarDadosFilmeUseCase consultarDadosFilmeUseCase;
    public static ConsultarFilmesUseCase consultarFilmesUseCase;
    public static CriarFilmeUseCase criarFilmeUseCase;
    public static EditarFilmeUseCase editarFilmeUseCase;
    public static ExcluirFilmeUseCase excluirFilmeUseCase;
    public static InativarFilmeUseCase inativarFilmeUseCase;

    public static AtivarExibicaoUseCase ativarExibicaoUseCase;
    public static CancelarExibicaoUseCase cancelarExibicaoUseCase;
    public static ConsultarExibicaoCancelada consultarExibicaoCancelada;
    public static ConsultarExibicaoUseCase consultarExibicaoUseCase;
    public static CriarExibicaoUseCase criarExibicaoUseCase;
    public static EditarExibicaoUseCase editarExibicaoUseCase;
    public static ExcluirExibicaoUseCase excluirExibicaoUseCase;

    public static AtivarSalaUseCase ativarSalaUseCase;
    public static ConsultarAssentoSala consultarAssentoSala;
    public static ConsultarDadosSalaUseCase consultarDadosSalaUseCase;
    public static ConsultarSalaInativaUseCase consultarSalaInativaUseCase;
    public static ConsultarSalasUseCase consultarSalasUseCase;
    public static CriarSalaUseCase criarSalaUseCase;
    public static EditarSalaUseCase editarSalaUseCase;
    public static ExcluirSalaUseCase excluirSalaUseCase;
    public static InativarSalaUseCase inativarSalaUseCase;

    public static RelatorioVendasUseCase relatorioVendasUseCase;

    public static CancelarVendaUseCase cancelarVendaUseCase;
    public static CreateVendaReportUseCase createVendaReportUseCase;
    public static CriarVendaUseCase criarVendaUseCase;




    public static void main(String[] args) {
        initializeUseCase();
        createDatabase();
        HelloApplication.main(args);  // mudar para a view root/home do projeto
    }

    private static void createDatabase(){
        DatabaseBuilder db = new DatabaseBuilder();
        db.buildDatabaseIfMissing();
    }
    //RODAR SEMPRE POR AQ !!!!!!!!!!!!!
    private static void initializeUseCase(){
        //iniciar agora aqui os use cases dps q eu fizer os dao do sqlite e a aninha fizer as telas
        FilmeDAO filmeDAO = new MovieDaoSqlite();
        SalaDAO salaDAO = new RoomDaoSqlite();
        ExibicaoDAO exibicaoDAO = new ExhibitionDaoSqlite();
        VendaDAO vendaDAO = new InMemoryVendaDAO();

        ativarFilmeUseCase = new AtivarFilmeUseCase(filmeDAO);
        consultarDadosFilmeUseCase = new ConsultarDadosFilmeUseCase(filmeDAO);
        consultarFilmesUseCase = new ConsultarFilmesUseCase(filmeDAO);
        criarFilmeUseCase = new CriarFilmeUseCase(filmeDAO);
        editarFilmeUseCase = new EditarFilmeUseCase(filmeDAO);
        excluirFilmeUseCase = new ExcluirFilmeUseCase(filmeDAO);
        inativarFilmeUseCase = new InativarFilmeUseCase(filmeDAO);

        ativarExibicaoUseCase = new AtivarExibicaoUseCase(exibicaoDAO);
        cancelarExibicaoUseCase = new CancelarExibicaoUseCase(exibicaoDAO);
        consultarExibicaoCancelada = new ConsultarExibicaoCancelada(exibicaoDAO);
        consultarExibicaoUseCase = new ConsultarExibicaoUseCase(exibicaoDAO);
        criarExibicaoUseCase = new CriarExibicaoUseCase(exibicaoDAO);
        editarExibicaoUseCase = new EditarExibicaoUseCase(exibicaoDAO);
        excluirExibicaoUseCase = new ExcluirExibicaoUseCase(exibicaoDAO);

        ativarSalaUseCase = new AtivarSalaUseCase(salaDAO);
        consultarAssentoSala = new ConsultarAssentoSala(salaDAO);
        consultarDadosSalaUseCase = new ConsultarDadosSalaUseCase(salaDAO);
        consultarSalaInativaUseCase = new ConsultarSalaInativaUseCase(salaDAO);
        consultarSalasUseCase = new ConsultarSalasUseCase(salaDAO);
        criarSalaUseCase = new CriarSalaUseCase(salaDAO);
        editarSalaUseCase = new EditarSalaUseCase(salaDAO);
        excluirSalaUseCase = new ExcluirSalaUseCase(salaDAO);
        inativarSalaUseCase = new InativarSalaUseCase(salaDAO);

        relatorioVendasUseCase = new RelatorioVendasUseCase(vendaDAO);

        cancelarVendaUseCase = new CancelarVendaUseCase(vendaDAO);
        createVendaReportUseCase = new CreateVendaReportUseCase(vendaDAO);
        criarVendaUseCase = new CriarVendaUseCase(vendaDAO, consultarExibicaoUseCase);



    }

}
