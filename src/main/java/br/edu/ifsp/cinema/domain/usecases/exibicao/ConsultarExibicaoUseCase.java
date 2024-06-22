package br.edu.ifsp.cinema.domain.usecases.exibicao;


import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;

import java.util.Optional;

public class ConsultarExibicaoUseCase {

    private ExibicaoDAO exibicaoDAO;

    public ConsultarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public Optional<Exibicao> findOne(Long exibicaoId) {
        return exibicaoDAO.findOne(exibicaoId);
    }
}
