package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

public class AtivarSalaUseCase {
    private SalaDAO salaDAO;

    public AtivarSalaUseCase (FilmeDAO filmeDAO) {
        this.salaDAO = salaDAO;
    }

    public void ativarFilme(Long id) {
        Sala sala = salaDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (sala.getStatus() == SalaStatus.ATIVO) {
            throw new EntityAlreadyExistsException("A sala já está ativa");
        }

        sala.setStatus(SalaStatus.ATIVO);
        salaDAO.update(sala);
    }
}
