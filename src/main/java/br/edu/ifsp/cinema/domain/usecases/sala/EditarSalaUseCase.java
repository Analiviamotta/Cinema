package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class EditarSalaUseCase {

    private SalaDAO salaDAO;

    public EditarSalaUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    } // a classe nao precisa saber qual o banco, desde que ele seja um DAO de sala

    public Boolean update(Sala sala) {
        Validator<Sala> validator= new SalaInputRequestValidator();
        Notification notification = validator.validate(sala);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = sala.getId();
        if(salaDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("A sala informada não existe");
        }
        if(salaDAO.isAtivo(sala.getStatus())){
            throw new InactiveObjectException("Não é possível editar uma sala ativa");
        }

        return salaDAO.update(sala);
    }
}
