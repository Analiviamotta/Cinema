package br.edu.ifsp.cinema.domain.entities.sessao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private Long id;
    private List<SessaoDiaSemana> diaSemana;
    private Duration tempoDuracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<LocalTime> horarios;
    private Filme filme;
    private SessaoStatus status;

    public Sessao(){
        status = SessaoStatus.ATIVO;
        diaSemana = new ArrayList<>();
        horarios = new ArrayList<>();
    }

    public Sessao(List<SessaoDiaSemana> diaSemana, Duration tempoDuracao, LocalDate dataInicio, LocalDate dataFim, List<LocalTime> horarios, Filme filme) {
        this.diaSemana = new ArrayList<>(diaSemana);
        this.tempoDuracao = tempoDuracao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horarios = new ArrayList<>(horarios);
        this.filme = filme;
        this.status = SessaoStatus.ATIVO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<SessaoDiaSemana> getDiaSemana() {
        return new ArrayList<>(diaSemana);
    }

    public void setDiaSemana(List<SessaoDiaSemana> diaSemana) {
        this.diaSemana = new ArrayList<>(diaSemana);
    }

    public Duration getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempo(Duration tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<LocalTime> getHorarios() {
        return new ArrayList<>(horarios);
    }

    public void setHorarios(List<LocalTime> horarios) {
        this.horarios = new ArrayList<>(horarios);
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public SessaoStatus getStatus() {
        return status;
    }

    public void setStatus(SessaoStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sessao{");
        sb.append("id=").append(id);
        sb.append(", diaSemana=").append(diaSemana);
        sb.append(", tempoDuracao=").append(tempoDuracao);
        sb.append(", dataInicio=").append(dataInicio);
        sb.append(", dataFim=").append(dataFim);
        sb.append(", horarios=").append(horarios);
        sb.append(", status=").append(status);
        sb.append(", filme=").append(filme != null ? filme.toString() : "null");
        sb.append('}');
        return sb.toString();
    }
}

