package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;


import java.time.LocalDate;
import java.util.List;

public class CreateVendaReportUseCase {
    private VendaDAO vendaDAO;

    public CreateVendaReportUseCase(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public List<Venda> findAllByPeriod(LocalDate inicio, LocalDate fim) {
        List<Venda> vendas = vendaDAO.findAllByPeriod(inicio, fim);

        if (vendas.isEmpty()) {
            throw new EntityNotFoundException("Não há vendas cadastradas");
        }
        return vendas;
    }
}
