package br.edu.ifsp.cinema.domain.usecases.sala;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.util.Collections;

public class SalaInputRequestValidator extends Validator<Sala> {
    @Override
    public Notification validate(Sala sala) {
        Notification notification = new Notification();
        if(sala == null){
            notification.addError("Sala is null");
            return notification;
        }

        //singleton para a collection receber apenas um elemento
        if(nullOrEmpty(Collections.singleton(sala.getNumber()))){
            notification.addError("Número da sala is null or empty");
        }
        if(nullOrEmpty(Collections.singleton(sala.getNumLinhas()))){
            notification.addError("Número de linhas is null or empty");
        }
        if(nullOrEmpty(Collections.singleton(sala.getNumColunas()))){
            notification.addError("Número de colunas is null or empty");
        }
        if(nullOrEmpty(Collections.singleton(sala.getCapacidade()))){
            notification.addError("Capacidade is null or empty");
        }

        return notification;
    }
}
