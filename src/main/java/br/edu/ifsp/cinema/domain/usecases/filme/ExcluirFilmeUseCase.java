package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.Optional;

public class ExcluirFilmeUseCase {
    private static FilmeDAO filmeDAO;

    public ExcluirFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public boolean remove(Long id) {
        Optional<Filme> filmeOpt = filmeDAO.findOne(id);
        if (filmeOpt.isEmpty()) {
            throw new EntityNotFoundException("Filme não encontrado");
        }
        Filme filme = filmeOpt.get();
        if (filmeDAO.isAtivo(filme.getId())) {
            throw new InactiveObjectException("Não é possível excluir um filme ativo");
        }

        if (filmeDAO.isInExibicao(filme.getId())) {
            throw new IllegalArgumentException("Não é possível excluir um filme que está em uma exibição");
        }

        return filmeDAO.deleteByKey(id);
    }

    public static boolean remove(Filme filme) {
        if (filme == null) {
            throw new IllegalArgumentException("O filme informado não pode ser nulo");
        }
        long id = filme.getId();
        Optional<Filme> filmeOpt = filmeDAO.findOne(id);
        if (filmeOpt.isEmpty()) {
            throw new EntityNotFoundException("Filme não encontrado");
        }
        if (filmeDAO.isAtivo(filme.getId())) {
            throw new InactiveObjectException("Não é possível excluir um filme ativo");
        }

        if (filmeDAO.isInExibicao(filme.getId())) {
            throw new IllegalArgumentException("Não é possível excluir um filme que está em uma exibição");
        }

        return filmeDAO.delete(filme);
    }

}
