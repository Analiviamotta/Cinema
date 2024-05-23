package br.edu.ifsp.cinema.domain.entities.filme;


public enum FilmeStatus {

    ATIVO("Ativo"),
    INATIVO ("Inativo");

    private String label;

    FilmeStatus(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}