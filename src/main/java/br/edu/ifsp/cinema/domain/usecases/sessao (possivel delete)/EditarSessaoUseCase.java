package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.time.LocalDateTime;

public class EditarSessaoUseCase {
    private SessaoDAO sessaoDAO;

    public EditarSessaoUseCase(SessaoDAO sessaoDAO) {
        this.sessaoDAO = sessaoDAO;
    }

    public Boolean update(Sessao sessao) {
        Validator<Sessao> validator = new SessaoInputRequestValidator();
        Notification notification = validator.validate(sessao);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = sessao.getId();
        if (sessaoDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("A seção informada não existe");
        }
        if(sessaoDAO.isAtivo(sessao.getId())){
            throw new InactiveObjectException("Não é possível editar uma seção ativa");
        }

        if (sessao.getDataFim().atTime(sessao.getHorarios().get(sessao.getHorarios().size() - 1)).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível editar uma sessão já realizada");
        }

        return sessaoDAO.update(sessao);
    }
}