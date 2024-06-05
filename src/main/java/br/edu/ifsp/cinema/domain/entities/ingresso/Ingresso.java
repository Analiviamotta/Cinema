package br.edu.ifsp.cinema.domain.entities.ingresso;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;

import java.math.BigDecimal;

public class Ingresso {
    private Long id;
    private Assento assento;
    private Sessao sessaoExibicao;
    private BigDecimal preco;

    public Ingresso(Assento assento, Sessao sessaoExibicao, BigDecimal preco) {
        this.assento = assento;
        this.sessaoExibicao = sessaoExibicao;
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public Assento getAssento() {
        return assento;
    }

    public void setAssento(Assento assento) {
        this.assento = assento;
    }

    public Sessao getSessaoExibicao() {
        return sessaoExibicao;
    }

    public void setSessaoExibicao(Sessao sessaoExibicao) {
        this.sessaoExibicao = sessaoExibicao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(String precoStr) {
            try {
                this.preco = new BigDecimal(precoStr);
            }
            catch (NumberFormatException e) {
                //formato errado deve ser 00 ou 00.00
                System.err.println("Formato de valor errado: " + e.getMessage());
            }

    }
    @Override
    public String toString() {
        return "Ingresso{" +
                "id=" + id +
                ", assento=" + (assento != null ? assento.toString() : "null") +
                ", sessaoExibicao=" + (sessaoExibicao != null ? sessaoExibicao.toString() : "null") +
                ", preco=" + preco +
                '}';
    }



    public Object getFilme() {
        return sessaoExibicao.getFilme();
    }
}
