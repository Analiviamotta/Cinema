package br.edu.ifsp.cinema.domain.entities.filme;

public enum FilmeGenero {
    ACAO ("Ação"),
    AVENTURA("Aventura"),
    TERROR("Terror"),
    COMEDIA("Comédia"),
    DRAMA("Drama"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    DOCUMENTARIO("Documentário");

    private String label;

    FilmeGenero(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static FilmeGenero fromString(String label) {
        for (FilmeGenero genero : FilmeGenero.values()) {
            if (genero.label.equalsIgnoreCase(label)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("No enum constant for label: " + label);
    }
}
