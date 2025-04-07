package ru.project;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:hangman.db";
    private int numberOfLetters = 0;

    public DatabaseManager() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement stmt = connection.createStatement()) {

            String createTableSQL = """
                   CREATE TABLE IF NOT EXISTS wordsForHangman (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        word TEXT NOT NULL,
                        lvl INTEGER NOT NULL
                            );
                   """;
            stmt.execute(createTableSQL);
            String addWords = """
                    INSERT INTO wordsForHangman (word, lvl) VALUES
            ('абрикос', 1), ('бабочка', 1), ('верблюд', 2), ('гвоздика', 2),
            ('деревня', 1), ('ежевика', 1), ('жасмин', 2), ('зебра', 1),
            ('игрушка', 1), ('йогурт', 2), ('капуста', 1), ('лампа', 1),
            ('малина', 1), ('небо', 1), ('огурец', 1), ('помидор', 1),
            ('речка', 1), ('самолет', 2), ('тумбочка', 3), ('улитка', 1),
            ('фонарь', 2), ('хоккей', 2), ('цветок', 1), ('черепаха', 3),
            ('шалаш', 1), ('щука', 1), ('эскимо', 2), ('юла', 1),
            ('яблоко', 1), ('барсук', 2), ('виноград', 2), ('горка', 1),
            ('дождь', 1), ('ежедневник', 3), ('жираф', 1), ('забор', 1),
            ('искра', 1), ('календарь', 2), ('лимон', 1), ('морковь', 1),
            ('носок', 1), ('облако', 1), ('палатка', 2), ('ракета', 2),
            ('собака', 1), ('трактор', 2), ('узор', 1), ('флаг', 1),
            ('хлеб', 1), ('чайник', 2);
            """;
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM wordsForHangman;");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate(addWords);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getSecretWord(int level) {
        String query = "SELECT word FROM wordsForHangman WHERE lvl = ? ORDER BY RANDOM() LIMIT 1;";
        String word = null;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, level);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    word = resultSet.getString("word");
                    numberOfLetters = word.length();
                } else {
                    throw new RuntimeException("Нет слов для выбранного уровня сложности");
                }
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
