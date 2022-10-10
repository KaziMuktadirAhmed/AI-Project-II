package LogicEngine;

public class AI {
    private final KnowledgeBase knowledge_base;

    public AI (Cell[][] initial_world_state) {
        this.knowledge_base = new KnowledgeBase();
    }
}
