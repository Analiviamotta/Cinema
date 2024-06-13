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
    }

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
        if(salaDAO.isAtivo(sala.getId())){
            throw new InactiveObjectException("Não é possível editar uma sala ativa");
        }
        if (salaDAO.isInExibicao(sala.getId())) {
            throw new IllegalArgumentException("Não é possível editar uma sala que está em uma exibição");
        }
        if (!sala.getAssentoList().equals(salaDAO.findOne(id).get().getAssentoList())) {
            throw new IllegalArgumentException("Não é possível editar os assentos da sala");
        }

        // tem que estar inativa
        // não pode editar uma sala que está na exibição
        // não pode editar os assentos

        return salaDAO.update(sala);
    }
}
