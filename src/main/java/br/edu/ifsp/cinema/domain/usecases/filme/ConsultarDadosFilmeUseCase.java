package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.List;
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

    public Optional<Filme> findByTitulo(String titulo) {
        Optional<Filme> filmeOpt = filmeDAO.findByTitulo(titulo);
        if (filmeOpt.isEmpty()) {
            // Se não encontrar o filme, retorna Optional.empty()
            return Optional.empty();
        }

        Filme filme = filmeOpt.get();
        if (filme.getStatus() == FilmeStatus.INATIVO) {
            // Se o filme encontrado estiver inativo, lança uma exceção
            throw new InactiveObjectException("Filme inativo: " + filme.getTitulo());
        }

        // Retorna o filme encontrado
        return filmeOpt;
    }
}

