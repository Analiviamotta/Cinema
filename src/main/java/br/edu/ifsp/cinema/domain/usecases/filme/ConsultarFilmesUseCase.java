package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultarFilmesUseCase {
    private static FilmeDAO filmeDAO;

    public ConsultarFilmesUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public static List<Filme> findAll() {
        List<Filme> filmesAtivosList = filmeDAO.findAll();

        if(filmesAtivosList.isEmpty()) {
            throw new EntityNotFoundException("Não há filmes cadastrados ativos");
        }

        return filmesAtivosList;
    }

    public static List<Filme> findAtivos() {
        List<Filme> filmesAtivosList = filmeDAO.findAll()
                .stream().filter(filme -> filmeDAO.isAtivo(filme.getId())).toList();

        if(filmesAtivosList.isEmpty()) {
            throw new EntityNotFoundException("Não há filmes cadastrados ativos");
        }

        return filmesAtivosList;
    }
}