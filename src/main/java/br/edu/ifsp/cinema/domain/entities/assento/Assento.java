package br.edu.ifsp.cinema.domain.entities.assento;

public class Assento {
    private Long id;
    private int coluna;
    private int linha;

    public Assento() {
    }

    public Assento(int coluna, int linha) {
        this.coluna = coluna;
        this.linha = linha;
    }



    public long getId() {
        return id;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Assento{");
        sb.append("id=").append(id);
        sb.append(", coluna=").append(coluna);
        sb.append(", linha=").append(linha);
        sb.append("}");

        return sb.toString();
    }

    public void setId(long id) {
        this.id = id;
    }
}
