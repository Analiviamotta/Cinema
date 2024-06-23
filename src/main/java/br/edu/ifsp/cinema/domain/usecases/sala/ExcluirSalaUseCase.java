package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.Optional;


public class ExcluirSalaUseCase {
    private SalaDAO salaDAO;

    public ExcluirSalaUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public boolean remove(Long id) {
        Optional<Sala> salaOpt = salaDAO.findOne(id);
        if (salaOpt.isEmpty()) {
            throw new EntityNotFoundException("Sala não encontrada");
        }
        Sala sala = salaOpt.get();
        if (salaDAO.isAtivo(sala.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma sala ativa");
        }

        if (salaDAO.isInExibicao(sala.getId())) {
            throw new IllegalArgumentException("Não é possível excluir uma sala que está em uma exibição");
        }

        return salaDAO.deleteByKey(id);
    }

    public boolean remove(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("A sala informada não pode ser nula");
        }
        long id = sala.getId();
        Optional<Sala> salaOpt = salaDAO.findOne(id);
        if (salaOpt.isEmpty()) {
            throw new EntityNotFoundException("Sala não encontrada");
        }

        if (salaDAO.isAtivo(sala.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma sala ativa");
        }

        if (salaDAO.isInExibicao(sala.getId())) {
            throw new IllegalArgumentException("Não é possível excluir uma sala que está em uma exibição");
        }

        return salaDAO.delete(sala);
    }

}
