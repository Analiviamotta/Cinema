package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultarSalasUseCase {
    private SalaDAO salaDAO;

    public ConsultarSalasUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public List<Sala> findAll() {
        List<Sala> salasAtivasList = salaDAO.findAll().stream()
                .filter(sala -> sala.getStatus() != SalaStatus.INATIVO)
                .collect(Collectors.toList());
        //remove as salas com status inativo

        if(salasAtivasList.isEmpty()) {
            throw new EntityNotFoundException("Não há salas cadastradas ativas");
        }

        return salasAtivasList;
    }
}
