package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;
import java.util.Optional;

public interface SalaDAO extends DAO<Sala, Long> {
    Optional<Sala> findByNumber(int numero);
    boolean isInExibicao(long id);
}