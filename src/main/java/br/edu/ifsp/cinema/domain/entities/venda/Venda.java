package br.edu.ifsp.cinema.domain.entities.venda;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Venda {
    private Long id;
    private List<Ingresso> ingressoList;
    private Exibicao exibicao;
    private Date data;
    private BigDecimal valorTotal;


    public Venda(Exibicao exibicao, Date data, Ingresso... ingressos) {
        this.exibicao = exibicao;
        this.data = data;
        this.ingressoList = new ArrayList<>();

        // Adiciona cada ingresso à lista
        for (Ingresso ingresso : ingressos) {
            this.ingressoList.add(ingresso);
        }
        setValorTotal(this.ingressoList);
    }

    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public List<Ingresso> getIngressoList() {
        return ingressoList;
    }

//    public void setIngressoList(List<Ingresso> ingressoList) {
//        this.ingressoList = ingressoList;
//    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    public void setExibicao(Exibicao exibicao) {
        this.exibicao = exibicao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        this.ingressoList.add(ingresso);
    }

    public void atualizarIngressosDisponiveis (List<Ingresso> ingressoList) {
        int qntIngressosVendidos = ingressoList.size();
        int qntIngressosDisponiveis = this.exibicao.getQntIngressosDisponiveis();
        this.exibicao.setQntIngressosDisponiveis(qntIngressosDisponiveis - qntIngressosVendidos);
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(List<Ingresso> ingressos) {
        BigDecimal total = BigDecimal.ZERO;

        for (Ingresso ingresso : ingressos) {
            BigDecimal precoIngresso = ingresso.getPreco();
            total = total.add(precoIngresso);
        }

        this.valorTotal = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Venda{");
        sb.append("id=").append(id);
        sb.append(", exibicao=").append(exibicao != null ? exibicao.toString() : "null");
        sb.append(", data=").append(data);

        if (ingressoList != null && !ingressoList.isEmpty()) {
            sb.append(", ingressoList=[");
            for (int i = 0; i < ingressoList.size(); i++) {
                sb.append(ingressoList.get(i).toString());
                if (i < ingressoList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        } else {
            sb.append(", ingressoList=null");
        }

        sb.append(", valorTotal=").append(valorTotal);
        sb.append("}");

        return sb.toString();
    }
}
