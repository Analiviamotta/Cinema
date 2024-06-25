package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

public class AtivarFilmeUseCase {
    private static FilmeDAO filmeDAO;

    public AtivarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public static void ativarFilme(Long id) {
        Filme filme = filmeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        if (filme.getStatus() == FilmeStatus.ATIVO) {
            throw new EntityAlreadyExistsException("O filme já está ativo");
        }

        filme.setStatus(FilmeStatus.ATIVO);
        filmeDAO.update(filme);
    }
}
