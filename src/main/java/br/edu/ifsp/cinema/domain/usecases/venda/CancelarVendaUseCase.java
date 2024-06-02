package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;

import java.util.Optional;

public class CancelarVendaUseCase {
    private VendaDAO vendaDAO;
    private VendaInputRequestValidator validator;

    public CancelarVendaUseCase(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public boolean cancel(Long id){

        Optional<Venda> optionalVenda = vendaDAO.findOne(id);
        if(optionalVenda.isPresent()) {
            Venda venda = optionalVenda.get();
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
        venda.cancelarVenda();
        return vendaDAO.update(venda);
    }


}
