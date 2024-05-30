package br.edu.ifsp.cinema.domain.entities.exibicao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;

public class Exibicao {
    private Long id;
    private Sessao sessao;
    private Filme filme;
    private Sala sala;
    private int qntIngressosDisponiveis;

    public Exibicao( Sessao sessao, Filme filme, Sala sala, int qntIngressosDisponiveis) {
        this.sessao = sessao;
        this.filme = filme;
        this.sala = sala;
        this.qntIngressosDisponiveis = qntIngressosDisponiveis;
    }

    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getQntIngressosDisponiveis() {
        return qntIngressosDisponiveis;
    }

    public void setQntIngressosDisponiveis(int qntIngressosDisponiveis) {
        this.qntIngressosDisponiveis = qntIngressosDisponiveis;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Exibicao{");
        sb.append("id=").append(id);
        sb.append(", sessao=").append(sessao != null ? sessao.toString() : "null");
        sb.append(", filme=").append(filme != null ? filme.toString() : "null");
        sb.append(", sala=").append(sala != null ? sala.toString() : "null");
        sb.append(", qntIngressosDisponiveis=").append(qntIngressosDisponiveis);
        sb.append("}");

        return sb.toString();
    }
}
