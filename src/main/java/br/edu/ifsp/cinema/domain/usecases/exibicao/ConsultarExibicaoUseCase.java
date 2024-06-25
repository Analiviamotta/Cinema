package br.edu.ifsp.cinema.domain.usecases.exibicao;


import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultarExibicaoUseCase {

    private static ExibicaoDAO exibicaoDAO;

    public ConsultarExibicaoUseCase(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public Optional<Exibicao> findOne(Long exibicaoId) {
        return exibicaoDAO.findOne(exibicaoId);
    }

    public static List<Exibicao> findAll() {
        List<Exibicao> exibicoesAtivasList = exibicaoDAO.findAll().stream()
                .filter(exibicao -> exibicao.getStatus() != ExibicaoStatus.CANCELADA)
                .collect(Collectors.toList());
        if(exibicoesAtivasList.isEmpty()) {
            throw new EntityNotFoundException("Não há exibicoes cadastradas não canceladas");
        }
        return exibicoesAtivasList;
    }
}
