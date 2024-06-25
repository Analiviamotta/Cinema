package br.edu.ifsp.cinema.domain.usecases.assento;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface AssentoDAO{
    Assento create(Assento entity, Long room_id);
    Optional<Assento> findOne(Long key);
    List<Assento> findAll();
    boolean update(Assento entity);
    boolean deleteByKey(Long key);
    boolean delete(Assento entity);
    boolean isAtivo(Long key);
    List<Assento> findAllByRoom(Long id);

}
