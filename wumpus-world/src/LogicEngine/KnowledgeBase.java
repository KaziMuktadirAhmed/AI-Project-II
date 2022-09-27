package LogicEngine;

public class KnowledgeBase {
    private final Cell[][] world_state = new Cell[10][10];

    public KnowledgeBase (Cell[][] initial_world_state) {
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                this.world_state[i][j].copyCell(initial_world_state[i][j]);
            }
        }
    }

    public void updateVisitedWorldState(int cell_row, int cell_col, Cell new_cell) {
        this.world_state[cell_row][cell_col].copyCell(new_cell);
    }
}
