package br.edu.ifsp.cinema.domain.usecases.exibicao;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;
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

// já coloquei algumas dessas verificações em ExibicaoInputRequestValidator
// O QUE FALTA: Negócio do DURATION e QTD DE INGRESSO SETADA


public class CriarExibicaoUseCase {
    private ExibicaoDAO exibicaoDAO;
    private SalaDAO salaDAO;

    public Exibicao insert(Exibicao exibicao) {
        Validator<Exibicao> validator = new ExibicaoInputRequestValidator();
        Notification notification = validator.validate(exibicao);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        if (exibicaoDAO.exibicaoExistenteNaMesmaDataHorarioSala(exibicao)) {
            throw new IllegalArgumentException("Já existe uma exibição na mesma data, horário e sala");
        }

        return exibicaoDAO.create(exibicao);
    }
}
