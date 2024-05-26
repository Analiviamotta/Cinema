package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class CriarFilmeUseCase {
    private br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO filmeDAO;

    public CriarFilmeUseCase(SalaDAO salaDAO) {
        this.filmeDAO= filmeDAO;
    } // a classe nao precisa saber qual o banco, desde que ele seja um DAO de sala

    public Filme insert(Filme filme) {
        Validator<Filme> validator = new FilmeInputRequestValidator();
        Notification notification = validator.validate(filme);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        //filme é procurado por titulo mas cade o titulo no diagrama de classes ???

//        String titulo = filme.getTitulo();
//        if(filmeDAO.findByTitulo(titulo).isPresent()){
//            throw new EntityAlreadyExistsException("O filme informado já existe");
//        }

        return filmeDAO.create(filme);
    }
}