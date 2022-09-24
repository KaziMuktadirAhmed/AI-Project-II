package LogicEngine;

public class AI {
    Cell[][] world_state = new Cell[10][10];
    KnowledgeBase knowledge_base;

    public AI (Cell[][] initial_world_state) {
        this.world_state = initial_world_state;
        knowledge_base = new KnowledgeBase(initial_world_state);
    }
}
