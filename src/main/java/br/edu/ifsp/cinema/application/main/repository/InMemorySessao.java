package br.edu.ifsp.cinema.application.main.repository;

import br.edu.ifsp.cinema.domain.entities.sessao.Sessao;
import br.edu.ifsp.cinema.domain.usecases.sessao.SessaoDAO;

import java.util.*;
import java.util.stream.Collectors;

public class InMemorySessao implements SessaoDAO {

    private static final Map<Long, Sessao> db = new LinkedHashMap<>();
    private static long idCont;

    @Override
    public List<Sessao> findAllByFilmeId(Long filmeId) {
        return db.values().stream()
                .filter(sessao -> Long.valueOf(sessao.getFilme().getId()).equals(filmeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sessao> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Sessao create(Sessao sessao) {
        idCont++;
        sessao.setId(idCont);
        db.put(idCont, sessao);
        return sessao;
    }

    @Override
    public boolean update(Sessao sessao) {
        Long id = sessao.getId();
        if (db.containsKey(id)) {
            db.replace(id, sessao);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Sessao> findOne(Long id) {
        if (db.containsKey(id))
            return Optional.of(db.get(id));
        return Optional.empty();
    }
}
