package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

public class InativarSalaUseCase {
    private SalaDAO salaDAO;

    public InativarSalaUseCase (SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public void inativarSala(Long id) {
        Sala sala = salaDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (sala.getStatus() != SalaStatus.ATIVO) {
            throw new InactiveObjectException("A sala já está inativa");
        }

        if (salaDAO.isInExibicao(sala.getId())) {
            throw new IllegalArgumentException("Não é possível inativar uma sala que está em uma exibição");
        }

        sala.setStatus(SalaStatus.INATIVO);
        salaDAO.update(sala);
    }
}