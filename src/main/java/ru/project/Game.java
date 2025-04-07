package ru.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final DatabaseManager databaseManager;

    public Game(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    void start() {
        System.out.println("""
                Игра "Виселица" запущена.\s
                Для начала новой игры используйте комманду /start
                Для выхода используйте комманду /exit""");
        try(Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine();
            if (command.equals("/start")) {
                System.out.println("""
                        Выберите уровень сложности:
                        1 - легкий уровень
                        2 - средний уровень
                        3 - сложный уровень
                        """);
                int lvl;
                while (true) {
                    lvl = scanner.nextInt();
                    if (lvl == 1 || lvl == 2 || lvl == 3) {
                        break;
                    } else {
                        System.out.println("Выберите корректный уровень сложности");
                    }
                }
                String word = databaseManager.getSecretWord(lvl);
                int numbersOfErrors = 0;
                char[] actualWord = word.toCharArray();
                char[] hiddenWord = new char[databaseManager.getNumberOfLetters()];
                Arrays.fill(hiddenWord, '*');
                System.out.println("Загадано слово, которое содержит "
                        + databaseManager.getNumberOfLetters() + " букв.\n" +
                        "Предположите букву.");

                while (true) {
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.isEmpty()) {
                        System.out.println("Введите хотя бы одну букву.");
                        continue;
                    }
                    if (!input.matches("[а-яё]")) {
                        System.out.println("Введите одну русскую букву.");
                        continue;
                    }
                    char i = input.charAt(0);
                    if (guessLetter(i, word) == null) {
                        System.out.println("Такой буквы в слове нет!");
                        numbersOfErrors++;
                        drawGallows(numbersOfErrors);
                        continue;
                    }
                    for (int j = 0; j < guessLetter(i, word).size(); j++) {
                        int number = guessLetter(i, word).get(j);
                        hiddenWord[number] = actualWord[number];
                    }
                    wordByLetters(hiddenWord);
                    if (!Arrays.toString(hiddenWord).contains("*")) {
                        System.out.println("Вы победили с " + numbersOfErrors + " ошибками!");
                        exit();
                    }
                }
            }
            if (command.equals("/exit")) {
                exit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void exit() {
        System.exit(0);
    }
    List<Integer> guessLetter(char letter, String word) {
        boolean isLetterExist = false;
        List<Integer> indexesOfLetters = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (letter == word.charAt(i)) {
                indexesOfLetters.add(i);
                isLetterExist = true;
            }
        }
        if (isLetterExist) {
            return indexesOfLetters;
        } else return null;

    }
    void wordByLetters(char[] hiddenWord) {
        for (int i = 0; i < hiddenWord.length; i++) {
            if (i == hiddenWord.length - 1) {
                System.out.println(hiddenWord[i]);
            } else System.out.printf(hiddenWord[i] + " ");
        }
    }

    void drawGallows(int numberOfErrors) {
        if (numberOfErrors == 1) {
            System.out.println("""
                     |
                     |
                     |
                     |
                     |
                    /|\\""");
        }
        if (numberOfErrors == 2) {
            System.out.println("""
                     |------
                     |
                     |
                     |
                     |
                    /|\\""");
        }
        if (numberOfErrors == 3) {
            System.out.println("""
                     |------
                     |     |
                     |     |
                     |
                     |
                    /|\\""");
        }
        if (numberOfErrors == 4) {
            System.out.println("""
                     |------
                     |     |
                     |     |
                     |     o
                     |
                    /|\\""");
        }
        if (numberOfErrors == 5) {
            System.out.println("""
                     |------
                     |     |
                     |     |
                     |     o
                     |    /|\\
                    /|\\""");
        }
        if (numberOfErrors == 6) {
            System.out.println("""
                     |------
                     |     |
                     |     |
                     |     o
                     |    /|\\
                    /|\\   /\\
                    Вас повесили!""");
            exit();
        }
    }
}