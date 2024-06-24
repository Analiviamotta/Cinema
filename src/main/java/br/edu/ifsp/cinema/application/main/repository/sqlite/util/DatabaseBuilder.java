package br.edu.ifsp.cinema.application.main.repository.sqlite.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.createStatement()) {
            statement.execute(createMovie());
            statement.execute(createRoom());
            statement.execute(createSeat());
            statement.execute(createExhibition());
            statement.execute(createSale());
            statement.execute(createTicket());
            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String createMovie(){
        return """
                CREATE TABLE Movie (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title VARCHAR(255),
                    genre VARCHAR(255),
                    synopsis VARCHAR(255),
                    parental_rating VARCHAR(255),
                    status VARCHAR(255)
                );
                """;
    }

    private String createRoom(){
        return """
                CREATE TABLE Room (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    number INT,
                    line_num INT,
                    column_num INT,
                    capacity INT,
                    status VARCHAR(255)
                );
                """;
    }


    private String createSeat(){
        return """
                CREATE TABLE Seat (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    column INT,
                    line INT,
                    room_id INT,
                    FOREIGN KEY (room_id) REFERENCES Room(id)
                );
                """;
    }

    private String createExhibition
            (){
        return """
                CREATE TABLE Exhibition (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    date_time DATETIME,
                    duration INTEGER,
                    tickets_number INT,
                    status BOOLEAN,
                    movie_id INT,
                    room_id INT,
                    FOREIGN KEY (movie_id) REFERENCES Movie(id),
                    FOREIGN KEY (room_id) REFERENCES Room(id)
                );
                """;
    }

    private String createSale
            (){
        return """
                CREATE TABLE Sale (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sale_date DATE,
                    status BOOLEAN,
                    exhibition_id INT,
                    FOREIGN KEY (exhibition_id) REFERENCES Exhibition(id)
                );
                """;
    }

    private String createTicket
            (){
        return """
                CREATE TABLE Ticket (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    price FLOAT,
                    is_sold BOOLEAN,
                    seat_id INT,
                    sale_id INT,
                    exhibition_id INT,
                    FOREIGN KEY (seat_id) REFERENCES Seat(id),
                    FOREIGN KEY (sale_id) REFERENCES Sale(id),
                    FOREIGN KEY (exhibition_id) REFERENCES Exhibition(id)
                );
                """;
    }
}