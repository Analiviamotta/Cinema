package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.application.main.repository.sqlite.util.ConnectionFactory;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDaoSqlite implements FilmeDAO {
    @Override
    public Optional<Filme> findByTitulo(String titulo) {
        String sql = "SELECT * FROM Movie WHERE title = ?";
        Filme filme = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                filme = mapResultSetToFilme(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(filme);
    }

//    @Override
//    public boolean isInExibicao(long filmeId) {
//        return isAtivo(filmeId);
//    }

    @Override
    public boolean isInExibicao(long filmeId) {
        String sql = "SELECT COUNT(*) FROM Exhibition WHERE movie_id = ? AND date_time > ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, filmeId);
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


    public Filme create(Filme entity) {
        String sql = "INSERT INTO Movie (title, genre, synopsis, parental_rating, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getTitulo());
            stmt.setString(2, entity.getGenero().toString());
            stmt.setString(3, entity.getSinopse());
            stmt.setString(4, entity.getClassificacaoIndicativa());
            stmt.setString(5, entity.getStatus().toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating movie failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating movie failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }



    @Override
    public Optional<Filme> findOne(Long id) {
        String sql = "SELECT * FROM Movie WHERE id = ?";
        Filme filme = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String generoDoBanco = rs.getString("genre").toLowerCase();
                FilmeGenero generoEnum = FilmeGenero.fromString(generoDoBanco.toUpperCase());

                filme = new Filme(
                        rs.getString("title"),
                        generoEnum,
                        rs.getString("synopsis"),
                        rs.getString("parental_rating")
                );
                filme.setId(rs.getLong("id"));
                filme.setStatus(FilmeStatus.valueOf(rs.getString("status").toUpperCase()));
                return Optional.of(filme);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Filme> findAll() {
        String sql = "SELECT * FROM Movie";
        List<Filme> filmes = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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
        /*
        Optional<Filme> existingFilm = findByTitulo(entity.getTitulo());
        if (existingFilm.isPresent() && !existingFilm.get().getId().equals(entity.getId())) {
            throw new EntityAlreadyExistsException("Já existe um filme com o mesmo título: " + entity.getTitulo());
        }
        */

        String sql = "UPDATE Movie SET title = ?, genre = ?, synopsis = ?, parental_rating = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, entity.getTitulo());
            stmt.setString(2, entity.getGenero().toString());
            stmt.setString(3, entity.getSinopse());
            stmt.setString(4, entity.getClassificacaoIndicativa());
            stmt.setString(5, entity.getStatus().toString());
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
                return FilmeStatus.valueOf(rs.getString("status").toUpperCase()) == FilmeStatus.ATIVO;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void deteleAllFilmes() {
        String deleteMovieSql = "DELETE FROM Movie";
        try (PreparedStatement deleteMovieStmt = ConnectionFactory.createPreparedStatement(deleteMovieSql)) {
            deleteMovieStmt.executeUpdate();
            System.out.println("Todas os filmes foram excluídos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Filme mapResultSetToFilme(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String titulo = rs.getString("title");
        FilmeGenero genero = FilmeGenero.fromString(rs.getString("genre"));
        String sinopse = rs.getString("synopsis");
        String classificacaoIndicativa = rs.getString("parental_rating");
        FilmeStatus status = FilmeStatus.valueOf(rs.getString("status").toUpperCase());

        Filme filme = new Filme(titulo, genero, sinopse, classificacaoIndicativa);
        filme.setId(id);
        filme.setStatus(status);

        return filme;
    }


}
