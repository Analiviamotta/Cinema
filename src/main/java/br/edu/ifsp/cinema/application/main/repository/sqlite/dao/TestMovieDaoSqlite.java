package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;

import java.util.List;
import java.util.Optional;

public class TestMovieDaoSqlite {
    public static void main(String[] args) {
        MovieDaoSqlite movieDao = new MovieDaoSqlite();

        // Create a new movie
        Filme filme1 = new Filme("Inception",
                FilmeGenero.DRAMA, "A mind-bending thriller", "PG-13");
        movieDao.create(filme1);
        System.out.println("Created: " + filme1);

        System.out.println("olá");

        Optional<Filme> foundFilme = movieDao.findOne(filme1.getId());
        System.out.println("Found by ID: " + foundFilme);

        List<Filme> filmes = movieDao.findAll();
        System.out.println("All movies:");
        for (Filme filme : filmes) {
            System.out.println(filme);
        }
    }
}

