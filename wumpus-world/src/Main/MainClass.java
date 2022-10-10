package Main;

import Game.Game;

import java.io.FileNotFoundException;

public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hello cruel world!!!");
        test();
    }

    public static void test() throws FileNotFoundException {
        Game game = new Game();
        game.loadWorld();
    }
}
