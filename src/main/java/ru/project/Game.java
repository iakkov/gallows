package ru.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    int numberOfLetters;
    void start() {
        System.out.println("Игра \"Виселица\" запущена. \n" +
                "Для начала новой игры используйте комманду /start\n" +
                "Для выхода используйте комманду /exit");
        try(Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine();
            if (command.equals("/start")) {
                String word = secretWord();
                char[] actualWord = word.toCharArray();
                char[] hiddenWord = new char[word.length()];
                for (int i = 0; i < hiddenWord.length; i++) {
                    hiddenWord[i] = '*';
                }
                System.out.println("Загадано слово, которое содержит "
                        + numberOfLetters + " букв.\n" +
                        "Предположите букву.");
                while (true) {
                    char i = scanner.nextLine().charAt(0);
                    System.out.println(Arrays.toString(hiddenWord));
                    if (guessLetter(i, word) == null) {
                        System.out.println("Такой буквы в слове нет!");
                        continue;
                    }
                    for (int j = 0; j < guessLetter(i, word).size(); j++) {
                        int number = guessLetter(i, word).get(j);
                        hiddenWord[number] = actualWord[number];
                    }
                    System.out.println(Arrays.toString(hiddenWord));
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
    String secretWord() {
        String[] wordsArray = {"солнце", "молоко", "компьютер",
        "голова", "рисунок", "кенгуру", "акробат", "гравюра", "мальчик", "пингвин"};
        int randomNumberOfWord = (int) (Math.random() * 10);
        return wordsArray[randomNumberOfWord];
    }
}
