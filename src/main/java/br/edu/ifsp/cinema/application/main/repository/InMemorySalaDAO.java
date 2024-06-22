package br.edu.ifsp.cinema.application.main.repository;

import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;
import br.edu.ifsp.cinema.domain.usecases.sala.SalaDAO;

import java.time.LocalDateTime;
import java.util.*;

public class InMemorySalaDAO implements SalaDAO {

    private static final Map<Long, Sala> db = new LinkedHashMap<>();
    //mantém a ordem de inserção
    private static long idCont;
    private ExibicaoDAO exibicaoDAO;

    @Override
    public Optional<Sala> findByNumber(int number) {
        return db.values().stream()
                .filter(sala -> sala.getNumber() == number)
                .findAny();
                // se nao encontrar retorna Optional.Empty
    }

    @Override
    public boolean isInExibicao(long salaId) {
        return exibicaoDAO.findBySalaId(salaId).stream()
                .anyMatch(exibicao -> exibicao.getHorarioData().isAfter(LocalDateTime.now()));
    }

    @Override
    public Sala create(Sala sala) {
        idCont++;
        sala.setId(idCont);
        db.put(idCont, sala);
        return sala;
    }


    @Override
    public Optional<Sala> findOne(Long id) {
        if(db.containsKey(id))
            return Optional.of(db.get(id));
        return Optional.empty();
    }

    @Override
    public List<Sala> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Sala sala) {
        Long id = sala.getId();
        if(db.containsKey(id)) {
            db.replace(id, sala);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        if(db.containsKey(id)){
            db.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Sala sala) {
        return deleteByKey(sala.getId());
    }

    @Override
    public boolean isAtivo(Long id) {
        Optional<Sala> sala = findOne(id);
        return sala.map(value -> value.getStatus().equals(SalaStatus.ATIVO)).orElse(false);
    }
}
