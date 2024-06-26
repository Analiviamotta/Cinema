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
                stmt.execute("CREATE TABLE Exhibition (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "date_time DATETIME," +
                        "duration INTEGER," +
                        "tickets_number INT," +
                        "status BOOLEAN," +
                        "movie_id INT," +
                        "room_id INT," +
                        "FOREIGN KEY (movie_id) REFERENCES Movie(id)," +
                        "FOREIGN KEY (room_id) REFERENCES Room(id)" +
                        ")");
            }

            MovieDaoSqlite movieDao = new MovieDaoSqlite();

            Filme filme = new Filme("Olaaa", FilmeGenero.DRAMA, "Sino2", "12");
            filme.setStatus(FilmeStatus.ATIVO);
            movieDao.create(filme);
            System.out.println("Created: " + filme);


            List<Filme> allFilmes = movieDao.findAll();
            System.out.println("All films: ");
            allFilmes.forEach(System.out::println);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setConnectionForTests(Connection connection) {
        ConnectionFactory.connection = connection;
    }
}
