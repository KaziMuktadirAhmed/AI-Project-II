package LogicEngine;

class Point{
    public int x;
    public int y;
}

public class KnowledgeBase {
    public Cell[][] memory = new Cell[10][10];
    public Point current_position = new Point();

    public KnowledgeBase() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                memory[i][j] = new Cell();
    }

    public Cell getCurrentCell() {
        return memory[current_position.y][current_position.x];
    }

    public Cell getCell(int row_num, int col_num) {
        return memory[row_num][col_num];
    }

    public void updateCurrentPosition(int row_num, int col_num) {
        current_position.x = col_num;
        current_position.y = row_num;
    }

    public void updateVisitedWorldState(Cell new_cell) {
        this.memory[current_position.y][current_position.x].copyCell(new_cell);
        this.memory[current_position.y][current_position.x].numTimesVisited++;
        updateCurrentCellSafetyProbability();
        updateAdjacentCellsProbability();
    }

    private void updateCurrentCellSafetyProbability() {
        if (memory[current_position.y][current_position.x].safe)
            memory[current_position.y][current_position.x].safetyProb = 100.0;
        else if(memory[current_position.y][current_position.x].wumpus) {
            memory[current_position.y][current_position.x].wumpusProb = 100.0;
            memory[current_position.y][current_position.x].pitProb = 0;
            memory[current_position.y][current_position.x].updateSafety();
        } else if (memory[current_position.y][current_position.x].pit) {
            memory[current_position.y][current_position.x].pitProb = 100.0;
            memory[current_position.y][current_position.x].wumpusProb = 0;
            memory[current_position.y][current_position.x].updateSafety();
            System.out.println("died");
        }
    }

    private void updateAdjacentCellsProbability() {
        Cell current_cell = memory[current_position.y][current_position.x];
        if(!current_cell.breeze && !current_cell.stench) allSafeCellAdjacent();
        if(current_cell.stench)                          currentCellStench();
        else                                             noStenchCurrentCell();
        if(current_cell.breeze)                          currentCellBreeze();
        else                                             noBreezeCurrentCell();
    }

    private void allSafeCellAdjacent() {
        if(current_position.y > 0)
            memory[current_position.y-1][current_position.x].safe = true;
        if(current_position.y < 9)
            memory[current_position.y+1][current_position.x].safe = true;
        if(current_position.x > 0)
            memory[current_position.y][current_position.x-1].safe = true;
        if(current_position.x < 9)
            memory[current_position.y][current_position.x+1].safe = true;
    }

    private void noStenchCurrentCell() {
        if(current_position.y > 0) {
            memory[current_position.y - 1][current_position.x].wumpus = false;
            memory[current_position.y - 1][current_position.x].wumpusProb = 0.0;
            memory[current_position.y - 1][current_position.x].updateSafety();
        }
        if(current_position.y < 9) {
            memory[current_position.y + 1][current_position.x].wumpus = false;
            memory[current_position.y + 1][current_position.x].wumpusProb = 0.0;
            memory[current_position.y + 1][current_position.x].updateSafety();
        }
        if(current_position.x > 0){
            memory[current_position.y][current_position.x - 1].wumpus = false;
            memory[current_position.y][current_position.x - 1].wumpusProb = 0.0;
            memory[current_position.y][current_position.x - 1].updateSafety();
        }
        if(current_position.x < 9){
            memory[current_position.y][current_position.x + 1].wumpus = false;
            memory[current_position.y][current_position.x + 1].wumpusProb = 0.0;
            memory[current_position.y][current_position.x + 1].updateSafety();
        }
    }

    private void noBreezeCurrentCell() {
        if(current_position.y > 0) {
            memory[current_position.y - 1][current_position.x].pit = false;
            memory[current_position.y - 1][current_position.x].pitProb = 0.0;
            memory[current_position.y - 1][current_position.x].updateSafety();
        }
        if(current_position.y < 9) {
            memory[current_position.y + 1][current_position.x].pit = false;
            memory[current_position.y + 1][current_position.x].pitProb = 0.0;
            memory[current_position.y + 1][current_position.x].updateSafety();
        }
        if(current_position.x > 0){
            memory[current_position.y][current_position.x - 1].pit = false;
            memory[current_position.y][current_position.x - 1].pitProb = 0.0;
            memory[current_position.y][current_position.x - 1].updateSafety();
        }
        if(current_position.x < 9){
            memory[current_position.y][current_position.x + 1].pit = false;
            memory[current_position.y][current_position.x + 1].pitProb = 0.0;
            memory[current_position.y][current_position.x + 1].updateSafety();
        }
    }

    private void currentCellStench() {
        if(current_position.y > 0 && !memory[current_position.y - 1][current_position.x].visited) {
            memory[current_position.y - 1][current_position.x].wumpusProb += 12.5;
            memory[current_position.y - 1][current_position.x].updateSafety();
        }
        if(current_position.y < 9 && !memory[current_position.y + 1][current_position.x].visited){
            memory[current_position.y + 1][current_position.x].wumpusProb += 12.5;
            memory[current_position.y + 1][current_position.x].updateSafety();
        }
        if(current_position.x > 0 && !memory[current_position.y][current_position.x - 1].visited){
            memory[current_position.y][current_position.x - 1].wumpusProb += 12.5;
            memory[current_position.y][current_position.x - 1].updateSafety();
        }
        if(current_position.x < 9 && !memory[current_position.y][current_position.x + 1].visited){
            memory[current_position.y][current_position.x + 1].wumpusProb += 12.5;
            memory[current_position.y][current_position.x + 1].updateSafety();
        }
    }

    private void currentCellBreeze() {
        if(current_position.y > 0 && !memory[current_position.y - 1][current_position.x].visited) {
            memory[current_position.y - 1][current_position.x].pitProb += 12.5;
            memory[current_position.y - 1][current_position.x].updateSafety();
        }
        if(current_position.y < 9 && !memory[current_position.y + 1][current_position.x].visited){
            memory[current_position.y + 1][current_position.x].pitProb += 12.5;
            memory[current_position.y + 1][current_position.x].updateSafety();
        }
        if(current_position.x > 0 && !memory[current_position.y][current_position.x - 1].visited){
            memory[current_position.y][current_position.x - 1].pitProb += 12.5;
            memory[current_position.y][current_position.x - 1].updateSafety();
        }
        if(current_position.x < 9 && !memory[current_position.y][current_position.x + 1].visited){
            memory[current_position.y][current_position.x + 1].pitProb += 12.5;
            memory[current_position.y][current_position.x + 1].updateSafety();
        }
    }

    private double calculateAvgProbability(int cell_row, int cell_col) {
        double cell_probability = 0.0;
        int count = 0;

        if(cell_row > 0) {
            cell_probability += memory[cell_row - 1][cell_col].safetyProb;
            count++;
        }

        if (cell_row < 9) {
            cell_probability += memory[cell_row + 1][cell_col].safetyProb;
            count++;
        }

        if (cell_col > 0) {
            cell_probability += memory[cell_row][cell_col - 1].safetyProb;
            count++;
        }

        if(cell_col < 9) {
            cell_probability += memory[cell_row][cell_col + 1].safetyProb;
            count++;
        }
        cell_probability /= count;
        return cell_probability;
    }
}
