package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

public class CriarAssentoSala {
    private SalaDAO salaDAO;

    public CriarAssentoSala(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public void criarAssento(Long salaId, Assento novoAssento) {
        Sala sala = salaDAO.findOne(salaId)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        sala.addAssento(novoAssento);

        salaDAO.update(sala);
    }
}
