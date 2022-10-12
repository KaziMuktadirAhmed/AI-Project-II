package Main;

import Game.Game;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        test();
    }

    public static void test() throws IOException {
        Game game = new Game();
        game.printWorld();
        game.run();
    }
}
