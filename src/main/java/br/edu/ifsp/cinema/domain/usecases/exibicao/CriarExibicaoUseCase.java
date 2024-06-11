package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.entities.sessao.SessaoDiaSemana;
import br.edu.ifsp.cinema.domain.entities.sessao.SessaoStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
import br.edu.ifsp.cinema.domain.usecases.sessao.SessaoDAO;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


// sala ativa, filme ativo, data posterior à atual, exibicao ativa,
// qtd de ingresso é setado de acordo com a sala
// não pode adicionar uma exibição na mesma data, mesmo horário e sala
// não pode adicionar uma exibição que o duration de outra exibição dá conflito
// duracao deve ser maior que zero


public class CriarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;
    private SalaDAO salaDAO;
    //private SessaoDAO sessaoDAO;

    public CriarExibicaoUseCase(ExibicaoDAO exibicaoDAO, SalaDAO salaDAO, SessaoDAO sessaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
        this.salaDAO = salaDAO;
        //this.sessaoDAO = sessaoDAO;
    }

    public Exibicao insert(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Optional<Sala> sala = salaDAO.findOne(exibicao.getSala().getId());
        if (sala.isEmpty()) {
            throw new EntityNotFoundException("Sala não encontrada");
        }

        if (sala.get().getStatus() != SalaStatus.ATIVO) {
            throw new InactiveObjectException("A sala selecionada não está ativa");
        }

        Optional<Sessao> sessao = sessaoDAO.findOne(exibicao.getSessao().getId());
        if (sessao.isEmpty()) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }
        if (sessao.get().getStatus() != SessaoStatus.ATIVO){
            throw new InactiveObjectException("A sessão não está ativa");
        }

        if (exibicao.getQntIngressosDisponiveis() < 0) {
            throw new IllegalArgumentException("A quantidade de ingressos disponíveis não pode ser negativa");
        }

        if (exibicaoJaExistente(exibicao)) {
            throw new IllegalArgumentException("Já existe uma exibição para esta sala, data, horário e dia da semana.");
        }

        return exibicaoDAO.create(exibicao);
    }

    public boolean exibicaoJaExistente(Exibicao exibicao) {
        Sessao sessao = exibicao.getSessao();
        LocalDate dataInicio = sessao.getDataInicio();
        LocalDate dataFim = sessao.getDataFim();
        List<LocalTime> horarios = sessao.getHorarios();
        List<SessaoDiaSemana> diasSemana = sessao.getDiaSemana();

        for (SessaoDiaSemana diaSemana : diasSemana) {
            for (LocalTime horario : horarios) {
                List<Exibicao> exibicoesConflitantes = exibicaoDAO.findBySalaAndDataHoraAndDiaSemana(
                        exibicao.getSala().getId(),
                        dataInicio,
                        dataFim,
                        horario,
                        diaSemana
                );

                if (!exibicoesConflitantes.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
