package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;


public class CancelarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;

    public CancelarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public void inativarExibicao(Long id) {
        Exibicao exibicao = exibicaoDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Exibição não encontrada"));

        if (exibicao.getStatus() == ExibicaoStatus.CANCELADA) {
            throw new InactiveObjectException("A exibição já está inativa");
        }

        exibicao.setStatus(ExibicaoStatus.CANCELADA);
        exibicaoDAO.update(exibicao);
    }
}
