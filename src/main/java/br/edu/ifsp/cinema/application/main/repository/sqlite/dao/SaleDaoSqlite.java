package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.venda.VendaDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class SaleDaoSqlite implements VendaDAO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public Venda create(Venda entity) {
        String sql = "INSERT INTO Sale (sale_date, status, exhibition_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getData().format(formatter));
            stmt.setString(2, entity.getStatus().toString());
            stmt.setLong(3, entity.getExibicao().getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating sale failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean update(Venda entity) {
        String sql = "UPDATE Sale SET sale_date = ?, status = ?, exhibition_id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, entity.getData().format(formatter));
            stmt.setString(2, entity.getStatus().toString());
            stmt.setLong(3, entity.getExibicao().getId());
            stmt.setLong(4, entity.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Venda entity) {
        return deleteByKey(entity.getId());
    }

    @Override
    public boolean deleteByKey(Long id) {
        String sql = "DELETE FROM Sale WHERE id = ?";
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
    public Optional<Venda> findOne(Long key) {
        return Optional.empty();
    }

    @Override
    public List<Venda> findAll() {
        return List.of();
    }

    @Override
    public boolean isAtivo(Long key) {
        return false;
    }

    @Override
    public List<Ingresso> listarIngressosDaVenda(Venda venda) {
        return List.of();
    }

    @Override
    public List<Venda> findAllByPeriod(LocalDate inicio, LocalDate fim) {
        return List.of();
    }


}
