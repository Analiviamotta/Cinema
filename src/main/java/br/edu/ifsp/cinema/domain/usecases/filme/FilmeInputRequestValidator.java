package br.edu.ifsp.cinema.domain.usecases.filme;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.usecases.utils.Notification;
import br.edu.ifsp.cinema.domain.usecases.utils.Validator;

import java.util.Collections;

public class FilmeInputRequestValidator extends Validator<Filme> {
        @Override
        public Notification validate(Filme filme) {
            Notification notification = new Notification();

            if(filme == null){
                notification.addError("Filme is null");
                return notification;
            }

//            if(nullOrEmpty(Collections.singleton(filme.getNome()))) {
//                notification.addError("Nome do filme is null or empty");
//            }
//
            if(nullOrEmpty(Collections.singleton(filme.getGenero()))) {
                notification.addError("Genero do filme is null or empty");
            }
            if(nullOrEmpty(Collections.singleton(filme.getSinopse()))) {
                notification.addError("Sinopse do filme is null or empty");
            }
            if(nullOrEmpty(Collections.singleton(filme.getClassificacaoIndicativa()))) {
                notification.addError("Classificação indicativa do filme is null or empty");
            }

            return notification;
        }
}

