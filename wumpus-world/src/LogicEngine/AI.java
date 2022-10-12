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
        Point position = kb.current_position;

        if(position.y > 0) {

        }
        if(position.y < 9) {}
        if(position.x > 0) {}
        if(position.x < 9) {}

    }

//    private void inferForWump

    public void decideMove() {}
}



















