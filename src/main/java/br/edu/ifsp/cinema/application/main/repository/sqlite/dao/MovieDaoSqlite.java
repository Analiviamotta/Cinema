package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MovieDaoSqlite implements FilmeDAO {
    @Override
    public Optional<Filme> findByTitulo(String Titulo) {
        String sql = "SELECT * FROM Movie WHERE titulo = ?";
        Filme filme = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, Titulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                filme = mapResultSetToFilme(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(filme);
    }

    @Override
    public boolean isInExibicao(long filmeId) {
        return isAtivo(filmeId);
    }

    @Override
    public Filme create(Filme entity) {
        String sql = "INSERT INTO Movie(title, genre, synopsis, parental_rating, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, entity.getTitulo());
            stmt.setString(2, entity.getGenero().toString());
            stmt.setString(3,entity.getSinopse());
            stmt.setString(4,entity.getClassificacaoIndicativa());
            stmt.setString(5, entity.getStatus().toString());
            stmt.setLong(6, entity.getId());
            stmt.execute();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Filme> findOne(Long id) {
        String sql = "SELECT * FROM Movie WHERE id = ?";
        Filme filme = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                filme = mapResultSetToFilme(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(filme);
    }



    @Override
    public List<Filme> findAll() {
        String sql = "SELECT * FROM Movie";
        List<Filme> filmes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                filmes.add(mapResultSetToFilme(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filmes;

    }

    @Override
    public boolean update(Filme entity) {
        String sql = "UPDATE Movie SET title = ?, genre = ?, synopsis = ?, parental_rating = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, entity.getTitulo());
            stmt.setString(2, entity.getGenero().name());
            stmt.setString(3, entity.getSinopse());
            stmt.setString(4, entity.getClassificacaoIndicativa());
            stmt.setString(5, entity.getStatus().name());
            stmt.setLong(6, entity.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
        //n sei qual melhor jeito de deixar
//        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
//            stmt.setString(1, entity.getTitulo());
//            stmt.setString(2,entity.getGenero().toString());
//            stmt.setString(3,entity.getSinopse());
//            stmt.setString(4,entity.getClassificacaoIndicativa());
//            stmt.setString(5,entity.getStatus().toString());
//        }
//        catch (SQLException exception){
//            exception.printStackTrace();
//            return false;
//        };
//        return true;
    }

    @Override
    public boolean deleteByKey(Long id) {
        String sql = "DELETE FROM Movie WHERE id = ?";
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
    public boolean delete(Filme entity) {
        return deleteByKey(entity.getId());
    }

    @Override
    public boolean isAtivo(Long id) {
        String sql = "SELECT status FROM Movie WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return FilmeStatus.valueOf(rs.getString("status")) == FilmeStatus.ATIVO;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    private Filme mapResultSetToFilme(ResultSet rs) throws SQLException {
        Filme filme = new Filme();
        filme.setId(rs.getLong("id"));
        filme.setTitulo(rs.getString("titulo"));
        filme.setGenero(FilmeGenero.valueOf(rs.getString("genero")));
        filme.setSinopse(rs.getString("sinopse"));
        filme.setClassificacaoIndicativa(rs.getString("classificacaoIndicativa"));
        filme.setStatus(FilmeStatus.valueOf(rs.getString("status")));
        return filme;
    }
}
