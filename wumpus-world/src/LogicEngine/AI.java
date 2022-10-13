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
        updateScore(visited_cell);
        kb.updateVisitedWorldState(visited_cell);
    }

    public void die(int row, int col, Cell death_cell) {
        kb.updateCurrentPosition(row, col);
        kb.updateVisitedWorldState(death_cell);
        this.score = 100;
    }

    private void updateScore(Cell cell) {
        Cell memory = kb.getCurrentCell();
        if(memory.visited)          this.score -= (5 * memory.numTimesVisited);
        else                        this.score += 1;
        if(cell.gold)               this.score = Integer.MAX_VALUE;
        if(cell.pit || cell.wumpus) this.score = Integer.MIN_VALUE;
    }

    public int getScore() {
        return this.score;
    }

    public String decideMove() {
        String decision = "";
        int predicted_score = Integer.MIN_VALUE;
        System.out.println("Position row " + kb.current_position.y + " col " + kb.current_position.x );
        if(kb.current_position.y > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y-1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "UP";
            }
            System.out.println("UP Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.y < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y+1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "DOWN";
            }
            System.out.println("DOWN Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.x > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x-1);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "LEFT";
            }
            System.out.println("LEFT Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.x < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x+1);
            if(calculateScore(neighbour) > predicted_score) {
                predicted_score = calculateScore(neighbour);
                decision = "RIGHT";
            }
            System.out.println("RIGHT Predicted score: " + calculateScore(neighbour));
        }

        return decision;
    }

    private int calculateScore(Cell cell) {
        int score = this.score;
        if(cell.visited) {
            if (cell.gold) score = Integer.MAX_VALUE;
            else if (cell.wumpus || cell.pit) score = Integer.MIN_VALUE;
            else if (cell.safe) score -= (5 * cell.numTimesVisited);
        } else {
            if (cell.safe) score += 1;
            else           score -= (100 - cell.safetyProb);
        }
        return score;
    }
}



















