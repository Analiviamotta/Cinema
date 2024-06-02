package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;

import java.util.List;

public class ExportOnTheTerminal implements VendaExportable{
    @Override
    public void generatesExportableReport(List<Venda> entities) {
        for(Venda v: entities){
            System.out.println(v);
        }
    }
}
