package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.sessao.SessaoDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;
import java.util.Optional;

public class CriarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;
    private SalaDAO salaDAO;
    private SessaoDAO sessaoDAO;

    public CriarExibicaoUseCase(ExibicaoDAO exibicaoDAO, SalaDAO salaDAO, SessaoDAO sessaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
        this.salaDAO = salaDAO;
        this.sessaoDAO = sessaoDAO;
    }

    public Exibicao insert(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Optional<Sala> sala = salaDAO.findOne(exibicao.getSala().getId());
        if (sala.isEmpty()) {
            throw new EntityNotFoundException("Sala não encontrada");
        }

        if (sala.get().getStatus() != SalaStatus.ATIVO) {
            throw new InactiveObjectException("A sala selecionada não está ativa");
        }

        Optional<Sessao> sessao = sessaoDAO.findOne(exibicao.getSessao().getId());
        if (sessao.isEmpty()) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }

        return exibicaoDAO.create(exibicao);
    }
}
