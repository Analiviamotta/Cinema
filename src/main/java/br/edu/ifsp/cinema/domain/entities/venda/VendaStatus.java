package br.edu.ifsp.cinema.domain.entities.venda;

public enum VendaStatus {
    EFETUADA("Efetuada"),
    CANCELADA("Cancelada");
    private String label;

    VendaStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
