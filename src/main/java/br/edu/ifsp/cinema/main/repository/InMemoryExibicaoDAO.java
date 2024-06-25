package br.edu.ifsp.cinema.main.repository;

import br.edu.ifsp.cinema.domain.entities.exibicao.Exibicao;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;

import java.util.*;

public class InMemoryExibicaoDAO implements ExibicaoDAO {
    private static final Map<Long, Exibicao> db = new LinkedHashMap<>();

    private static long idCont;
    @Override
    public List<Exibicao> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Exibicao create(Exibicao exibicao) {
        exibicao.setId(idCont++);
        db.put(exibicao.getId(), exibicao);
        return exibicao;
    }

    @Override
    public boolean update(Exibicao exibicao) {
        if (db.containsKey(exibicao.getId())) {
            db.put(exibicao.getId(), exibicao);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Long key) {
        return false;
    }

    @Override
    public boolean delete(Exibicao entity) {
        return false;
    }

    @Override
    public boolean isAtivo(Long key) {
        return false;
    }

    @Override
    public Optional<Exibicao> findOne(Long id){
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Exibicao> findByFilmeId(long id) {
        return List.of();
    }

    @Override
    public List<Exibicao> findBySalaId(long id) {
        return List.of();
    }


    @Override
    public boolean exibicaoExistenteNaMesmaDataHorarioSala(Exibicao exibicao) {
        for (Exibicao exib : db.values()) {
            if (exib.getSala().getId() == exibicao.getSala().getId() &&
                    exib.getHorarioData().isEqual(exibicao.getHorarioData())) {
                return true;
            }
        }
        return false;
    }
}
