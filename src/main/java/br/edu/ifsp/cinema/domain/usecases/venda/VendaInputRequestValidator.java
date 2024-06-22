package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

public class VendaInputRequestValidator extends Validator<Venda>{

    @Override
    public Notification validate(Venda venda) {
        Notification notification= new Notification();
        if(venda == null){
            notification.addError("venda is null");
            return notification;
        }
        if (venda.getExibicao() == null) {
            notification.addError("Exibição cannot be null");
        }
        if (venda.getIngressoList() == null || venda.getIngressoList().isEmpty()) {
            notification.addError("ingressos cannot be null or empty");
        }

        return notification;
    }
}
