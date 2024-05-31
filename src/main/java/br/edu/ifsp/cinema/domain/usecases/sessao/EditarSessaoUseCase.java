package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

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
        return sessaoDAO.update(sessao);
    }
}