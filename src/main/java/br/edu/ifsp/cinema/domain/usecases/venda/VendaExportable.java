package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.export.Exportable;

import java.util.List;

public interface VendaExportable extends Exportable<Venda> {
    void generatesExportableReport(List<Venda> entities);
}
