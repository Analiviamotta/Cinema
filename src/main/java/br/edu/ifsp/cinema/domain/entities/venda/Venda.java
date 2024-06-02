package br.edu.ifsp.cinema.domain.entities.venda;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Venda {
    private Long id;
    private List<Ingresso> ingressoList;
    private Exibicao exibicao;
    private LocalDate data;
    private VendaStatus status;

    public Venda(Exibicao exibicao, Ingresso... ingressos) {
        this.exibicao = exibicao;
        this.data = LocalDate.now();
        this.status = VendaStatus.EFETUADA;
        this.ingressoList = new ArrayList<>();
        for (Ingresso ingresso : ingressos) {
            adicionarIngresso(ingresso);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ingresso> getIngressoList() {
        return new ArrayList<>(ingressoList);
    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    public void setExibicao(Exibicao exibicao) {
        this.exibicao = exibicao;
    }

    public LocalDate getData() {
        return data;
    }

//    public void setData(LocalDate data) {
//        this.data = data;
//    }
    //acho que não tem mais sentido alterar aqui, já que é sempre a data do dia em que foi criada

    public void adicionarIngresso(Ingresso ingresso) {
        this.ingressoList.add(ingresso);
        atualizarIngressosDisponiveis();
    }

    public BigDecimal getPrecoTotal() {
        BigDecimal precoTotal = BigDecimal.ZERO;
        for (Ingresso ingresso : ingressoList) {
            precoTotal = precoTotal.add(ingresso.getPreco());
        }
        return precoTotal;
    }

    private void atualizarIngressosDisponiveis() {
        exibicao.setQntIngressosDisponiveis(exibicao.getQntIngressosDisponiveis() - 1);
    }

    public void cancelarVenda(){
        status = VendaStatus.CANCELADA;
    }

    public VendaStatus getStatus() {
        return status;
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

        sb.append("}");

        return sb.toString();
    }
}