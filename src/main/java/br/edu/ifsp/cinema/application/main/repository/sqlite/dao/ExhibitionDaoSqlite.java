package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExhibitionDaoSqlite implements ExibicaoDAO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Exibicao create(Exibicao entity) {
        String sql = "INSERT INTO Exhibition (date_time, duration, tickets_number, status, movie_id, room_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getHorarioData().format(formatter));
            stmt.setLong(2, entity.getTempoDuracao().toMinutes());
            stmt.setInt(3, entity.getQntIngressosDisponiveis());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getFilme().getId());
            stmt.setLong(6, entity.getSala().getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating exhibition failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating exhibition failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Optional<Exibicao> findOne(Long id) {
        String sql = """
        SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
        FROM Exhibition e 
        INNER JOIN Movie m ON e.movie_id = m.id
        INNER JOIN Room r ON e.room_id = r.id
        WHERE e.id = ?
        """;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Filme filme = new Filme(
                        rs.getString("title"),
                        FilmeGenero.valueOf(rs.getString("genre")),
                        rs.getString("synopsis"),
                        rs.getString("parental_rating")
                );
                filme.setId(rs.getLong("movie_id"));
                filme.setStatus(FilmeStatus.valueOf(rs.getString("movie_status").toUpperCase()));

                // Carregar assentos da sala
                List<Assento> assentos = new ArrayList<>();
                try (PreparedStatement seatStmt = ConnectionFactory.createPreparedStatement("SELECT * FROM Seat WHERE room_id = ?")) {
                    seatStmt.setLong(1, rs.getLong("room_id"));
                    ResultSet seatRs = seatStmt.executeQuery();
                    while (seatRs.next()) {
                        Assento assento = new Assento(
                                seatRs.getInt("column"),
                                seatRs.getInt("line"),
                                seatRs.getLong("id") // Set ID based on database value
                        );
                        assentos.add(assento);
                    }
                }

                Sala sala = new Sala(
                        rs.getInt("number"),
                        rs.getInt("line_num"),
                        rs.getInt("column_num"),
                        rs.getInt("capacity"),
                        assentos // Set assentos list
                );
                sala.setId(rs.getLong("room_id"));
                sala.setStatus(SalaStatus.valueOf(rs.getString("room_status").toUpperCase()));

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time"), formatter),
                        Duration.ofMinutes(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.fromString(rs.getString("status")));

                return Optional.of(exibicao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar exibição: " + e.getMessage());
        }
        return Optional.empty();
    }



    @Override
    public List<Exibicao> findAll() {
        String sql = """
            SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                    m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                    r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
            FROM Exhibition e
            INNER JOIN Movie m ON e.movie_id = m.id
            INNER JOIN Room r ON e.room_id = r.id
            """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            SalaDAO salaDAO = new RoomDaoSqlite();
            while (rs.next()) {
                Filme filme = new Filme(
                        rs.getString("title"),
                        FilmeGenero.valueOf(rs.getString("genre")),
                        rs.getString("synopsis"),
                        rs.getString("parental_rating")
                );
                filme.setId(rs.getLong("movie_id"));
                filme.setStatus(FilmeStatus.valueOf(rs.getString("movie_status").toUpperCase()));

                Sala sala = new Sala(
                        rs.getInt("number"),
                        rs.getInt("line_num"),
                        rs.getInt("column_num"),
                        rs.getInt("capacity"),
                        salaDAO.findAllSeatByRoom(rs.getLong("room_id"))
                );
                sala.setId(rs.getLong("room_id"));
                sala.setStatus(SalaStatus.valueOf(rs.getString("room_status").toUpperCase()));

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time"), formatter),
                        Duration.ofMinutes(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.fromString(rs.getString("status")));

                exibicoes.add(exibicao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exibicoes;
    }


    @Override
    public boolean update(Exibicao entity) {
        String sql = "UPDATE Exhibition SET date_time = ?, duration = ?, tickets_number = ?, status = ?, movie_id = ?, room_id = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, entity.getHorarioData().format(formatter));
            stmt.setLong(2, entity.getTempoDuracao().toMinutes());
            stmt.setInt(3, entity.getQntIngressosDisponiveis());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getFilme().getId());
            stmt.setLong(6, entity.getSala().getId());
            stmt.setLong(7, entity.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        String sql = "DELETE FROM Exhibition WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Exibicao entity) {
        return deleteByKey(entity.getId());
    }

    @Override
    public boolean isAtivo(Long id) {
        throw new IllegalStateException("Não existe estado 'Ativo' em Exibição");
    }

    @Override
    public List<Exibicao> findByFilmeId(long id) {
        String sql = """
            SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                    m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                    r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
            FROM Exhibition e 
            INNER JOIN Movie m ON e.movie_id = m.id
            INNER JOIN Room r ON e.room_id = r.id
            WHERE e.movie_id = ?
            """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Filme filme = new Filme(
                            rs.getString("title"),
                            FilmeGenero.valueOf(rs.getString("genre")),
                            rs.getString("synopsis"),
                            rs.getString("parental_rating")
                    );
                    filme.setId(rs.getLong("movie_id"));
                    filme.setStatus(FilmeStatus.valueOf(rs.getString("movie_status")));

                    Sala sala = new Sala(
                            rs.getInt("number"),
                            rs.getInt("line_num"),
                            rs.getInt("column_num"),
                            rs.getInt("capacity"),
                            new ArrayList<>() // Não é necessário carregar os assentos aqui
                    );
                    sala.setId(rs.getLong("room_id"));
                    sala.setStatus(SalaStatus.valueOf(rs.getString("room_status")));

                    Exibicao exibicao = new Exibicao(
                            sala,
                            filme,
                            LocalDateTime.parse(rs.getString("date_time"), formatter),
                            Duration.ofMinutes(rs.getInt("duration")),
                            rs.getInt("tickets_number")
                    );
                    exibicao.setId(rs.getLong("id"));
                    exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

                    exibicoes.add(exibicao);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exibicoes;
    }

    @Override
    public List<Exibicao> findBySalaId(long id) {
        String sql = """
            SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                    m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                    r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
            FROM Exhibition e 
            INNER JOIN Movie m ON e.movie_id = m.id
            INNER JOIN Room r ON e.room_id = r.id
            WHERE e.room_id = ?
            """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Filme filme = new Filme(
                            rs.getString("title"),
                            FilmeGenero.valueOf(rs.getString("genre")),
                            rs.getString("synopsis"),
                            rs.getString("parental_rating")
                    );
                    filme.setId(rs.getLong("movie_id"));
                    filme.setStatus(FilmeStatus.valueOf(rs.getString("movie_status")));

                    Sala sala = new Sala(
                            rs.getInt("number"),
                            rs.getInt("line_num"),
                            rs.getInt("column_num"),
                            rs.getInt("capacity"),
                            new ArrayList<>()
                    );
                    sala.setId(rs.getLong("room_id"));
                    sala.setStatus(SalaStatus.valueOf(rs.getString("room_status")));

                    Exibicao exibicao = new Exibicao(
                            sala,
                            filme,
                            LocalDateTime.parse(rs.getString("date_time"), formatter),
                            Duration.ofMinutes(rs.getInt("duration")),
                            rs.getInt("tickets_number")
                    );
                    exibicao.setId(rs.getLong("id"));
                    exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

                    exibicoes.add(exibicao);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exibicoes;
    }

    @Override
    public boolean exibicaoExistenteNaMesmaDataHorarioSala(Exibicao exibicao) {
        String sql = """
            SELECT e.id
            FROM Exhibition e 
            WHERE e.room_id = ? AND (
                (e.date_time <= ? AND e.date_time + e.duration >= ?) OR
                (e.date_time >= ? AND e.date_time <= ?)
            )
            """;
        LocalDateTime startDateTime = exibicao.getHorarioData();
        LocalDateTime endDateTime = startDateTime.plus(exibicao.getTempoDuracao());
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, exibicao.getSala().getId());
            stmt.setString(2, startDateTime.format(formatter));
            stmt.setString(3, endDateTime.format(formatter));
            stmt.setString(4, startDateTime.format(formatter));
            stmt.setString(5, endDateTime.format(formatter));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
