package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreateVendaReportUseCase {
    private VendaDAO vendaDAO;
    private VendaExportable vendaExportable;

    public CreateVendaReportUseCase(VendaDAO vendaDAO, VendaExportable vendaExportable) {
        this.vendaDAO = vendaDAO;
        this.vendaExportable = vendaExportable;
    }

    public List<Venda> returnVendas(LocalDate startDate, LocalDate endDate, Filme filme){
        if (!startDate.isBefore(endDate) && !startDate.isEqual(endDate)) {
            throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data final.");
        }
        List<Venda> report = vendaDAO.findAll().stream()
                .filter(venda -> !venda.getData().isBefore(startDate) &&
                        !venda.getData().isAfter(endDate) &&
                        (filme == null || venda.getIngressoList().stream()
                                .anyMatch(ingresso -> ingresso.getFilme().equals(filme))))
                .collect(Collectors.toList());

        if (report.isEmpty()) {
            System.out.println("Não há registro de vendas no período selecionado.");
        } else {
            vendaExportable.generatesExportableReport(report);
        }
        return report;
    }
}
