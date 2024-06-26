package br.edu.ifsp.cinema.domain.entities.ingresso;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.assento.Assento;

import java.math.BigDecimal;

public class Ingresso {
    private Long id;
    private Assento assento;
    private Exibicao exibicao;
    private BigDecimal preco;
    private boolean vendido;

    public Ingresso(Assento assento, Exibicao exibicao, BigDecimal preco) {
        this.assento = assento;
        this.exibicao = exibicao;
        this.preco = preco;
        this.vendido = false;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public Object getFilme() {
        return exibicao.getFilme();
    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "id=" + id +
                ", assento=" + (assento != null ? assento.toString() : "null") +
                ", sessaoExibicao=" + (exibicao != null ? exibicao.toString() : "null") +
                ", preco=" + preco +
                '}';
    }

    // colocar exibição


}
