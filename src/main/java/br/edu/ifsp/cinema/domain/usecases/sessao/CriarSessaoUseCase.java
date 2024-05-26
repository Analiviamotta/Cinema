package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.util.Optional;


public class CriarSessaoUseCase {
    private SalaDAO salaDAO;
    private SessaoDAO sessaoDAO;

    public CriarSessaoUseCase(SalaDAO salaDAO, SessaoDAO sessaoDAO) {
        this.salaDAO = salaDAO;
        this.sessaoDAO = sessaoDAO;
    }

    public Sessao insert(Sessao sessao) {
        Validator<Sessao> validator = new SessaoInputRequestValidator();
        Notification notification = validator.validate(sessao);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Optional<Sala> sala = salaDAO.findOne(sessao.getId());
        if(sala.isEmpty()){
            throw  new EntityNotFoundException("Sala não encontrada");
        }

        if (sala.get().getStatus() != SalaStatus.ATIVO) {
            throw new InactiveObjectException("A sala selecionada não está ativa");
        }

        // to do: verificar "sala disponivel"

        return sessaoDAO.create(sessao);
    }


}