package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TestExhibitionDaoSqlite {
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
                stmt.execute("CREATE TABLE Room (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number INTEGER NOT NULL," +
                        "line_num INTEGER NOT NULL," +
                        "column_num INTEGER NOT NULL," +
                        "capacity INTEGER NOT NULL," +
                        "status TEXT NOT NULL" +
                        ")");
                stmt.execute("CREATE TABLE Exhibition (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "date_time DATETIME," +
                        "duration INTEGER," +
                        "tickets_number INT," +
                        "status TEXT," +
                        "movie_id INT," +
                        "room_id INT," +
                        "FOREIGN KEY (movie_id) REFERENCES Movie(id)," +
                        "FOREIGN KEY (room_id) REFERENCES Room(id)" +
                        ")");
                stmt.execute("CREATE TABLE Seat (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "column INT," +
                        "line INT," +
                        "room_id INT," +
                        "FOREIGN KEY (room_id) REFERENCES Room(id)" +
                        ")");
            }

            ExhibitionDaoSqlite exhibitionDao = new ExhibitionDaoSqlite();
            MovieDaoSqlite movieDao = new MovieDaoSqlite();
            RoomDaoSqlite roomDao = new RoomDaoSqlite();

            // Criar um filme e uma sala para teste
            Filme filme = new Filme("Inception", FilmeGenero.DRAMA, "A mind-bending thriller", "PG-13");
            filme.setStatus(FilmeStatus.ATIVO);
            movieDao.create(filme);
            System.out.println("Created Movie: " + filme);

            Sala sala = new Sala(1, 1, 1, 10, null);
            sala.setStatus(SalaStatus.ATIVO);
            roomDao.create(sala);
            System.out.println("Created Room: " + sala);

            // Criar uma nova exibição para teste
            Exibicao exibicao = new Exibicao(sala, filme, LocalDateTime.now(), Duration.ofMinutes(120), 100);
            exibicao.setStatus(ExibicaoStatus.EFETUADA);
            exhibitionDao.create(exibicao);
            System.out.println("Created Exhibition: " + exibicao);

            System.out.println("ID of the created Exhibition: " + exibicao.getId());

            // Encontrar a exibição recém-criada pelo ID
            Optional<Exibicao> foundExibicao = exhibitionDao.findOne(exibicao.getId());
            System.out.println("Found by ID: " + foundExibicao);

//            // Deletar a exibição pelo ID
//            boolean deletedByKey = exhibitionDao.deleteByKey(exibicao.getId());
//            System.out.println("Deleted by key: " + deletedByKey);
//
//            // Verificar se a exibição foi deletada
//            foundExibicao = exhibitionDao.findOne(exibicao.getId());
//            System.out.println("Found after deletion: " + foundExibicao);
//
//            // Criar outra exibição para teste
//            Exibicao outraExibicao = new Exibicao(sala, filme, LocalDateTime.now().plusDays(1), Duration.ofMinutes(150), 150);
//            outraExibicao.setStatus(ExibicaoStatus.EFETUADA);
//            exhibitionDao.create(outraExibicao);
//            System.out.println("Created: " + outraExibicao);
//
//            // Deletar a exibição diretamente
//            boolean deleted = exhibitionDao.delete(outraExibicao);
//            System.out.println("Deleted: " + deleted);
//
//            // Verificar se a outra exibição foi deletada
//            foundExibicao = exhibitionDao.findOne(outraExibicao.getId());
//            System.out.println("Found after deletion: " + foundExibicao);
//
//            // Criar várias exibições para teste
//            Exibicao exibicao1 = new Exibicao(sala, filme, LocalDateTime.now().plusDays(2), Duration.ofMinutes(110), 110);
//            exibicao1.setStatus(ExibicaoStatus.EFETUADA);
//            Exibicao exibicao2 = new Exibicao(sala, filme, LocalDateTime.now().plusDays(3), Duration.ofMinutes(130), 130);
//            exibicao2.setStatus(ExibicaoStatus.EFETUADA);
//            Exibicao exibicao3 = new Exibicao(sala, filme, LocalDateTime.now().plusDays(4), Duration.ofMinutes(140), 140);
//            exibicao3.setStatus(ExibicaoStatus.EFETUADA);
//
//            exhibitionDao.create(exibicao1);
//            exhibitionDao.create(exibicao2);
//            exhibitionDao.create(exibicao3);
//
//            // Encontrar todas as exibições
//            List<Exibicao> allExibicoes = exhibitionDao.findAll();
//            System.out.println("All exhibitions: ");
//            allExibicoes.forEach(System.out::println);
//
//            // Testar exibicaoExistenteNaMesmaDataHorarioSala
//            Exibicao exibicaoTeste = new Exibicao(sala, filme, exibicao1.getHorarioData(), Duration.ofMinutes(120), 100);
//            exibicaoTeste.setStatus(ExibicaoStatus.EFETUADA);
//            boolean exists = exhibitionDao.exibicaoExistenteNaMesmaDataHorarioSala(exibicaoTeste);
//            System.out.println("Exhibition exists at same date, time and room: " + exists);
//
//            // Atualizar exibição
//            exibicao1.setQntIngressosDisponiveis(90);
//            boolean updated = exhibitionDao.update(exibicao1);
//            System.out.println("Updated: " + updated);
//
//            Optional<Exibicao> updatedExibicao = exhibitionDao.findOne(exibicao1.getId());
//            System.out.println("Found after update: " + updatedExibicao);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setConnectionForTests(Connection connection) {
        ConnectionFactory.connection = connection;
    }
}
