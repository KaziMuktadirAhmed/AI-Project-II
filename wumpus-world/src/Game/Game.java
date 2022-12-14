package Game;

import LogicEngine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Point{
    public int x;
    public int y;
}

public class Game {
    public Cell[][] game_world = new Cell[10][10];
    Point player_position = new Point();
    AI ai = new AI();

    public Game() throws FileNotFoundException {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                game_world[i][j] = new Cell();
            }
        }
        loadWorld();
    }

    private void loadWorld() throws FileNotFoundException {
        File saved_world = new File("initial_world.txt");
        Scanner scan_file = new Scanner(saved_world);
        String[] line;
        int row_count = 0;
        while (scan_file.hasNextLine()) {
            line = scan_file.nextLine().split(",");
            for (int col_count=0; col_count< line.length; col_count++) {
                switch (line[col_count].toLowerCase()) {
                    case "s" -> game_world[row_count][col_count].safe = true;
                    case "g" -> {
                        game_world[row_count][col_count].safe = true;
                        game_world[row_count][col_count].gold = true;
                    }
                    case "p" -> {
                        game_world[row_count][col_count].safe = false;
                        game_world[row_count][col_count].pit = true;
                        updateAdjacentCell(row_count, col_count, game_world[row_count][col_count]);
                    }
                    case "w" -> {
                        game_world[row_count][col_count].safe = false;
                        game_world[row_count][col_count].wumpus = true;
                        updateAdjacentCell(row_count, col_count, game_world[row_count][col_count]);
                    }
                }
            }
            row_count++;
        }
    }

    private void updateAdjacentCell(int row_num, int col_num, Cell cell) {
        if(row_num > 0) {
            if(cell.wumpus) game_world[row_num - 1][col_num].stench = true;
            if(cell.pit)    game_world[row_num - 1][col_num].breeze = true;
        }
        if(row_num < 9) {
            if(cell.wumpus) game_world[row_num + 1][col_num].stench = true;
            if(cell.pit)    game_world[row_num + 1][col_num].breeze = true;
        }
        if(col_num > 0) {
            if(cell.wumpus) game_world[row_num][col_num - 1].stench = true;
            if(cell.pit)    game_world[row_num][col_num - 1].breeze = true;
        }
        if(col_num < 9) {
            if(cell.wumpus) game_world[row_num][col_num + 1].stench = true;
            if(cell.pit)    game_world[row_num][col_num + 1].breeze = true;
        }
    }

    public void printWorld() throws IOException {
        String output = "";
        for (int i=0; i<10; i++) {
            String line = "";
            for (int j=0; j<10; j++) {
                if(game_world[i][j].safe) {
                    line += "{ safe ,";
                    if(game_world[i][j].gold)   line += "g,";
                    if(game_world[i][j].breeze) line += "bre,";
                    else                        line += "nbr,";
                    if(game_world[i][j].stench) line += "stn";
                    else                        line += "nst";
                }
                else {
                    line += "{ death,";
                    if (game_world[i][j].pit)       line += "pit,";
                    else                            line += "npt,";
                    if (game_world[i][j].wumpus)    line += "wum";
                    else                            line += "nwm";
                }
                line += " } ";
            }
            output += (line + "\n");
        }
        FileWriter output_file = new FileWriter("output.txt");
        output_file.write(output);
        output_file.close();
    }

    public void run() {
        Scanner scan_inpt = new Scanner(System.in);
        boolean run_game = true;
        setInitialPosition();
        while (run_game) {
            String ai_move = "";
            ai.observe(player_position.y, player_position.x, game_world[player_position.y][player_position.x]);
            ai_move = ai.decideMove();

            showWorld();
            System.out.println("Ai move: " + ai_move);
            System.out.println("Ai score: " + ai.getScore());
            String input = scan_inpt.nextLine();
            move(ai_move);
            if(input.equalsIgnoreCase("exit")) run_game = false;
            if(game_world[player_position.y][player_position.x].pit || game_world[player_position.y][player_position.x].wumpus) {
                showWorld();
                System.out.println("DEATH");
                System.out.println("visited status: " + game_world[player_position.y][player_position.x].visited);
                respawn(player_position.y, player_position.x, game_world[player_position.y][player_position.x]);
//                run_game = false;
            } else if (game_world[player_position.y][player_position.x].gold) {
                showWorld();
                System.out.println("WIN");
                run_game = false;
            }
        }
    }

    private void respawn(int row, int col, Cell death_place) {
        ai.die(row, col, death_place);
        setInitialPosition();
    }

    private void setInitialPosition() {
        player_position.x = 0;
        player_position.y = 0;
        game_world[player_position.y][player_position.x].visited = true;
    }

    private void move(String input) {
        switch (input.toLowerCase()) {
            case "up" -> {
                if (player_position.y > 0) player_position.y--;
            }
            case "down" -> {
                if (player_position.y < 9) player_position.y++;
            }
            case "left" -> {
                if (player_position.x > 0) player_position.x--;
            }
            case "right" -> {
                if (player_position.x < 9) player_position.x++;
            }
            case "shoot" -> {
                Scanner scan_dir = new Scanner(System.in);
                System.out.print("Input direction: ");
                if(shoot(scan_dir.next())) System.out.println("Wumpus killed");
            }
        }
        game_world[player_position.y][player_position.x].visited = true;
    }

    private boolean shoot(String direction) {
        boolean did_kill = false;
        switch (direction.toLowerCase()) {
            case "up" -> {
                if (player_position.y > 0) {
                    if(game_world[player_position.y-1][player_position.x].wumpus) {
                        game_world[player_position.y - 1][player_position.x].wumpus = false;
                        game_world[player_position.y - 1][player_position.x].safe = true;
                        updateAdjacentCell(player_position.y-1, player_position.x, game_world[player_position.y-1][player_position.x]);
                        did_kill = true;
                    }
                }
            }
            case "down" -> {
                if (player_position.y < 9){
                    if(game_world[player_position.y+1][player_position.x].wumpus) {
                        game_world[player_position.y + 1][player_position.x].wumpus = false;
                        game_world[player_position.y + 1][player_position.x].safe = true;
                        updateAdjacentCell(player_position.y+1, player_position.x, game_world[player_position.y+1][player_position.x]);
                        did_kill = true;
                    }
                }
            }
            case "left" -> {
                if (player_position.x > 0){
                    if(game_world[player_position.y][player_position.x-1].wumpus) {
                        game_world[player_position.y][player_position.x - 1].wumpus = false;
                        game_world[player_position.y][player_position.x - 1].safe = true;
                        updateAdjacentCell(player_position.y, player_position.x-1, game_world[player_position.y][player_position.x-1]);
                        did_kill = true;
                    }
                }
            }
            case "right" -> {
                if (player_position.x < 9){
                    if(game_world[player_position.y][player_position.x+1].wumpus) {
                        game_world[player_position.y][player_position.x + 1].wumpus = false;
                        game_world[player_position.y][player_position.x + 1].safe = true;
                        updateAdjacentCell(player_position.y, player_position.x+1, game_world[player_position.y][player_position.x+1]);
                        did_kill = true;
                    }
                }
            }
        }
        return did_kill;
    }

    private void showWorld () {
        String output = "";
        for (int i = 0; i < 10; i++) {
            String line = "";
            for (int j = 0; j < 10; j++) {
                if(player_position.x == j && player_position.y == i) line += "A";
                if(game_world[i][j].visited) {
                    if(ai.kb.memory[i][j].breeze && ai.kb.memory[i][j].stench)  line += "BS|";
                    else if (ai.kb.memory[i][j].breeze)                         line += "Br|";
                    else if (ai.kb.memory[i][j].stench)                         line += "BS|";
                    else if (ai.kb.memory[i][j].wumpus)                         line += "Wm|";
                    else if (ai.kb.memory[i][j].pit)                            line += "Pt|";
                    else if (ai.kb.memory[i][j].gold)                           line += "Go|";
                    else                                                        line += "No|";
                }
                else                            line += "Un|";
                if(game_world[i][j].wumpus)     line += "W";
                else if (game_world[i][j].pit)  line += "P";
                else if (game_world[i][j].gold) line += "G";
                else                            line += "S";
                line += " ";
            }
            output += (line + "\n");
        }
        System.out.print(output);
    }
}
