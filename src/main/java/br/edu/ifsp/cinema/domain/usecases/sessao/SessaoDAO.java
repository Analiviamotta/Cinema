package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface SessaoDAO extends DAO<Sessao, Long>{
    List<Sessao> findAllByFilmeId(Long filmeId);

    Optional<Sessao> findOne(Long id);
}
