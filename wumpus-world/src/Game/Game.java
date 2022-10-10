package Game;

import LogicEngine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    public Cell[][] game_world = new Cell[10][10];

    public void loadWorld() throws FileNotFoundException {
        File saved_world = new File("initial_world.txt");
        Scanner scan_file = new Scanner(saved_world);
        String[] line;
        while (scan_file.hasNextLine()) {
            line = scan_file.nextLine().split(",");
        }
    }
}
