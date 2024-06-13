package br.edu.ifsp.cinema.domain.entities.exibicao;

public enum ExibicaoStatus {
    EFETUADA("Efetuada"),
    CANCELADA("Cancelada");
    private String label;

    ExibicaoStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
