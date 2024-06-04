package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;


public interface ExibicaoDAO extends DAO<Exibicao, Long> {
    List<Sessao> listarSessoesDaExibicao(Exibicao exibicao);
    Optional<Exibicao> findOne(Long Exibicao);
}
