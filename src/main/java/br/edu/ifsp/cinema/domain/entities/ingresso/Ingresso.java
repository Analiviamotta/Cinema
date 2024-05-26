package br.edu.ifsp.cinema.domain.entities.ingresso;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;

public class Ingresso {
    private long id;
    private Assento assento;
    private Sessao sessaoExibicao;

    public Ingresso(long id, Assento assento, Sessao sessaoExibicao) {
        this.id = id;
        this.assento = assento;
        this.sessaoExibicao = sessaoExibicao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ingresso{");
        sb.append("id=").append(id);
        sb.append(", assento=").append(assento != null ? assento.toString() : "null");
        sb.append(", sessaoExibicao=").append(sessaoExibicao != null ? sessaoExibicao.toString() : "null");
        sb.append("}");

        return sb.toString();
    }

    // to do:
    public void imprimirIngresso(){

    }
}
