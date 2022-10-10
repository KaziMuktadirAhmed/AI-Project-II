package LogicEngine;

public class KnowledgeBase {
    private final Cell[][] memory = new Cell[10][10];

    public void updateVisitedWorldState(int cell_row, int cell_col, Cell new_cell) {
        this.memory[cell_row][cell_col].copyCell(new_cell);
    }

    private double calculateSafetyProbability(int cell_row, int cell_col) {
        if(memory[cell_row][cell_col].visited) {
            if (memory[cell_row][cell_col].safe)
                return 100.0;
            else
                return 0.0;
        }

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
            cell_probability += memory[cell_row][cell_col-1].safetyProb;
            count++;
        }

        if(cell_col < 9) {
            cell_probability += memory[cell_row][cell_col+1].safetyProb;
            count++;
        }
        cell_probability /= count;

        return cell_probability;
    }
}
