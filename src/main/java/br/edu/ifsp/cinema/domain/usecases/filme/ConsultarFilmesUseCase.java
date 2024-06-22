package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultarFilmesUseCase {
    private FilmeDAO filmeDAO;

    public ConsultarFilmesUseCase(SalaDAO salaDAO) {
        this.filmeDAO = filmeDAO;
    }

    public List<Filme> findAll() {
        List<Filme> filmesAtivosList = filmeDAO.findAll().stream()
                .filter(filme -> filme.getStatus() != FilmeStatus.INATIVO)
                .collect(Collectors.toList());
        //remove os filmes com status inativo

        if(filmesAtivosList.isEmpty()) {
            throw new EntityNotFoundException("Não há filmes cadastrados ativos");
        }

        return filmesAtivosList;
    }
}

