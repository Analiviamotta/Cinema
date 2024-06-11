package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.*;

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

        //id é um long primitivo e não pode ser null
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

        // chamar o inativarSalaUseCase para verificar se a sala não está em uma exibição
        if (salaDAO.isAtivo(sala.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma sala ativa");
        }
        return salaDAO.delete(sala);
    }

}
