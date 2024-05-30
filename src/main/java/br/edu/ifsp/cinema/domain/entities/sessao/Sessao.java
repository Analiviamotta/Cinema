package br.edu.ifsp.cinema.domain.entities.sessao;
import br.edu.ifsp.cinema.domain.entities.filme.Filme;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Sessao {
    private Long id;
    private SessaoDiaSemana diaSemana;
    private LocalTime tempo;
    private LocalTime hora;
    private Filme filme;

    public Sessao(){
    }

    public Sessao(SessaoDiaSemana diaSemana, LocalTime tempo, LocalTime hora, Filme filme) {
        this.diaSemana = diaSemana;
        this.tempo = tempo;
        this.hora = hora;
        this.filme = filme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SessaoDiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(SessaoDiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(String horaStr) {
        try {
           this.hora = LocalTime.parse(horaStr);
        } catch (DateTimeParseException e) {
            // formato correto hh:mm
            System.out.println("Formato de hora inválido: " + horaStr);
        }
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sessao{");
        sb.append("id=").append(id);
        sb.append(", diaSemana=").append(diaSemana);
        sb.append(", tempo=").append(tempo);
        sb.append(", hora=").append(hora);
        sb.append(", filme=").append(filme != null ? filme.toString() : "null");
        sb.append('}');
        return sb.toString();
    }
}

