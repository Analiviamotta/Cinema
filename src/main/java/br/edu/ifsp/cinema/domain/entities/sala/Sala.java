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

    public Sala() {
        status = SalaStatus.ATIVO;
        this.assentoList = new ArrayList<>();
    }

    public Sala(int numero, int numLinhas, int numColunas, int capacidade) {
        this.numero = numero;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.capacidade = capacidade;
        this.assentoList = new ArrayList<>();
        this.status = SalaStatus.ATIVO;

        if (numLinhas * numColunas > capacidade) {
            throw new IllegalArgumentException("Número de assentos excede a capacidade da sala.");
        }

        criarAssentos(numLinhas, numColunas);
    }


    public Sala(int numero, int numLinhas, int numColunas, int capacidade, List<Assento> assentoList) {
        this.numero = numero;
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.capacidade = capacidade;
        this.assentoList = new ArrayList<>();
        this.status = SalaStatus.ATIVO;

        if (numLinhas * numColunas > capacidade) {
            throw new IllegalArgumentException("Número de assentos excede a capacidade da sala.");
        }

        criarAssentos(numLinhas, numColunas);
    }

    private void criarAssentos(int numLinhas, int numColunas) {
        List<Assento> assentosInvalidos = new ArrayList<>();
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                Assento assento = new Assento(linha, coluna);
                if (verificarAssentoValido(assento)) {
                    assentoList.add(assento);
                } else {
                    assentosInvalidos.add(assento);
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void inativarSala() {
        this.status = SalaStatus.INATIVO;
    }

    public void ativarSala() {
        this.status = SalaStatus.ATIVO;
    }

    public boolean verificarAssentoValido(Assento assento) {
        return assento.getLinha() >= 0 && assento.getLinha() < numLinhas &&
                assento.getColuna() >= 0 && assento.getColuna() < numColunas;
    }

    public void addAssento(Assento novoAssento) {
        if (!verificarAssentoValido(novoAssento)) {
            throw new IllegalArgumentException("O assento não é válido para esta sala");
        }
        assentoList.add(novoAssento);
    }

    public void removeAssento(Assento assentoParaExcluir) {
        assentoList.remove(assentoParaExcluir);
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
