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
    private Assento assentos[][];

    public Sala(){
        status = SalaStatus.ATIVO;
        //this.assentoList = new ArrayList<>();
    }

    public Sala(int numero, int numLinhas, int numColunas, int capacidade, List<Assento> assentos) {
        this.numero = numero;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        for (int i = 1; i <= getNumLinhas(); i++) {
            for (int j = 1; j <= getNumColunas(); j++) {
                this.assentos[i-1][j-1] = new Assento(i, j);
            }
        }

        this.status = SalaStatus.ATIVO;
    }

//    public Sala(int numero, int numLinhas, int numColunas, int capacidade, List<Assento> assentos) {
//        this.numero = numero;
//        this.numLinhas = numLinhas;
//        this.numColunas = numColunas;
//        this.capacidade = capacidade;
//        this.assentoList = new ArrayList<>();
//
//        List<Assento> assentosInvalidos = new ArrayList<>();
//
//        for (Assento assento : assentos) {
//            if (!verificarAssentoValido(assento)) {
//                assentosInvalidos.add(assento);
//            } else {
//                this.assentoList.add(assento);
//            }
//        }
//        if (!assentosInvalidos.isEmpty()) {
//            throw new IllegalArgumentException("Os seguintes assentos são inválidos para esta sala: " + assentosInvalidos);
//        }
//
//        this.status = SalaStatus.ATIVO;
//    }

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

//    public void setNumLinhas(int numLinhas) {
//        if (!assentoList.isEmpty()) {
//            throw new IllegalStateException("Não é possível alterar o número de linhas após a adição de assentos à sala.");
//        }
//        this.numLinhas = numLinhas;
//    }

    public int getNumColunas() {
        return numColunas;
    }

//    public void setNumColunas(int numColunas) {
//        if (!assentoList.isEmpty()) {
//            throw new IllegalStateException("Não é possível alterar o número de colunas após a adição de assentos à sala.");
//        }
//        this.numColunas = numColunas;
//    }

    public int getCapacidade() {
        return capacidade;
    }

//    public void setCapacidade(int capacidade) {
//        this.capacidade = capacidade;
//    }

    public SalaStatus getStatus() {
        return status;
    }

    public void setStatus(SalaStatus status) {
        this.status = status;
    }

    public Assento[][] getAssentos() {
        return assentos;
    }

    public Assento getAssento(int i, int j) {
        return assentos[i][j];
    }

    //    public List<Assento> getAssentoList() {
//        return new ArrayList<>(assentoList);
//    }

//    public void setAssentoList(List<Assento> assentoList) {
//        this.assentoList = assentoList;
//    }

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

//    public void addAssento(Assento novoAssento) {
//        if (!verificarAssentoValido(novoAssento)) {
//            throw new IllegalArgumentException("O assento não é válido para esta sala");
//        }
//        assentoList.add(novoAssento);
//    }
//
//    public void removeAssento(Assento assentoParaExcluir) {
//        assentoList.remove(assentoParaExcluir);
//    }




    @Override
    public String toString() {
        StringBuilder assentosString = new StringBuilder();
        for (int i = 0; i < getNumLinhas(); i++) {
            for (int j = 0; j < getNumColunas(); j++) {
                assentosString.append(assentos[i][j]);
            }
        }
//        for (Assento assento : assentoList) {
//            assentosString.append(assento.toString()).append(", ");
//        }

        if (!assentosString.isEmpty()) {
            assentosString.setLength(assentosString.length() - 2);
        }

        return "Sala{" +
                "id=" + id +
                ", numero=" + numero +
                ", numLinhas=" + numLinhas +
                ", numColunas=" + numColunas +
                ", capacidade=" + capacidade +
                ", status=" + status +
                ", assentos=[" + assentosString +
                "]}";
    }


}
