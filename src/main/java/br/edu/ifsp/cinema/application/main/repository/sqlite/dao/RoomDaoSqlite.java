package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
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
                } else {
                    throw new SQLException("Creating room failed, no ID obtained.");
                }
            }

            criarAssentosParaSala(entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    private void criarAssentosParaSala(Sala sala) throws SQLException {
        String insertSql = "INSERT INTO Seat (line, column, room_id) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            for (int linha = 1; linha <= sala.getNumLinhas(); linha++) {
                for (int coluna = 1; coluna <= sala.getNumColunas(); coluna++) {
                    insertStmt.setInt(1, linha);
                    insertStmt.setInt(2, coluna);
                    insertStmt.setLong(3, sala.getId());
                    insertStmt.executeUpdate();

                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            Assento assento = new Assento(linha, coluna, generatedId);
                            sala.getAssentoList().add(assento);
                        } else {
                            throw new SQLException("Creating seat failed, no ID obtained.");
                        }
                    }
                }
            }
        }
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
        String deleteRoomSql = "DELETE FROM Room WHERE id = ?";
        String deleteSeatsSql = "DELETE FROM Seat WHERE room_id = ?";
        try (PreparedStatement deleteRoomStmt = ConnectionFactory.createPreparedStatement(deleteRoomSql);
             PreparedStatement deleteSeatsStmt = ConnectionFactory.createPreparedStatement(deleteSeatsSql)) {
            deleteSeatsStmt.setLong(1, id);
            deleteSeatsStmt.executeUpdate();

            deleteRoomStmt.setLong(1, id);
            int rowsAffected = deleteRoomStmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                String status = rs.getString("status").toUpperCase();
                return SalaStatus.valueOf(status) == SalaStatus.ATIVO;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se a sala está ativa: " + e.getMessage());
        }
        return false;
    }


    public void deleteAllSalas() {
        String deleteSeatsSql = "DELETE FROM Seat";
        String deleteRoomsSql = "DELETE FROM Room";
        try (PreparedStatement deleteSeatsStmt = ConnectionFactory.createPreparedStatement(deleteSeatsSql);
             PreparedStatement deleteRoomsStmt = ConnectionFactory.createPreparedStatement(deleteRoomsSql)) {

            // Primeiro excluímos todos os assentos
            deleteSeatsStmt.executeUpdate();

            // Em seguida, excluímos todas as salas
            deleteRoomsStmt.executeUpdate();

            System.out.println("Todas as salas e assentos foram excluídos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Sala mapResultSetToSala(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Integer numero = rs.getInt("number");
        Integer numLinhas = rs.getInt("line_num");
        Integer numColunas = rs.getInt("column_num");
        Integer capacidade = rs.getInt("capacity");
        SalaStatus status = SalaStatus.valueOf(rs.getString("status").toUpperCase());

        List<Assento> assentos = new ArrayList<>();

        Sala sala = new Sala(numero, numLinhas, numColunas, capacidade);
        sala.setId(id);
        sala.setStatus(status);
        sala.setAssentoList(assentos);

        return sala;
    }


}