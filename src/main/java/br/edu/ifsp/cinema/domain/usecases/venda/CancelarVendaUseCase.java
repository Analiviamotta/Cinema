package br.edu.ifsp.cinema.domain.usecases.venda;

// tem que repor os ingressos que estariam vendidos (FEITO)


import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;

import java.util.Optional;

public class CancelarVendaUseCase {
    private static VendaDAO vendaDAO;
    private VendaInputRequestValidator validator;

    public CancelarVendaUseCase(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public static boolean cancel(Long id){
        Optional<Venda> optionalVenda = vendaDAO.findOne(id);
        if(optionalVenda.isPresent()) {
            Venda venda = optionalVenda.get();

            //repondo os ingressos
            for (Ingresso ingresso : venda.getIngressoList()) {
                ingresso.setVendido(false);
            }

            venda.cancelarVenda();
            return vendaDAO.update(venda);
        }
        return false;
    }

    public boolean cancel(Venda venda){
        Notification notification = validator.validate(venda);
        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        for (Ingresso ingresso : venda.getIngressoList()) {
            ingresso.setVendido(false);
        }
        venda.cancelarVenda();
        return vendaDAO.update(venda);
    }
}
