package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;


public class AtivarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;

    public AtivarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public void ativarExibicao(Long id) {
        Exibicao exibicao = exibicaoDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Exibição não encontrada"));

        if (exibicao.getStatus() == ExibicaoStatus.EFETUADA) {
            throw new EntityAlreadyExistsException("A exibição já está ativa");
        }

        exibicao.setStatus(ExibicaoStatus.EFETUADA);
        exibicaoDAO.update(exibicao);
    }
}
