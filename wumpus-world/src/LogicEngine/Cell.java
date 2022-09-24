package LogicEngine;

public class Cell {
    public boolean visited;
    public boolean safe;
    public boolean breeze;
    public boolean stench;
    public boolean wumpus;
    public boolean pit;
    public double safetyProb;

    public void copyCell(Cell c) {
        this.visited = c.visited;
        this.safe = c.safe;
        this.breeze = c.breeze;
        this.stench = c.stench;
        this.wumpus = c.wumpus;
        this.pit = c.pit;
        this.safetyProb = c.safetyProb;
    }
}
