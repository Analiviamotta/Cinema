package br.edu.ifsp.cinema.domain.entities.venda;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;

import java.util.Date;
import java.util.List;

public class Venda {
    private long id;
    private List<Ingresso> ingressoList;
    private Exibicao exibicao;
    private Date data;


    public Venda(long id, List<Ingresso> ingressoList, Exibicao exibicao, Date data) {
        this.id = id;
        this.ingressoList = ingressoList;
        this.exibicao = exibicao;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Venda{");
        sb.append("id = ").append(id);
        sb.append(", exibicao = ").append(exibicao.toString());
        sb.append(", data = ").append(data);

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

        sb.append("}");

        return sb.toString();
    }
}
