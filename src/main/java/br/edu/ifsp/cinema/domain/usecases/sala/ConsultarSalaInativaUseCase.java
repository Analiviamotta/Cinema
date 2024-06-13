package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultarSalaInativaUseCase {
    private SalaDAO salaDAO;

    public ConsultarSalaInativaUseCase (SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public List<Sala> findAllInativas() {
        List<Sala> salasInativasList = salaDAO.findAll().stream()
                .filter(sala -> sala.getStatus() == SalaStatus.INATIVO)
                .collect(Collectors.toList());

        if (salasInativasList.isEmpty()) {
            throw new EntityNotFoundException("Não há salas inativas cadastradas");
        }

        return salasInativasList;
    }
}