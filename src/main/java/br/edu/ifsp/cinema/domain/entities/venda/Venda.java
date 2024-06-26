package br.edu.ifsp.cinema.domain.entities.venda;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
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

    public Venda(List<Ingresso> ingressoList, Exibicao exibicao, LocalDate data, VendaStatus status) {
        this.ingressoList = ingressoList;
        this.exibicao = exibicao;
        this.data = data;
        this.status = status;
    }

    public Venda(Long id, Exibicao exibicao, Ingresso... ingressos) {
        this.id = id;
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
        if(ingresso.isVendido()) {
            throw new IllegalArgumentException("Não é possível vender um ingresso que já está vendido.");
        }
        ingresso.setVendido(true);
        this.ingressoList.add(ingresso);
        atualizarIngressosDisponiveis();
    }

    private void atualizarIngressosDisponiveis() {
        exibicao.setQntIngressosDisponiveis(exibicao.getQntIngressosDisponiveis() - 1);
    }

    public BigDecimal getPrecoTotal() {
        BigDecimal precoTotal = BigDecimal.ZERO;
        for (Ingresso ingresso : ingressoList) {
            precoTotal = precoTotal.add(ingresso.getPreco());
        }
        return precoTotal;
    }

    public void cancelarVenda(){
        status = VendaStatus.CANCELADA;
    }

    public VendaStatus getStatus() {
        return status;
    }

    public Filme getFilme(){
        return exibicao.getFilme();
    }

    public Sala getSala(){
        return exibicao.getSala();
    }
    public Duration getDuracao(){
        return exibicao.getTempoDuracao();
    }

    public int getNumeroDeIngressoDisponiveis(){
        return exibicao.getQntIngressosDisponiveis();
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