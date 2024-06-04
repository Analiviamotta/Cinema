package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;


public class CriarSessaoUseCase {
    private FilmeDAO filmeDAO;
    private SessaoDAO sessaoDAO;

    public CriarSessaoUseCase(FilmeDAO filmeDAO, SessaoDAO sessaoDAO) {
        this.filmeDAO= filmeDAO;
        this.sessaoDAO = sessaoDAO;
    }

    public Sessao insert(Sessao sessao) {
        Validator<Sessao> validator = new SessaoInputRequestValidator();
        Notification notification = validator.validate(sessao);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Filme filme = filmeDAO.findByTitulo(sessao.getFilme().getTitulo())
                .orElseThrow(() -> new IllegalArgumentException("O filme selecionado não existe"));

        if (filme.getStatus() != FilmeStatus.ATIVO) {
            throw new InactiveObjectException("O filme selecionado não está ativo");
        }

        sessao.setFilme(filme);

        return sessaoDAO.create(sessao);
    }
}