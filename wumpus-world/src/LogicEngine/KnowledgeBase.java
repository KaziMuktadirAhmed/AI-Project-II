package LogicEngine;

public class KnowledgeBase {
    Cell[][] world_state = new Cell[10][10];

<<<<<<< HEAD
    public KnowledgeBase (Cell[][] initial_world_state) {
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                this.world_state[i][j].copyCell(initial_world_state[i][j]);
            }
        }
    }

    private void updateWorldState() {}
=======
>>>>>>> 7175939 (ok)
}
