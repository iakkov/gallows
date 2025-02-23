package ru.project;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    int numberOfLetters;
    private DatabaseManager databaseManager;

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
                String word = databaseManager.secretWord();
                int numbersOfErrors = 0;
                char[] actualWord = word.toCharArray();
                char[] hiddenWord = new char[databaseManager.getNumberOfLetters()];
                Arrays.fill(hiddenWord, '*');
                System.out.println("Загадано слово, которое содержит "
                        + databaseManager.getNumberOfLetters() + " букв.\n" +
                        "Предположите букву.");
                while (true) {
                    char i = scanner.nextLine().charAt(0);
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
