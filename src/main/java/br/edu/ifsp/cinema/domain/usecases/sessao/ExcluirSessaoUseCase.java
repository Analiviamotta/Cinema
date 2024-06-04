package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.time.LocalDateTime;
import java.util.Optional;

public class ExcluirSessaoUseCase {
    private SessaoDAO sessaoDAO;

    public ExcluirSessaoUseCase(SessaoDAO sessaoDAO) {
        this.sessaoDAO = sessaoDAO;
    }

    public boolean remove(Long sessaoId) {
        Optional<Sessao> sessaoOpt = sessaoDAO.findOne(sessaoId);
        if (sessaoOpt.isEmpty()) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }
        Sessao sessao = sessaoOpt.get();
        if (sessaoDAO.isAtivo(sessao.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma sessão ativa");
        }
        if (sessao.getDataFim().atTime(sessao.getHorarios().get(0)).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível excluir uma sessão já realizada");
        }

        return sessaoDAO.deleteByKey(sessaoId);
    }

    public boolean remove(Sessao sessao) {
        if (sessao == null) {
            throw new IllegalArgumentException("A sessão informada não pode ser nula");
        }
        long id = sessao.getId();
        Optional<Sessao> sessaoOpt = sessaoDAO.findOne(id);
        if (sessaoOpt.isEmpty()) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }
        if (sessaoDAO.isAtivo(sessao.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma sessão ativa");
        }
        if (sessao.getDataFim().atTime(sessao.getHorarios().get(sessao.getHorarios().size() - 1)).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível editar uma sessão já realizada");
        }

        return sessaoDAO.delete(sessao);
    }
}
