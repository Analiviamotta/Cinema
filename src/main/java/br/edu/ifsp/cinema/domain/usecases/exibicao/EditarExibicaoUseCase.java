package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.time.LocalDateTime;

public class EditarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;

    public EditarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public Boolean update(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);
        
        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        if (exibicao.getSessao().getDataFim().atTime(exibicao.getSessao().getHorarios().get(exibicao.getSessao().getHorarios().size() - 1)).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível atualizar uma exibição que já ocorreu");
        }

        if (exibicao.getQntIngressosDisponiveis() < 0) {
            throw new IllegalArgumentException("A quantidade de ingressos disponíveis não pode ser negativa");
        }

        long id = exibicao.getId();
        if (exibicaoDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("A exibição informada não existe");
        }

        return exibicaoDAO.update(exibicao);
    }
}




  