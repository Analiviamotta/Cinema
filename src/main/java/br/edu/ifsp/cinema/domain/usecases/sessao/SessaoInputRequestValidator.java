package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.entities.sessao.SessaoDiaSemana;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.util.Collections;

public class SessaoInputRequestValidator extends Validator<Sessao> {

    @Override
    public Notification validate(Sessao sessao) {
        Notification notification = new Notification();

        if (sessao == null) {
            notification.addError("Sessao is null");
            return notification;
        }
        if (sessao.getDiaSemana() == null || sessao.getDiaSemana().isEmpty()) {
            notification.addError("Dia da semana is null or empty");
        }
        if (sessao.getHorarios() == null || sessao.getHorarios().isEmpty()) {
            notification.addError("Horarios is null or empty");
        }
        if (sessao.getDataInicio() == null) {
            notification.addError("Data de inicio is null");
        }
        if (sessao.getDataFim() == null) {
            notification.addError("Data de fim is null");
        }
        if (sessao.getTempoDuracao() == null) {
            notification.addError("Tempo de duracao is null");
        }
        if (sessao.getFilme() == null) {
            notification.addError("Filme is null");
        }

        return notification;
    }
}
