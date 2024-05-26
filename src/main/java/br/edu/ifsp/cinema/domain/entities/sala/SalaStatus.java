package br.edu.ifsp.cinema.domain.entities.sala;

public enum SalaStatus {

    ATIVO("Ativo"),
    INATIVO ("Inativo");

    private String label;

    SalaStatus(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}