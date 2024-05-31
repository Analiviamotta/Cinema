package br.edu.ifsp.cinema.application.main.repository;

import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import br.edu.ifsp.cinema.domain.entities.venda.VendaDAO;
import br.edu.ifsp.cinema.domain.entities.venda.VendaStatus;

import java.util.*;

public class InMemoryVendaDAO implements VendaDAO {
    private static final Map<Long, Venda> db = new LinkedHashMap<>();
    private static long idCont;

    @Override
    public Venda create(Venda venda) {
        idCont++;
        venda.setId(idCont);
        db.put(idCont, venda);
        return venda;
    }

    @Override
    public boolean update(Venda venda) {
        Long id = venda.getId();
        if (db.containsKey(id)) {
            db.replace(id, venda);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Venda venda) {
        return deleteByKey(venda.getId());
    }

    @Override
    public boolean deleteByKey(Long key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Venda> findOne(Long key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Venda> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean isAtivo(Long key) {
        Optional<Venda> venda = findOne(key);
        return venda.map(value -> value.getStatus().equals(VendaStatus.EFETUADA)).orElse(false);
    }

    @Override
    public List<Ingresso> listarIngressosDaVenda(Venda venda) {
        return venda.getIngressoList();
    }
}
