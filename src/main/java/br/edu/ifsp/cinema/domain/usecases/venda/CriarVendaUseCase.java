package br.edu.ifsp.cinema.domain.usecases.venda;

import br.edu.ifsp.cinema.domain.usecases.exibicao.ConsultarExibicaoUseCase;
import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;


import java.util.List;

// nao pode vender mais ingressos do que estão disponíveis
// não pode vender um ingresso que está vendido
// FEITO

public class CriarVendaUseCase {
    private VendaDAO vendaDAO;
    private ConsultarExibicaoUseCase consultarExibicaoUseCase;
    private VendaInputRequestValidator validator = new VendaInputRequestValidator();

    public CriarVendaUseCase(
            VendaDAO vendaDAO,
            ConsultarExibicaoUseCase consultarExibicaoUseCase) {
        this.vendaDAO = vendaDAO;
        this.consultarExibicaoUseCase = consultarExibicaoUseCase;
    }

    public Venda criarVenda(Long exibicaoId, List<Ingresso> ingressos) {
        Exibicao exibicao = consultarExibicaoUseCase.findOne(exibicaoId)
                .orElseThrow(() -> new EntityNotFoundException("Exibição com id " + exibicaoId + " não encontrada"));

        if (ingressos.isEmpty()) {
            throw new IllegalArgumentException("Lista de Ingressos não pode estar vazia");
        }

        if (ingressos.size() > exibicao.getQntIngressosDisponiveis()) {
            throw new IllegalArgumentException("Número de ingressos não pode exceder a quantidade disponível.");
        }


        // eu extrai essa verificacao daqui pra entidade venda, e por isso ja não é necessario ter essa verificacao aqui

//        for (Ingresso ingresso : ingressos) {
//            if (ingresso.isVendido()) {
//                throw new IllegalArgumentException("Não é possível vender um ingresso que já está vendido.");
//            }
//            ingresso.setVendido(true);
//        }

        Venda venda = new Venda(exibicao, ingressos.toArray(new Ingresso[0]));

        // Validar a venda
        Notification notification = validator.validate(venda);
        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Long vendaId = vendaDAO.create(venda).getId();
        return vendaDAO.findOne(vendaId).orElseThrow(() -> new EntityNotFoundException("Venda com id " + vendaId + " não encontrada"));
    }
}
