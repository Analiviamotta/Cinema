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

        if (nullOrEmpty(Collections.singleton(sessao.getDiaSemana()))) {
            notification.addError("Dia da semana is null or empty");
        }
        if (nullOrEmpty(Collections.singleton(sessao.getHora()))) {
            notification.addError("Hora das sessão is null or empty");
        }
        if (nullOrEmpty(Collections.singleton(sessao.getHora()))) {
            notification.addError("Hora das sessão is null or empty");
        }
        if(nullOrEmpty((Collections.singleton(sessao.getTempo())))){
            notification.addError(("Tempo de exibição is null or empty"));
        }
        if(nullOrEmpty((Collections.singleton(sessao.getFilme())))){
            notification.addError("Filme is null or empty ");
        }

        return notification;
    }
}
