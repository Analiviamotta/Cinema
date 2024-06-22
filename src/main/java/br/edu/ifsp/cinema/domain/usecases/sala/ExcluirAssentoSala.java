package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;

public class ExcluirAssentoSala {
    private SalaDAO salaDAO;

    public ExcluirAssentoSala(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public void excluirAssento(Long salaId, Assento assentoParaExcluir) {
        Sala sala = salaDAO.findOne(salaId)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (!sala.getAssentoList().contains(assentoParaExcluir)) {
            throw new EntityNotFoundException("Assento não encontrado nesta sala");
        }

        sala.removeAssento(assentoParaExcluir);

        salaDAO.update(sala);
    }
}
