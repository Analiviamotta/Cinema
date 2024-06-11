package br.edu.ifsp.cinema.domain.entities.sessao;

public enum SessaoDiaSemana {
    DOMINGO("Domingo"),
    SEGUNDA("Segunda"),
    TERCA("Terça"),
    QUARTA("Quarta"),
    QUINTA("Quinta"),
    SEXTA("Sexta"),
    SABADO("Sábado");

    private String label;

    SessaoDiaSemana(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}



