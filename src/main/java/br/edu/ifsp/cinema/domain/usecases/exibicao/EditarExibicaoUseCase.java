package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.sessao.SessaoDAO;
import br.edu.ifsp.cinema.domain.usecases.sessao.SessaoInputRequestValidator;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class EditarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;

    public EditarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public Boolean update(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);
        
        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = exibicao.getId();
        if (exibicaoDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("A exibição informada não existe");
        }
        return exibicaoDAO.update(exibicao);
        
    }
}




  