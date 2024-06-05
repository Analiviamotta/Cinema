package br.edu.ifsp.cinema.domain.usecases.venda;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VendaDAO extends DAO<Venda, Long> {
    @Override
    Venda create(Venda venda);

    @Override
    boolean update(Venda venda);

    @Override
    boolean delete(Venda venda);

    @Override
    boolean deleteByKey(Long key);

    @Override
    Optional<Venda> findOne(Long key);

    @Override
    List<Venda> findAll();
    @Override
    boolean isAtivo(Long key);
    List<Ingresso> listarIngressosDaVenda(Venda venda);


    List<Venda> findAllByPeriod(LocalDate inicio, LocalDate fim);

}
