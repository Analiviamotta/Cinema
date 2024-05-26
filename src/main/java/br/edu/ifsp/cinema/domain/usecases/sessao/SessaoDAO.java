package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.Optional;

public interface SessaoDAO extends DAO<Sessao, Long> {
    Sessao create(Sessao sessao);
    Optional<Sessao> findOne(long id);
}
