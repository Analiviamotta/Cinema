package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;

public class InativarFilmeUseCase {
    private FilmeDAO filmeDAO;

    public InativarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public void inativarFilme(Long id) {
        Filme filme = filmeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        if (filme.getStatus() != FilmeStatus.ATIVO) {
            throw new InactiveObjectException("O filme já está inativo");
        }

        if (filmeDAO.isInExibicao(filme.getId())) {
            throw new IllegalArgumentException("Não é possível inativar um filme que está em uma exibição");
        }

        filme.setStatus(FilmeStatus.INATIVO);
        filmeDAO.update(filme);
    }
}

    // verificar se está ativo
    // se tiver ativo:
    // não tem relação com nada (sessão)


