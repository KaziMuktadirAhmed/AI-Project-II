package LogicEngine;

public class Cell {
    public boolean visited = false;
    public boolean safe = false;
    public boolean breeze = false;
    public boolean stench = false;
    public boolean wumpus = false;
    public boolean pit = false;
    public boolean gold = false;
    public int numTimesVisited = 0;
    public double safetyProb = 50.0;
    public double wumpusProb = 50.0;
    public double pitProb = 50.0;

    public void copyCell(Cell c) {
        this.visited = c.visited;
        this.safe = c.safe;
        this.breeze = c.breeze;
        this.stench = c.stench;
        this.wumpus = c.wumpus;
        this.pit = c.pit;
        this.gold = c.gold;
    }

    public void updateSafety() {
        if(wumpusProb > pitProb) safetyProb = 100 - wumpusProb;
        else                     safetyProb = 100 - pitProb;
        if(safetyProb == 100)    safe = true;
        if(wumpusProb == 100 && pitProb == 100) safe = false;
    }
}
