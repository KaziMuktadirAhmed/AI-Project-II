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
        int row_count = 0;
        while (scan_file.hasNextLine()) {
            line = scan_file.nextLine().split(",");
            for (int col_count=0; col_count< line.length; col_count++) {
                if(line[col_count].toLowerCase().equals("s"))
                    game_world[row_count][col_count].safe = true;
                else if (line[col_count].toLowerCase().equals("g"))
                    game_world[row_count][col_count].gold = true;
                else if (line[col_count].toLowerCase().equals("p")) {
                    game_world[row_count][col_count].pit = true;
                    updateAdjacentCell(row_count, col_count, game_world[row_count][col_count]);
                }
                else if (line[col_count].toLowerCase().equals("w")) {
                    game_world[row_count][col_count].wumpus = true;
                    updateAdjacentCell(row_count, col_count, game_world[row_count][col_count]);
                }
            }
            row_count++;
        }
    }

    public void updateAdjacentCell(int row_num, int col_num, Cell cell) {
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
