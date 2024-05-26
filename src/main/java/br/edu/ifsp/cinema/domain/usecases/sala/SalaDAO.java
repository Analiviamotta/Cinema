package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface SalaDAO extends DAO<Sala, Long> {
    Optional<Sala> findByNumber(int numero);

    Sala create(Sala sala);
    Optional<Sala> findOne(long id);
    List<Sala> findAll();
    boolean update(Sala sala);
    boolean deleteByKey(long id);
    boolean delete(Sala sala);
    boolean isAtivo(SalaStatus status);
}