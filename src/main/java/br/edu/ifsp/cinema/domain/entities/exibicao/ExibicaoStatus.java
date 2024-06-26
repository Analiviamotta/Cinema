package br.edu.ifsp.cinema.domain.entities.exibicao;

import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;

public enum ExibicaoStatus {
    EFETUADA("Efetuada"),
    CANCELADA("Cancelada");
    private String label;

    ExibicaoStatus(String label) {
        this.label = label;
    }

    public static ExibicaoStatus fromString(String label) {
        for (ExibicaoStatus exibicaoStatus : ExibicaoStatus.values()) {
            if (exibicaoStatus.label.equalsIgnoreCase(label)) {
                return exibicaoStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant for label: " + label);
    }

    @Override
    public String toString() {
        return label;
    }



}
