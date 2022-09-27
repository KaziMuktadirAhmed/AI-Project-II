package LogicEngine;

public class AI {
    private Cell[][] world_state = new Cell[10][10];
    private final KnowledgeBase knowledge_base;

    public AI (Cell[][] initial_world_state) {
        this.world_state = initial_world_state;
        this.knowledge_base = new KnowledgeBase(initial_world_state);
    }
}
