package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class CriarSalaUseCase {

    private SalaDAO salaDAO;

    public CriarSalaUseCase(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public Sala insert(Sala sala) {
        Validator<Sala> validator= new SalaInputRequestValidator();
        Notification notification = validator.validate(sala);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        int number = sala.getNumber();
        if(salaDAO.findByNumber(number).isPresent()){
            throw new EntityAlreadyExistsException("A sala informada já existe");
        }

        for (int linha = 1; linha <= sala.getNumLinhas(); linha++) {
            for (int coluna = 1; coluna <= sala.getNumColunas(); coluna++) {
                Assento assento = new Assento(coluna, linha);
                if (!sala.verificarAssentoValido(assento)) {
                    throw new IllegalArgumentException("O assento não é válido para esta sala: " + assento);
                }
                sala.addAssento(assento);
            }
        }

        return salaDAO.create(sala);
    }

}
