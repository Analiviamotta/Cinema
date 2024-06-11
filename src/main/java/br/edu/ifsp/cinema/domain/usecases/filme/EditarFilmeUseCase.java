package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class EditarFilmeUseCase {
    private FilmeDAO filmeDAO;

    public EditarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    } // a classe nao precisa saber qual o banco, desde que ele seja um DAO de sala

    public Boolean update(Filme filme) {
        Validator<Filme> validator= new FilmeInputRequestValidator();
        Notification notification = validator.validate(filme);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = filme.getId();
        if(filmeDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("O filme informado não existe");
        }
        /*if(filmeDAO.isAtivo(filme.getId())){
            throw new InactiveObjectException("Não é possível editar um filme ativo");
        }*/

        // só pode editar um filme que está ativo
        // não pode editar um filme que está em uma sessão
        // não pode editar o nome

        return filmeDAO.update(filme);
    }
}

