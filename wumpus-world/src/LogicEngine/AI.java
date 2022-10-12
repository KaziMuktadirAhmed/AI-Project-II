package LogicEngine;

public class AI {
    private final KnowledgeBase kb;
    private int score = 100; // Initial score

    public AI () {
        this.kb = new KnowledgeBase();
    }

    public AI (KnowledgeBase new_base) {
        this.kb = new_base;
    }

    public void observe(int row_num, int col_num, Cell visited_cell) {
        kb.updateCurrentPosition(row_num, col_num);
        kb.updateVisitedWorldState(visited_cell);
    }

    public String decideMove() {
        String decision = "";
        int predicted_score = Integer.MIN_VALUE;

        if(kb.current_position.y > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y-1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "UP";
            }
        }

        if(kb.current_position.y < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y+1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "DOWN";
            }
        }

        if(kb.current_position.x > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x-1);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "LEFT";
            }
        }

        if(kb.current_position.y < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x+1);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "RIGHT";
            }
        }

        return decision;
    }

    private int calculateScore(Cell cell) {
        int score = this.score;
        if(cell.visited) {
            if (cell.gold) score += Integer.MAX_VALUE;
            else if (cell.wumpus || cell.pit) score -= 1000;
            else if (cell.safe) score -= 1;
        } else {
            if (cell.safe) score += 1;
        }
        return score;
    }
}



















