package br.edu.ifsp.cinema.domain.entities.sala;

import br.edu.ifsp.cinema.domain.entities.assento.Assento;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;

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


    public Sala(int numero, int numLinhas, int numColunas, int capacidade, List<Assento> assentos) {
        this.numero = numero;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.capacidade = capacidade;
        this.assentoList = new ArrayList<>();

        List<Assento> assentosInvalidos = new ArrayList<>();

        for (Assento assento : assentos) {
            if (!verificarAssentoValido(assento)) {
                assentosInvalidos.add(assento);
            } else {
                this.assentoList.add(assento);
            }
        }
        if (!assentosInvalidos.isEmpty()) {
            throw new IllegalArgumentException("Os seguintes assentos são inválidos para esta sala: " + assentosInvalidos);
        }

        this.status = SalaStatus.ATIVO;
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
        if (!assentoList.isEmpty()) {
            throw new IllegalStateException("Não é possível alterar o número de linhas após a adição de assentos à sala.");
        }
        this.numLinhas = numLinhas;
    }

    public int getNumColunas() {
        return numColunas;
    }

    public void setNumColunas(int numColunas) {
        if (!assentoList.isEmpty()) {
            throw new IllegalStateException("Não é possível alterar o número de colunas após a adição de assentos à sala.");
        }
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
        return new ArrayList<>(assentoList);
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

    public boolean verificarAssentoValido(Assento assento) {
        return assento.getLinha() >= 1 && assento.getLinha() <= numLinhas &&
                assento.getColuna() >= 1 && assento.getColuna() <= numColunas;
    }

    @Override
    public String toString() {
        StringBuilder assentosString = new StringBuilder();
        for (Assento assento : assentoList) {
            assentosString.append(assento.toString()).append(", ");
        }

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
