package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.time.Duration;
import java.time.LocalDateTime;

public class ExibicaoInputRequestValidator extends Validator<Exibicao> {

    @Override
    public Notification validate(Exibicao exibicao) {
        Notification notification = new Notification();

        if (exibicao == null) {
            notification.addError("Exibição é nula");
            return notification;
        }

        if (exibicao.getSala() == null) {
            notification.addError("Sala é nula");
        } else if (exibicao.getSala().getStatus() != SalaStatus.ATIVO) {
            notification.addError("Sala não está ativa");
        }

        if (exibicao.getFilme() == null) {
            notification.addError("Filme é nulo");
        } else if (exibicao.getFilme().getStatus() != FilmeStatus.ATIVO) {
            notification.addError("Filme não está ativo");
        }

        if (exibicao.getHorarioData() == null || exibicao.getHorarioData().isBefore(LocalDateTime.now())) {
            notification.addError("Data de exibição é inválida");
        }

        if (exibicao.getQntIngressosDisponiveis() < 0) {
            notification.addError("Quantidade de ingressos disponíveis é inválida");
        }

        if (exibicao.getTempoDuracao().compareTo(Duration.ZERO) <= 0) {
            notification.addError("Duração da exibição deve ser maior que zero");
        }

        return notification;
    }
}


