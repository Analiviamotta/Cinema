package br.edu.ifsp.cinema.domain.usecases.sessao;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class ConsultarSessoesUseCase {
    private SessaoDAO sessaoDAO;

    public ConsultarSessoesUseCase(SessaoDAO sessaoDAO) {
        this.sessaoDAO = sessaoDAO;
    }

    public List<Sessao> findAll() {
        List<Sessao> sessoes = sessaoDAO.findAll();

        if (sessoes.isEmpty()) {
            throw new EntityNotFoundException("Não há sessões cadastradas");
        }
        return sessoes;
    }
}