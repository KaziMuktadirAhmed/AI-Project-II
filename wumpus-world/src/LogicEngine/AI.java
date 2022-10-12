package LogicEngine;

public class AI {
    private final KnowledgeBase kb;
    private int score = 100;

    public AI () {
        this.kb = new KnowledgeBase();
    }

    public void observe(int row_num, int col_num, Cell visited_cell) {
        kb.updateCurrentPosition(row_num, col_num);
        kb.updateVisitedWorldState(visited_cell);
    }

    private void infer() {
        Point position = kb.current_position;

        if(position.y > 0) {}
        if(position.y < 9) {}
        if(position.x > 0) {}
        if(position.x < 9) {}

    }

    private void inferForWumpus(Point position) {

    }

    public String decideMove() {
        String decision = "";
        int predicted_score = Integer.MIN_VALUE;
        Cell current_cell = kb.getCurrentCell();

        if(kb.current_position.y > 0) {

        }

//        if()
        return decision;
    }

    private int calculateScore(Cell cell) {
        int score = this.score;
        if(cell.gold)                     score += 100000;
        else if (cell.wumpus || cell.pit) score -= 1000;
        else if (cell.safe)               score -= 1;
        return score;
    }
}



















