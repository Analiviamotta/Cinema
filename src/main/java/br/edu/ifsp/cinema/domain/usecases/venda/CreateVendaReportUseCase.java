package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;


import java.time.LocalDate;
import java.util.List;

public class CreateVendaReportUseCase {
    private static VendaDAO vendaDAO;

    public CreateVendaReportUseCase(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public static List<Venda> findAllByPeriod(LocalDate inicio, LocalDate fim) {

        List<Venda> vendas;

        if (inicio == null && fim == null) {
            vendas = vendaDAO.findAll();  // Assuming there's a method to get all sales
        } else {
            vendas = vendaDAO.findAllByPeriod(inicio, fim);
        }

        if (vendas.isEmpty()) {
            throw new EntityNotFoundException("Não há vendas cadastradas");
        }
        return vendas;
    }
}