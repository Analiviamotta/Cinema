package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.Optional;

public class ConsultarDadosFilmeUseCase {

    private FilmeDAO filmeDAO;

    public ConsultarDadosFilmeUseCase(FilmeDAO filmeDAO) {this.filmeDAO = filmeDAO;}

    public Optional<Filme> findOne(long id) {
        Optional<Filme> filmeOpt = filmeDAO.findOne(id);
        if (filmeOpt.isEmpty()) {
            throw new EntityAlreadyExistsException("Filme não encontrado");
        }
        Filme filme = filmeOpt.get();
        if (filme.getStatus() == FilmeStatus.INATIVO) {
            throw new InactiveObjectException("Filme inativo");
        }

        return filmeOpt;
    }
}

