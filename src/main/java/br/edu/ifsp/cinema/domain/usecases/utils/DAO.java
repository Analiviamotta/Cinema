package br.edu.ifsp.cinema.domain.usecases.utils;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;

import java.util.List;
import java.util.Optional;

public interface DAO<T, K> {
    T create(T entity);
    Optional<T> findOne(K key);
    List<T> findAll();
    boolean update(T entity);
    boolean deleteByKey(K key);
    boolean delete(T entity);
    boolean isAtivo(K key);
}
