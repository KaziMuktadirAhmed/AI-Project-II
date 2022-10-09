package LogicEngine;

public class KnowledgeBase {
    private final Cell[][] world_state = new Cell[10][10];

    public KnowledgeBase(Cell[][] initial_world_state) {
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                this.world_state[i][j].copyCell(initial_world_state[i][j]);
            }
        }
    }

    public void updateVisitedWorldState(int cell_row, int cell_col, Cell new_cell) {
        this.world_state[cell_row][cell_col].copyCell(new_cell);
    }

    private double calculateSafetyProbability(int cell_row, int cell_col) {
        if(world_state[cell_row][cell_col].visited) {
            if (world_state[cell_row][cell_col].safe)
                return 100.0;
            else
                return 0.0;
        }

        double cell_probability = 0.0;
        int count = 0;

        if(cell_row > 0) {
            cell_probability += world_state[cell_row - 1][cell_col].safetyProb;
            count++;
        }

        if (cell_row < 9) {
            cell_probability += world_state[cell_row + 1][cell_col].safetyProb;
            count++;
        }

        if (cell_col > 0) {
            cell_probability += world_state[cell_row][cell_col-1].safetyProb;
            count++;
        }

        if(cell_col < 9) {
            cell_probability += world_state[cell_row][cell_col+1].safetyProb;
            count++;
        }
        cell_probability /= count;

        return cell_probability;
    }
}
