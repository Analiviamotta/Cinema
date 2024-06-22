package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.usecases.utils.DAO;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;

import java.util.Optional;

public interface FilmeDAO extends DAO<Filme, Long> {
        Optional<Filme> findByTitulo(String Titulo);
        boolean isInExibicao(long filmeId);
}
