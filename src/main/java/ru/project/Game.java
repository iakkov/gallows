package ru.project;

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
                System.out.println("Загадано слово, которое содержит "
                        + numberOfLetters + " букв.\n" +
                        "Предположите букву.");
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
    void guessLetter() {
        try (Scanner scanner = new Scanner(System.in)) {
            String guessWord = scanner.nextLine();
            while (guessWord.length() != 1) {
                System.out.println("Введите только одну букву!");
            }
        }
    }
    String secretWord() {
        String[] wordsArray = {"Солнце", "Молоко", "Компьютер",
        "Голова", "Рисунок", "Кенгуру", "Акробат", "Гравюра", "Мальчик", "Пингвин"};
        int randomNumberOfWord = (int) (Math.random() * 10);
        return wordsArray[randomNumberOfWord];
    }
}
