package br.edu.ifsp.cinema.domain.entities.sala;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private Long id;
    private int numero;
    private int numLinhas;
    private int numColunas;
    private int capacidade;
    private SalaStatus status;
    private List<Assento> assentoList;

    public Sala(){
        status = SalaStatus.ATIVO;
        this.assentoList = new ArrayList<>();
    }

    public Sala(int numero, int numLinhas, int numColunas, int capacidade, List<Assento> assentoList) {
        this.numero = numero;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.capacidade = capacidade;
        this.assentoList = (assentoList != null) ? assentoList : new ArrayList<>();
        this.status = SalaStatus.ATIVO;
    }

    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

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

    public List<Assento> getAssentoList() {
        return assentoList;
    }

    public void setAssentoList(List<Assento> assentoList) {
        this.assentoList = assentoList;
    }

    public void inativarSala(){
        this.status = SalaStatus.INATIVO;
    }

    public void ativarSala(){
        this.status = SalaStatus.ATIVO;
    }

    @Override
    public String toString() {
        StringBuilder assentosString = new StringBuilder();
        for (Assento assento : assentoList) {
            assentosString.append(assento.toString()).append(", ");
        }

        // tira a ultima virgula e espaço
        if (assentosString.length() > 0) {
            assentosString.setLength(assentosString.length() - 2);
        }

        return "Sala{" +
                "id=" + id +
                ", numero=" + numero +
                ", numLinhas=" + numLinhas +
                ", numColunas=" + numColunas +
                ", capacidade=" + capacidade +
                ", status=" + status +
                ", assentoList=[" + assentosString +
                "]}";
    }
}
