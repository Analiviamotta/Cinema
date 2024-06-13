package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.exibicao.ExibicaoStatus;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultarExibicaoCancelada{
    private ExibicaoDAO exibicaoDAO;

    public ConsultarExibicaoCancelada(ExibicaoDAO exibicaoDAO) {
        this.exibicaoDAO = exibicaoDAO;
    }

    public List<Exibicao> findAllCanceladas() {
        List<Exibicao> exibicoesCanceladasList = exibicaoDAO.findAll().stream()
                .filter(exibicao -> exibicao.getStatus() == ExibicaoStatus.CANCELADA)
                .collect(Collectors.toList());

        if (exibicoesCanceladasList.isEmpty()) {
            throw new EntityNotFoundException("Não há exibições canceladas cadastradas");
        }

        return exibicoesCanceladasList;
    }
}
