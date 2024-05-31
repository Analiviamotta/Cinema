package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;

import java.util.List;
import java.util.Optional;


public interface ExibicaoDAO {
    List<Exibicao> findAll();
    List<Sessao> listarSessoesDaExibicao(Exibicao exibicao);
    Exibicao create(Exibicao exibicao);
    boolean update(Exibicao exibicao);
    Optional<Exibicao> findOne(Long Exibicao);
}
