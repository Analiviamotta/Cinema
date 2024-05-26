package br.edu.ifsp.cinema.domain.usecases.utils;
import java.util.List;
import java.util.Optional;

public interface DAO<T, K> {
    K create(T entity);
    Optional<T> findOne(long key);
    List<T> findAll();
    boolean update(T entity);
    boolean deleteByKey(K key);
    boolean delete(T entity);
}
