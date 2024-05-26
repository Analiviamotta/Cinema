package br.edu.ifsp.cinema.domain.entities.relatorioVendas;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatorioVendas {
    private Date dataInicio;
    private Date dataFim;
    private List<Venda> vendaList;

    public RelatorioVendas(Date dataInicio, Date dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

// To DO:

// public void exportarRelatorioVendas() {}

//  TO DO: tirar os comentario quando fizer vendaDAO


//    public List<Venda> gerarRelatorioVendasPeriodo(Date inicio, Date fim) {
//        List<Venda> todasVendaList =  new ArrayList<>(vendaDAO.findAll());
//        List<Venda> vendaList = new ArrayList<>();
//
//        for (Venda venda : todasVendaList) {
//            Date dataVenda = venda.getData();
//
//            if (dataVenda.after(inicio) && dataVenda.before(fim)) {
//                vendaList.add(venda);
//            }
//        }
//
//        return vendaList;
//    }
}

