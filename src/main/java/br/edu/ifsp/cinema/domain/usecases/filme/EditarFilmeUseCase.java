package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class EditarFilmeUseCase {
    private static FilmeDAO filmeDAO;

    public EditarFilmeUseCase(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public static Boolean update(Filme filme) {
        Validator<Filme> validator= new FilmeInputRequestValidator();
        Notification notification = validator.validate(filme);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        long id = filme.getId();
        Filme existingFilme = filmeDAO.findByTitulo(filme.getTitulo()).orElseThrow(() -> new EntityNotFoundException("O filme informado não existe"));
        if(filmeDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("O filme informado não existe");
        }

        if (!filmeDAO.isAtivo(filme.getId())) {
            throw new InactiveObjectException("Não é possível editar um filme inativo");
        }

        if (filmeDAO.isInExibicao(filme.getId())) {
            throw new IllegalArgumentException("Não é possível editar um filme que está em uma exibição");
        }

        if (!existingFilme.getTitulo().equals(filme.getTitulo())) {
            throw new IllegalArgumentException("Não é permitido editar o título do filme");
        }

        // só pode editar um filme que está ativo
        // não pode editar um filme que está em uma exibicao
        // não pode editar o nome

        return filmeDAO.update(filme);
    }
}

