package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.Optional;

public class ConsultarDadosSalaUseCase {
    private SalaDAO salaDAO;

    public ConsultarDadosSalaUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public Optional<Sala> findOne(long id) {
        Optional<Sala> salaOpt = salaDAO.findOne(id);
        if (salaOpt.isEmpty()) {
            throw new EntityAlreadyExistsException("Sala não encontrada");
        }
        Sala sala = salaOpt.get();
        if (sala.getStatus() == SalaStatus.INATIVO) {
            throw new InactiveObjectException("Sala inativa");
        }

        return salaOpt;
    }
}
