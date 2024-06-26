package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;


// olhar para o InputRequestValidator, mas basicamente vai manter
// as mesmas verificações do CriarExibicao

public class EditarExibicaoUseCase {
    private static ExibicaoDAO exibicaoDAO;

    public EditarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public static Boolean update(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = exibicao.getId();
        if (exibicaoDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("A exibição informada não existe");
        }

        if (exibicaoDAO.exibicaoExistenteNaMesmaDataHorarioSala(exibicao)) {
            throw new IllegalArgumentException("Já existe uma exibição na mesma data, horário e sala");
        }

        return exibicaoDAO.update(exibicao);
    }
}




