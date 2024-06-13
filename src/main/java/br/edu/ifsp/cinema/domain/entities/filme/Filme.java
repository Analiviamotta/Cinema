package br.edu.ifsp.cinema.domain.entities.filme;

public class Filme {
    private Long id;
    private String titulo;
    private FilmeGenero genero;
    private String sinopse;
    private String classificacaoIndicativa;
    private FilmeStatus status;

    public Filme(){
        status = FilmeStatus.ATIVO;
    }

    public Filme(String titulo, FilmeGenero genero, String sinopse, String classificacaoIndicativa) {
        this.titulo = titulo;
        this.genero = genero;
        this.sinopse = sinopse;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.status = FilmeStatus.ATIVO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FilmeGenero getGenero() {
        return genero;
    }

    public void setGenero(FilmeGenero genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(String classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public FilmeStatus getStatus() {
        return status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setStatus(FilmeStatus status) {
        this.status = status;
    }

    public void inativarFilme(){
        this.status = FilmeStatus.INATIVO;
    }

    public void ativarFilme(){
        this.status = FilmeStatus.ATIVO;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                "titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", sinopse='" + sinopse + '\'' +
                ", classificacaoIndicativa='" + classificacaoIndicativa + '\'' +
                ", status=" + status +
                '}';
    }


}
