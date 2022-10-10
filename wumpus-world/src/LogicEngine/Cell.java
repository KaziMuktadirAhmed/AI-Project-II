package LogicEngine;

public class Cell {
    public boolean visited = false;
    public boolean safe = false;
    public boolean breeze = false;
    public boolean stench = false;
    public boolean wumpus = false;
    public boolean pit = false;
    public boolean gold = false;
    public double safetyProb = - 1.0;

    public void copyCell(Cell c) {
        this.visited = c.visited;
        this.safe = c.safe;
        this.breeze = c.breeze;
        this.stench = c.stench;
        this.wumpus = c.wumpus;
        this.pit = c.pit;
        this.gold = c.gold;
        this.safetyProb = c.safetyProb;
    }
}
