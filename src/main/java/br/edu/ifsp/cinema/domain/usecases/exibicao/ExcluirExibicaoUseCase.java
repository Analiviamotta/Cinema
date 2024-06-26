// nao pode ser posterior à data atual (não pode ser realizada já)
// usar o Validator já
// excluir precisa estar inativa

package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.InactiveObjectException;

import java.time.LocalDateTime;
import java.util.Optional;

public class ExcluirExibicaoUseCase {
    private static ExibicaoDAO exibicaoDAO;

    public ExcluirExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public boolean remove(Long id) {
        Optional<Exibicao> exibicaoOpt = exibicaoDAO.findOne(id);
        if (exibicaoOpt.isEmpty()) {
            throw new EntityNotFoundException("Exibição não encontrada");
        }
        Exibicao exibicao = exibicaoOpt.get();
        if (exibicaoDAO.isAtivo(exibicao.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma exibição ativa");
        }

        if (exibicao.getHorarioData().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível excluir uma exibição que já ocorreu");
        }

        return exibicaoDAO.deleteByKey(id);
    }

    public static boolean remove(Exibicao exibicao) {
        if (exibicao == null) {
            throw new IllegalArgumentException("A exibição informada não pode ser nula");
        }
        long id = exibicao.getId();
        Optional<Exibicao> exibicaoOpt = exibicaoDAO.findOne(id);
        if (exibicaoOpt.isEmpty()) {
            throw new EntityNotFoundException("Exibição não encontrada");
        }
        if (exibicaoDAO.isAtivo(exibicao.getId())) {
            throw new InactiveObjectException("Não é possível excluir uma exibição ativa");
        }

        if (exibicao.getHorarioData().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível excluir uma exibição que já ocorreu");
        }

        return exibicaoDAO.delete(exibicao);
    }
}