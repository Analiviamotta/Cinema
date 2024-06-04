package br.edu.ifsp.cinema.domain.entities.sessao;

public enum SessaoStatus {
    ATIVO("Ativo"),
    INATIVO ("Inativo");

    private String label;

    SessaoStatus(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
