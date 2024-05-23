package br.edu.ifsp.cinema.domain.entities.sala;

public class Sala {
    private long id;
    private int numero;
    private int numLinhas;
    private int numColunas;
    private int capacidade;
    private SalaStatus status;

    public Sala(){
        status = SalaStatus.ATIVO;
    }

    public Sala(long id, int number, int numLinhas, int numColunas, int capacidade, SalaStatus status) {
        this.id = id;
        this.numero = number;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.capacidade = capacidade;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return numero;
    }

    public void setNumber(int number) {
        this.numero = number;
    }

    public int getNumLinhas() {
        return numLinhas;
    }

    public void setNumLinhas(int numLinhas) {
        this.numLinhas = numLinhas;
    }

    public int getNumColunas() {
        return numColunas;
    }

    public void setNumColunas(int numColunas) {
        this.numColunas = numColunas;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public SalaStatus getStatus() {
        return status;
    }

    public void setStatus(SalaStatus status) {
        this.status = status;
    }

    public void inativarSala(){
        this.status = SalaStatus.INATIVO;
    }

    public void ativarSala(){
        this.status = SalaStatus.ATIVO;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", numero=" + numero +
                ", numLinhas=" + numLinhas +
                ", numColunas=" + numColunas +
                ", capacidade=" + capacidade +
                ", status=" + status +
                '}';
    }
}
