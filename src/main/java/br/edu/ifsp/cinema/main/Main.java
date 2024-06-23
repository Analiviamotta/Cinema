package br.edu.ifsp.cinema.main;

import br.edu.ifsp.cinema.application.view.HelloApplication;
import br.edu.ifsp.cinema.main.repository.sqlite.util.DatabaseBuilder;


public class Main {
    public static void main(String[] args) {
        initializeUseCase();
        createDatabase();
        HelloApplication.main(args);  // mudar para a view root/home do projeto
    }

    private static void createDatabase(){
        DatabaseBuilder db = new DatabaseBuilder();
        db.buildDatabaseIfMissing();
    }

    private static void initializeUseCase(){
        //iniciar agora aqui os use cases dps q eu fizer os dao do sqlite e a aninha fizer as telas

    }

}
