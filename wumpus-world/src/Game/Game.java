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

    public void updateAdjecentCell(int row_num, int col_num, Cell cell) {
        if(!cell.wumpus && !cell.pit) return;
        if(row_num > 0) {
            game_world[row_num - 1][col_num].stench = cell.wumpus;
            game_world[row_num - 1][col_num].breeze = cell.pit;
        }
        if(row_num < 9) {
            game_world[row_num + 1][col_num].stench = cell.wumpus;
            game_world[row_num + 1][col_num].breeze = cell.pit;
        }
        if(col_num > 0) {
            game_world[row_num][col_num - 1].stench = cell.wumpus;
            game_world[row_num][col_num - 1].breeze = cell.pit;
        }
        if(col_num > 0) {
            game_world[row_num][col_num + 1].stench = cell.wumpus;
            game_world[row_num][col_num + 1].breeze = cell.pit;
        }
    }
}
