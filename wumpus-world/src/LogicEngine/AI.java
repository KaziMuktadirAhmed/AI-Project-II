package LogicEngine;

import java.util.ArrayList;
public class AI {
    public KnowledgeBase kb;
    private int score = 100; // Initial score
    public boolean death_flag = false;

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
        death_flag = true;
//        path.remove(path.size()-1);
        this.score = 100;
    }

//    public String getMoveFromPath() {
//        String move = path.get(0);
//        path.remove(0);
//        if(path.size() == 0) death_flag = false;
//        return move;
//    }

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
        if(kb.current_position.y > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y-1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score && neighbour.numTimesVisited < 20) {
                predicted_score = calculateScore(neighbour);
                decision = "UP";
            }
            System.out.println("UP Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.y < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y+1,kb.current_position.x);
            if(calculateScore(neighbour) > predicted_score && neighbour.numTimesVisited < 20) {
                predicted_score = calculateScore(neighbour);
                decision = "DOWN";
            }
            System.out.println("DOWN Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.x > 0) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x-1);
            if(calculateScore(neighbour) > predicted_score && neighbour.numTimesVisited < 20) {
                predicted_score = calculateScore(neighbour);
                decision = "LEFT";
            }
            System.out.println("LEFT Predicted score: " + calculateScore(neighbour));
        }

        if(kb.current_position.x < 9) {
            Cell neighbour = kb.getCell(kb.current_position.y,kb.current_position.x+1);
            if(calculateScore(neighbour) > predicted_score && neighbour.numTimesVisited < 20) {
                predicted_score = calculateScore(neighbour);
                decision = "RIGHT";
            }
            System.out.println("RIGHT Predicted score: " + calculateScore(neighbour));
        }
        int random = (int) (Math.random()*4);
        if(decision.equals("")) {
            if (random == 0) decision = "UP";
            else if (random == 1) decision = "DOWN";
            else if (random == 2) decision = "LEFT";
            else if (random == 3) decision = "RIGHT";
        }
//        path.add(decision);
        return decision;
    }

    private int calculateScore(Cell cell) {
        int score = this.score;
        if(cell.visited) {
            if (cell.gold) score = Integer.MAX_VALUE;
            else if (cell.wumpus || cell.pit) score = Integer.MIN_VALUE;
            else if (cell.safe) score -= (2 * cell.numTimesVisited);
        } else {
            if (cell.safe) score += 10000;
            else           score -= (100 - cell.safetyProb);
        }
        return score;
    }
}



















