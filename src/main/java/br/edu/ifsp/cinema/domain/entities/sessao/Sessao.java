package br.edu.ifsp.cinema.domain.entities.sessao;

public class Sessao {
    private long id;
    private SessaoDiaSemana diaSemana;
    private int hora;

    public Sessao(){
    }

    public Sessao(long id, SessaoDiaSemana diaSemana, int hora) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.hora = hora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SessaoDiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(SessaoDiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "id=" + id +
                ", diaSemana=" + diaSemana +
                ", hora=" + hora +
                '}';
    }
}
