package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.entities.venda.VendaStatus;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;
import br.edu.ifsp.cinema.domain.usecases.venda.VendaDAO;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                    criarIngressosParaVenda(entity);
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
    public Optional<Venda> findOne(Long id) {
        String sql = "SELECT * FROM Sale WHERE id = ?";
        Venda venda = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                venda = mapResultSetToVenda(rs);
                venda.setId(rs.getLong("id"));
                return Optional.of(venda);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Venda> findAll() {
        String sql = "SELECT * FROM Sale WHERE id = ?";
        List<Venda> vendas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vendas.add(mapResultSetToVenda(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vendas;
    }

    @Override
    public boolean isAtivo(Long key) {
        return false;
    }

    @Override
    public List<Ingresso> listarIngressosDaVenda(Venda venda) {
        return findAllTicketBySale(venda.getId());
    }
    // infelizmente ficou faltando
    @Override
    public List<Venda> findAllByPeriod(LocalDate inicio, LocalDate fim) {
        return List.of();
    }

    private void criarIngressosParaVenda(Venda venda) throws SQLException {
        String insertSql = "UPDATE Ticket SET is_sold = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            for (int x = 1; x <= venda.getIngressoList().size(); x++) {
                insertStmt.setBoolean(1, venda.getIngressoList().get(x).isVendido());
                insertStmt.setLong(2, venda.getIngressoList().get(x).getId());
                insertStmt.executeUpdate();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private Venda mapResultSetToVenda(ResultSet rs) throws SQLException {
        ExibicaoDAO exibicaoDAO = new ExhibitionDaoSqlite();
        Long id = rs.getLong("id");
        LocalDate data = LocalDate.parse(rs.getString("sale_date"));
        VendaStatus status = VendaStatus.valueOf(rs.getString("status").toUpperCase());
        Long exibicao_id = rs.getLong("exhibition_id");

        List<Assento> assentos = findSeatBySale(id);
        Optional<Exibicao> exibicao = exibicaoDAO.findOne(exibicao_id);
        List<Ingresso> ingressos = findAllTicketBySale(id);

        if (exibicao.isPresent()){
            Venda venda = new Venda(ingressos,exibicao.get(),data,status);
            venda.setId(id);
            return venda;
        }
        return null;
    }

    private Ingresso mapResultSetToIngresso(ResultSet rs) throws SQLException {
        ExibicaoDAO exibicaoDAO = new ExhibitionDaoSqlite();

        Long id = rs.getLong("id");
        BigDecimal price = rs.getBigDecimal("price");
        Boolean is_sold = rs.getBoolean("is_sold");
        Long seat_id = rs.getLong("seat_id");
        Long sale_id = rs.getLong("sale_id");
        Long exhibition_id = rs.getLong("exhibition_id");

        Assento assento = findSeatByTicket(rs.getLong("sale_id"));
        Exibicao exibicao = exibicaoDAO.findByVendaId(rs.getLong("sale_id"));
        Ingresso ingresso = new Ingresso(assento, exibicao,price);
        ingresso.setId(id);

        return ingresso;
    }
    public Assento findSeatByTicket(Long id) {
        String sql = "SELECT * FROM Seat s INNER JOIN Ticket t  ON s.id = t.seat_id WHERE t.sale_id = ?";
        Assento assento = new Assento();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assento = mapResultSetToAssento(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return assento;
    }

    public List<Assento> findSeatBySale(Long id) {
        String sql = "SELECT * FROM Seat s INNER JOIN Ticket t  ON s.id = t.seat_id WHERE t.sale_id = ?";
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
        return assentos;
    }

    public List<Ingresso> findAllTicketBySale(Long id) {
        String sql = "SELECT * FROM Ticket WHERE sale_id = ?";
        List<Ingresso> ingressos = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ingressos.add(mapResultSetToIngresso(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ingressos;
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
