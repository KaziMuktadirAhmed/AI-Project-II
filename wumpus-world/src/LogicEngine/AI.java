package LogicEngine;

public class AI {
    Cell[][] world_state = new Cell[10][10];
    KnowledgeBase knowledgeBase;

    public AI (Cell[][] initial_world_state) {
        this.world_state = initial_world_state;
        knowledgeBase = new KnowledgeBase(initial_world_state);
    }
}
