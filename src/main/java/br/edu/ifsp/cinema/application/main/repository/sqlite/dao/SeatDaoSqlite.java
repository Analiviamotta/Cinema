package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.usecases.assento.AssentoDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDaoSqlite implements AssentoDAO {
    @Override
    public List<Assento> findAllByRoom(Long id) {
        String sql = "SELECT * FROM Seat WHERE room_id = ?";
        List<Assento> assentos = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assentos.add(mapResultSetToAssento(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return assentos;    }

    @Override
    public Assento create(Assento entity, Long room_id) {
        String sql = "INSERT INTO Seat(column, line, room_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getColuna());
            stmt.setInt(2, entity.getLinha());
            stmt.setLong(3,room_id);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Optional<Assento> findOne(Long key) {
        return Optional.empty();
    }

    @Override
    public List<Assento> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Assento entity) {
        return false;
    }

    @Override
    public boolean deleteByKey(Long key) {
        return false;
    }

    @Override
    public boolean delete(Assento entity) {
        return false;
    }

    @Override
    public boolean isAtivo(Long key) {
        return false;
    }

    private Assento mapResultSetToAssento(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Integer coluna = rs.getInt("column");
        Integer linha = rs.getInt("line");

        Assento assento = new Assento(coluna,linha);
        assento.setId(id);

        return assento;
    }

}
