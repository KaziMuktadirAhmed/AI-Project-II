package LogicEngine;

public class AI {
    private final KnowledgeBase kb;

    public AI () {
        this.kb = new KnowledgeBase();
    }

    public void observe(int row_num, int col_num, Cell visited_cell) {
        kb.updateCurrentPosition(row_num, col_num);
        kb.updateVisitedWorldState(visited_cell);
    }

    private void infer() {

    }

    public void decideMove() {}
}
