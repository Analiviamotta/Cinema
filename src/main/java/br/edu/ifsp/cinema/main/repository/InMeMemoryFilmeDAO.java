package br.edu.ifsp.cinema.main.repository;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.usecases.exibicao.ExibicaoDAO;
import br.edu.ifsp.cinema.domain.usecases.filme.FilmeDAO;

import java.time.LocalDateTime;
import java.util.*;

public class InMeMemoryFilmeDAO implements FilmeDAO {
    private static final Map<Long, Filme> db = new LinkedHashMap<>();
    //mantém a ordem de inserção
    private static long idCont;
    private ExibicaoDAO exibicaoDAO;

    @Override
    public Optional<Filme> findByTitulo(String titulo) {
        return db.values().stream()
                .filter(filme -> filme.getTitulo().equals(titulo))
                .findAny();
    }

    @Override
    public boolean isInExibicao(long filmeId) {
        return exibicaoDAO.findByFilmeId(filmeId).stream()
                .anyMatch(exibicao -> exibicao.getHorarioData().isAfter(LocalDateTime.now()));
    }

    @Override
    public Filme create(Filme filme) {
        idCont++;
        filme.setId(idCont);
        db.put(idCont, filme);
        return filme;
    }


    @Override
    public Optional<Filme> findOne(Long id) {
        if(db.containsKey(id))
            return Optional.of(db.get(id));
        return Optional.empty();
    }

    @Override
    public List<Filme> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Filme filme) {
        Long id = filme.getId();
        if(db.containsKey(id)) {
            db.replace(id, filme);
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
    public boolean delete(Filme filme) {
        return deleteByKey(filme.getId());
    }

    @Override
    public boolean isAtivo(Long id) {
        Optional<Filme> filme = findOne(id);
        return filme.map(value -> value.getStatus().equals(FilmeStatus.ATIVO)).orElse(false);
    }

}
