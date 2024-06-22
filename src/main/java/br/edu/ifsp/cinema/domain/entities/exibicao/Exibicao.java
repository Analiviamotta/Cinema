package br.edu.ifsp.cinema.domain.entities.exibicao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;

import java.time.Duration;
import java.time.LocalDateTime;

public class Exibicao {
    private Long id;
    private Sala sala;
    private Filme filme;
    private LocalDateTime horarioData;
    private Duration tempoDuracao;
    private int qntIngressosDisponiveis;
    private ExibicaoStatus status;

    public Exibicao(){
        status = ExibicaoStatus.EFETUADA;
    }

    public Exibicao(Sala sala, Filme filme, LocalDateTime horarioData, Duration tempoDuracao, int qntIngressosDisponiveis) {
        this.sala = sala;
        this.filme = filme;
        this.horarioData = horarioData;
        this.tempoDuracao = tempoDuracao;
        this.qntIngressosDisponiveis = qntIngressosDisponiveis;
        this.status = ExibicaoStatus.EFETUADA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public LocalDateTime getHorarioData() {
        return horarioData;
    }

    public void setHorarioData(LocalDateTime horarioData) {
        this.horarioData = horarioData;
    }

    public Duration getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(Duration tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public int getQntIngressosDisponiveis() {
        return qntIngressosDisponiveis;
    }

    public void setQntIngressosDisponiveis(int qntIngressosDisponiveis) {
        this.qntIngressosDisponiveis = qntIngressosDisponiveis;
    }

    public ExibicaoStatus getStatus() {
        return status;
    }

    public void setStatus(ExibicaoStatus status) {
        this.status = status;
    }

    public void cancelarExibicao(){
        this.status = ExibicaoStatus.CANCELADA;
    }

    public void efetuarExibicao(){
        this.status = ExibicaoStatus.EFETUADA;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Exibicao{");
        sb.append("id=").append(id);
        sb.append(", sala=").append(sala != null ? sala.toString() : "null");
        sb.append(", filme=").append(filme != null ? filme.toString() : "null");
        sb.append(", data e horário=").append(horarioData);
        sb.append(", duracao=").append(tempoDuracao);
        sb.append(", qntIngressosDisponiveis=").append(qntIngressosDisponiveis);
        sb.append("}");

        return sb.toString();
    }
}
