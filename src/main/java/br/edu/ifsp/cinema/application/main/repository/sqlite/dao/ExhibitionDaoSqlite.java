package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.assento.AssentoDAO;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;

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
            stmt.setString(1,entity.getHorarioData().format(formatter));
            stmt.setLong(2, entity.getTempoDuracao().toMinutes());
            stmt.setInt(3, entity.getQntIngressosDisponiveis());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getFilme().getId());
            stmt.setLong(6,entity.getSala().getId());
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
                FROM Exhibition e inner join Movie m on e.movie_id = m.id
                inner join Room r on e.room_id = r.id
                WHERE e.id = ?
                """;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                AssentoDAO assentoDAO = new SeatDaoSqlite();
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
                        assentoDAO.findAllByRoom(rs.getLong("room_id"))
                );

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time")),
                        Duration.ofSeconds(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

                return Optional.of(exibicao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Exibicao> findAll() {
        String sql = """
                SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                        m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                        r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
                FROM Exhibition e inner join Movie m on e.movie_id = m.id
                inner join Room r on e.room_id = r.id
                """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AssentoDAO assentoDAO = new SeatDaoSqlite();
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
                        assentoDAO.findAllByRoom(rs.getLong("room_id"))
                );

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time")),
                        Duration.ofSeconds(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

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
            stmt.setString(1,entity.getHorarioData().format(formatter));
            stmt.setLong(2, entity.getTempoDuracao().toMinutes());
            stmt.setInt(3, entity.getQntIngressosDisponiveis());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getFilme().getId());
            stmt.setLong(6,entity.getSala().getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //NAO TA PODENDO, NAO TA PERMITIDO DELETAR EXIBIÇÃO OKAY, FAVOR TIRAR O BOTAO DA TELA PQ SENAO VAI QUEBRAR TUDO
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

    //nao tem ativo mas tbm nao da pra tirar pq ta no dao entao estourei uma exception
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
                FROM Exhibition e inner join Movie m on e.movie_id = m.id
                inner join Room r on e.room_id = r.id
                WHERE e.movie_id = ?
                """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AssentoDAO assentoDAO = new SeatDaoSqlite();
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
                        assentoDAO.findAllByRoom(rs.getLong("room_id"))
                );

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time")),
                        Duration.ofSeconds(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

                exibicoes.add(exibicao);
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
                FROM Exhibition e inner join Movie m on e.movie_id = m.id
                inner join Room r on e.room_id = r.id
                WHERE e.room_id = ?
                """;
        List<Exibicao> exibicoes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AssentoDAO assentoDAO = new SeatDaoSqlite();
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
                        assentoDAO.findAllByRoom(rs.getLong("room_id"))
                );

                Exibicao exibicao = new Exibicao(
                        sala,
                        filme,
                        LocalDateTime.parse(rs.getString("date_time")),
                        Duration.ofSeconds(rs.getInt("duration")),
                        rs.getInt("tickets_number")
                );
                exibicao.setId(rs.getLong("id"));
                exibicao.setStatus(ExibicaoStatus.valueOf(rs.getString("status")));

                exibicoes.add(exibicao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exibicoes;
    }

    @Override
    public boolean exibicaoExistenteNaMesmaDataHorarioSala(Exibicao exibicao) {
        String sql = """
                SELECT e.id, e.date_time, e.duration, e.tickets_number, e.status, e.movie_id, e.room_id,
                        m.title, m.genre, m.synopsis, m.parental_rating, m.status as movie_status,
                        r.number, r.line_num, r.column_num, r.capacity, r.status as room_status
                FROM Exhibition e inner join Movie m on e.movie_id = m.id
                inner join Room r on e.room_id = r.id
                WHERE e.date_time = ? and e.room_id = ?
                """;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, exibicao.getHorarioData().format(formatter));
            stmt.setLong(2, exibicao.getSala().getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



}
