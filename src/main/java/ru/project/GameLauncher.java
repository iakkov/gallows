package ru.project;

public class GameLauncher {


    public static void main(String[] args) {
        Game game = new Game(new DatabaseManager());
        game.start();
    }
}
