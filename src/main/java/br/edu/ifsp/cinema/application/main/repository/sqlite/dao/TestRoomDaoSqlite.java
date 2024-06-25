package br.edu.ifsp.cinema.application.main.repository.sqlite.dao;

import br.edu.ifsp.cinema.domain.entities.filme.Filme;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeGenero;
import br.edu.ifsp.cinema.domain.entities.filme.FilmeStatus;
import br.edu.ifsp.cinema.domain.entities.sala.Sala;
import br.edu.ifsp.cinema.domain.entities.sala.SalaStatus;

import java.util.List;
import java.util.Optional;

public class TestRoomDaoSqlite {

    public static void main(String[] args) {
        try {
            RoomDaoSqlite roomDao = new RoomDaoSqlite();

//            roomDao.deleteAllSalas();
//
//            List<Sala> allSalas = roomDao.findAll();
//            System.out.println("All salas: ");
//            allSalas.forEach(System.out::println);

//
            Sala sala = new Sala(180, 5, 5, 200);
            sala = roomDao.create(sala);
            System.out.println("Created: " + sala);

            Optional<Sala> foundSala = roomDao.findOne(sala.getId());
            System.out.println("Found by ID: " + foundSala);

//            // Verificar se os assentos foram inseridos corretamente
//            if (foundSala.isPresent()) {
//                Sala salaEncontrada = foundSala.get();
//                if (salaEncontrada.getAssentoList() != null && !salaEncontrada.getAssentoList().isEmpty()) {
//                    System.out.println("Assentos encontrados:");
//                    salaEncontrada.getAssentoList().forEach(System.out::println);
//                } else {
//                    System.out.println("Nenhum assento encontrado para a sala.");
//                }
//            } else {
//                System.out.println("Sala não encontrada.");
//            }
//
//            // Deletar o filme pelo ID
//            boolean deletedByKey = roomDao.deleteByKey(sala.getId());
//            System.out.println("Deleted by key: " + deletedByKey);
//
//            // Verificar se o filme foi deletado
//            foundSala = roomDao.findOne(sala.getId());
//            System.out.println("Found after deletion: " + foundSala);
//
//            Sala outraSala = new Sala(900, 4, 4, 100);
//            outraSala.setStatus(SalaStatus.ATIVO);
//            roomDao.create(outraSala);
//            System.out.println("Created: " + outraSala);
//
//            boolean deleted = roomDao.delete(outraSala);
//            System.out.println("Deleted: " + deleted);
//
//            foundSala = roomDao.findOne(outraSala.getId());
//            System.out.println("Found after deletion: " + foundSala);

            // Adicionando uma nova sala para testar
            Sala novaSala = new Sala(1, 10, 10, 100);
            novaSala.setStatus(SalaStatus.ATIVO);
            roomDao.create(novaSala);

            // Testar isInExibicao
            if (roomDao.isInExibicao(novaSala.getId())) {
                throw new IllegalArgumentException("Não é possível criar um filme que está em uma exibição");
            }

//            // Chamar o método para buscar todas as salas
//            List<Sala> allSalas = roomDao.findAll();
//            System.out.println("All salas: ");
//            allSalas.forEach(System.out::println);
//
//
//            // Testar isAtivo
//            Sala salaAtiva = new Sala(100, 2, 3, 50);
//            salaAtiva.setStatus(SalaStatus.ATIVO);
//            roomDao.create(salaAtiva);
//            System.out.println("Created: " + salaAtiva);
//
//            boolean isAtivo = roomDao.isAtivo(salaAtiva.getId());
//            System.out.println("Is Ativo: " + isAtivo);
//
//
//            salaAtiva.setCapacidade(80);
//            boolean updated = roomDao.update(salaAtiva);
//            System.out.println("Updated: " + updated);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
