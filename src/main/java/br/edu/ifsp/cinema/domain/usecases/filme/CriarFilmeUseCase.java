package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class CriarFilmeUseCase {
    private static FilmeDAO filmeDAO;

    public CriarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public static Filme insert(Filme filme) {
        Validator<Filme> validator = new FilmeInputRequestValidator();
        Notification notification = validator.validate(filme);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String titulo = filme.getTitulo();
        if(filmeDAO.findByTitulo(titulo).isPresent()){
            throw new EntityAlreadyExistsException("O filme informado já existe");
        }

        return filmeDAO.create(filme);
    }
}
