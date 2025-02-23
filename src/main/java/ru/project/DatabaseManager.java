package ru.project;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private int numberOfLetters = 0;

    public DatabaseManager() {
        try (Connection connection = DriverManager.getConnection(DB_URL, "postgres", "postgres");
             Statement stmt = connection.createStatement()) {
            String createTableSQL = """
                   CREATE TABLE IF NOT EXISTS wordsForGallows (
                        id SERIAL PRIMARY KEY,
                        word VARCHAR(255) NOT NULL
                            );
                   """;
            String addWords = """
                    INSERT INTO wordsForGallows (word) VALUES
                    ('абрикос'), ('бабочка'), ('верблюд'), ('гвоздика'),
                    ('деревня'), ('ежевика'), ('жасмин'), ('зебра'),
                    ('игрушка'), ('йогурт'), ('капуста'), ('лампа'),
                    ('малина'), ('небо'), ('огурец'), ('помидор'),
                    ('речка'), ('самолет'), ('тумбочка'), ('улитка'),
                    ('фонарь'), ('хоккей'), ('цветок'), ('черепаха'),
                    ('шалаш'), ('щука'), ('эскимо'), ('юла'),
                    ('яблоко'), ('барсук'), ('виноград'), ('горка'),
                    ('дождь'), ('ежедневник'), ('жираф'), ('забор'),
                    ('искра'), ('календарь'), ('лимон'), ('морковь'),
                    ('носок'), ('облако'), ('палатка'), ('ракета'),
                    ('собака'), ('трактор'), ('узор'), ('флаг'),
                    ('хлеб'), ('чайник');
                    """;
            stmt.execute(createTableSQL);
            stmt.executeUpdate(addWords);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String secretWord() {
        String query = "SELECT word FROM wordsForGallows ORDER BY RANDOM() LIMIT 1;";
        String word = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, "postgres", "postgres");
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                word = resultSet.getString("word");
                numberOfLetters = word.length();
            } else {
                throw new RuntimeException("Таблица wordsForGallows пуста.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return word;
    }
    public int getNumberOfLetters() {
        return numberOfLetters;
    }
}
