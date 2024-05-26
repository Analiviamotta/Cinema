package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class CriarSalaUseCase {

    private SalaDAO salaDAO;

    public CriarSalaUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    } // a classe nao precisa saber qual o banco, desde que ele seja um DAO de sala

    public Integer insert(Sala sala) {
        Validator<Sala> validator= new SalaInputRequestValidator();
        Notification notification = validator.validate(sala);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        int number = sala.getNumber();
        if(salaDAO.findByNumber(number).isPresent()){
            throw new EntityAlreadyExistsException("A sala informada já existe");
        }

        return salaDAO.create(sala);
    }
}
