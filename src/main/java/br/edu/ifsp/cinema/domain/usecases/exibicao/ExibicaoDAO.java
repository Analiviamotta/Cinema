package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.usecases.utils.DAO;
import java.util.Optional;


public interface ExibicaoDAO extends DAO<Exibicao, Long> {
    Optional<Exibicao> findOne(Long Exibicao);
}
