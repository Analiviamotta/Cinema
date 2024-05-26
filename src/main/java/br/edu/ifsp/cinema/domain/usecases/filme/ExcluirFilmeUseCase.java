package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.Optional;

public class ExcluirFilmeUseCase {
    private FilmeDAO filmeDAO;

    public ExcluirFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public boolean remove(long id) {
        Optional<Filme> filmeOpt = filmeDAO.findOne(id);
        if (filmeOpt.isEmpty()) {
            throw new EntityNotFoundException("Filme não encontrado");
        }
        Filme filme = filmeOpt.get();
        if (filmeDAO.isAtivo(filme.getStatus())) {
            throw new InactiveObjectException("Não é possível excluir um filme ativo");
        }

        //id é um long primitivo e não pode ser null
        return filmeDAO.deleteByKey(id);
    }

    public boolean remove(Filme filme) {
        if (filme == null) {
            throw new IllegalArgumentException("O filme informado não pode ser nulo");
        }
        long id = filme.getId();
        Optional<Filme> filmeOpt = filmeDAO.findOne(id);
        if (filmeOpt.isEmpty()) {
            throw new EntityNotFoundException("Filme não encontrado");
        }
        if (filmeDAO.isAtivo(filme.getStatus())) {
            throw new InactiveObjectException("Não é possível excluir um filme ativo");
        }
        return filmeDAO.delete(filme);
    }

}
