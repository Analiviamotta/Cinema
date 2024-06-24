package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class TestMovieDaoSqlite {
    public static void main(String[] args) {
        try {
            // Configurar conexão com banco de dados em memória para testes
            Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            setConnectionForTests(connection);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE Movie (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT NOT NULL," +
                        "genre TEXT NOT NULL," +
                        "synopsis TEXT," +
                        "parental_rating TEXT," +
                        "status TEXT NOT NULL" +
                        ")");
            }

            MovieDaoSqlite movieDao = new MovieDaoSqlite();

            // Criar um novo filme para teste
            Filme filme = new Filme("Inception 3.0", FilmeGenero.DRAMA, "A mind-bending thriller sequel", "PG-13");
            filme.setStatus(FilmeStatus.ATIVO);
            movieDao.create(filme);
            System.out.println("Created: " + filme);

            // Encontrar o filme recém-criado pelo ID
            Optional<Filme> foundFilme = movieDao.findOne(filme.getId());
            System.out.println("Found by ID: " + foundFilme);

            // Deletar o filme pelo ID
            boolean deletedByKey = movieDao.deleteByKey(filme.getId());
            System.out.println("Deleted by key: " + deletedByKey);

            // Verificar se o filme foi deletado
            foundFilme = movieDao.findOne(filme.getId());
            System.out.println("Found after deletion: " + foundFilme);

            // Criar outro filme para teste
            Filme outroFilme = new Filme("Outro Filme", FilmeGenero.ACAO, "Descrição do outro filme", "PG");
            outroFilme.setStatus(FilmeStatus.ATIVO);
            movieDao.create(outroFilme);
            System.out.println("Created: " + outroFilme);

            // Deletar o filme diretamente
            boolean deleted = movieDao.delete(outroFilme);
            System.out.println("Deleted: " + deleted);

            // Verificar se o outro filme foi deletado
            foundFilme = movieDao.findOne(outroFilme.getId());
            System.out.println("Found after deletion: " + foundFilme);

            // Criar vários filmes para teste
            Filme filme1 = new Filme("Filme 1", FilmeGenero.AVENTURA, "Sinopse do Filme 1", "G");
            filme1.setStatus(FilmeStatus.ATIVO);
            Filme filme2 = new Filme("Filme 2", FilmeGenero.TERROR, "Sinopse do Filme 2", "R");
            filme2.setStatus(FilmeStatus.ATIVO);
            Filme filme3 = new Filme("Filme 3", FilmeGenero.ACAO, "Sinopse do Filme 3", "PG");
            filme3.setStatus(FilmeStatus.ATIVO);

            movieDao.create(filme1);
            movieDao.create(filme2);
            movieDao.create(filme3);

            // Encontrar todos os filmes
            List<Filme> allFilmes = movieDao.findAll();
            System.out.println("All films: ");
            allFilmes.forEach(System.out::println);

            // Testar isAtivo
            Filme filmeAtivo = new Filme("Filme Ativo2", FilmeGenero.DRAMA, "Sinopse do Filme Ativo", "PG-13");
            filmeAtivo.setStatus(FilmeStatus.ATIVO);
            movieDao.create(filmeAtivo);
            System.out.println("Created: " + filmeAtivo);

            boolean isAtivo = movieDao.isAtivo(filmeAtivo.getId());
            System.out.println("Is Ativo: " + isAtivo);

            // Atualizar filme
            filmeAtivo.setTitulo("Filme Ativo Atualizado");
            boolean updated = movieDao.update(filmeAtivo);
            System.out.println("Updated: " + updated);

            Optional<Filme> updatedFilme = movieDao.findOne(filmeAtivo.getId());
            System.out.println("Found after update: " + updatedFilme);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setConnectionForTests(Connection connection) {
        ConnectionFactory.connection = connection;
    }
}
