package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;

import java.util.Optional;

public class AtivarFilmeUseCase {
    private FilmeDAO filmeDAO;

    public AtivarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public void ativarFilme(Long id) {
        Filme filme = filmeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        if (filme.getStatus() == FilmeStatus.ATIVO) {
            throw new EntityAlreadyExistsException("O filme já está ativo");
        }

        filme.setStatus(FilmeStatus.ATIVO);
        filmeDAO.update(filme);
    }
}
