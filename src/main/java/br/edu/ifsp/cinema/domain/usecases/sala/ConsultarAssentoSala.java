package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import java.util.List;

public class ConsultarAssentoSala {
    private static SalaDAO salaDAO;

    public ConsultarAssentoSala(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public static List<Assento> consultarAssentos(Long salaId) {
        Sala sala = salaDAO.findOne(salaId)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        return sala.getAssentoList();
    }
}
