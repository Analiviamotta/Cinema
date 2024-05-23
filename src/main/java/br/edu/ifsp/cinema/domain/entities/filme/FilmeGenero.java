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
}
