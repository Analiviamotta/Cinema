package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExibicaoInputRequestValidator extends Validator<Exibicao> {

    @Override
    public Notification validate(Exibicao exibicao) {
        Notification notification = new Notification();

        if (exibicao == null) {
            notification.addError("Exibição is null");
            return notification;
        }

        if (exibicao.getSessao() == null) {
            notification.addError("Sessão is null");
        }

        if (exibicao.getSala() == null) {
            notification.addError("Sala is null");
        }

        if (exibicao.getQntIngressosDisponiveis() < 0) {
            notification.addError("Quantidade de ingressos disponíveis is invalid");
        }


        // excluir esse getSessao
        if (exibicao.getSessao().getDataInicio().isBefore(LocalDate.now())) {
            notification.addError("Não é possível criar uma exibição para uma data/hora passada");
        }

        return notification;
    }
}

