package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface SessaoDAO{
    List<Sessao> findAllByFilmeId(Long filmeId);
    List<Sessao> findAll();
    Sessao create(Sessao sessao);
    boolean update(Sessao sessao);
    Optional<Sessao> findOne(Long id);
}
