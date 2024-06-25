package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.assento.AssentoDAO;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoSqlite implements SalaDAO {


    @Override
    public Optional<Sala> findByNumber(int numero) {
        String sql = "SELECT * FROM Room WHERE number = ?";
        Sala sala = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sala = mapResultSetToSala(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(sala);

    }

    @Override
    public boolean isInExibicao(long salaId) {
        String sql = "SELECT COUNT(*) FROM Exhibition WHERE room_id = ? AND date_time > ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, salaId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Sala create(Sala entity) {
        String sql = "INSERT INTO Room(number, line_num, column_num, capacity, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getNumber());
            stmt.setInt(2, entity.getNumLinhas());
            stmt.setInt(3, entity.getNumColunas());
            stmt.setInt(4, entity.getCapacidade());
            stmt.setString(5, entity.getStatus().toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    AssentoDAO assentoDAO = new SeatDaoSqlite();
                    for (Assento assento : entity.getAssentoList()) {
                        assentoDAO.create(assento, entity.getId());
                    }
                } else {
                    throw new SQLException("Creating room failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Optional<Sala> findOne(Long id) {
        String sql = "SELECT * FROM Room WHERE id = ?";
        Sala sala = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sala = mapResultSetToSala(rs);
                sala.setId(rs.getLong("id"));
                return Optional.of(sala);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Sala> findAll() {
        String sql = "SELECT * FROM Room";
        List<Sala> salas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                salas.add(mapResultSetToSala(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return salas;
    }

    @Override
    public boolean update(Sala entity) {
        String sql = "UPDATE Room SET number = ?, line_num = ?, column_num = ?, capacity = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, entity.getNumber());
            stmt.setInt(2, entity.getNumLinhas());
            stmt.setInt(3, entity.getNumColunas());
            stmt.setInt(4, entity.getCapacidade());
            stmt.setString(5, entity.getStatus().name());
            stmt.setLong(6, entity.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        String sql = "DELETE FROM Room WHERE id = ?";
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
    public boolean delete(Sala entity) {
        return deleteByKey(entity.getId());
    }

    @Override
    public boolean isAtivo(Long id) {
        String sql = "SELECT status FROM Room WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return SalaStatus.valueOf(rs.getString("status")) == SalaStatus.ATIVO;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private Sala mapResultSetToSala(ResultSet rs) throws SQLException {
        AssentoDAO assentoDAO = new SeatDaoSqlite();

        Long id = rs.getLong("id");
        Integer numero = rs.getInt("number");
        Integer numLinhas = rs.getInt("line_num");
        Integer numColunas = rs.getInt("column_num");
        Integer capacidade = rs.getInt("capacity");
        SalaStatus status = SalaStatus.valueOf(rs.getString("status").toUpperCase());

        List<Assento> assentos = assentoDAO.findAllByRoom(id);

        Sala sala = new Sala(numero,numLinhas,numColunas,capacidade,assentos);
        sala.setId(id);
        sala.setStatus(status);

        return sala;
    }
}

