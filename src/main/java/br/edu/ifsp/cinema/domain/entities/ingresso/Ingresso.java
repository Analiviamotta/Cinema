package br.edu.ifsp.cinema.domain.entities.ingresso;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import java.math.BigDecimal;

public class Ingresso {
    private Long id;
    private Assento assento;
    private Exibicao sessaoExibicao;
    private BigDecimal preco;

    public Ingresso(Assento assento, Exibicao sessaoExibicao, BigDecimal preco) {
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

    // colocar exibição


    public Object getFilme() {
        return sessaoExibicao.getFilme();
    }
}
