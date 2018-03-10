package models;

public abstract class SearchAlgorithm {

    protected int delay;

    public abstract void search(Node[][] world, Point start, Point end);

    public void setDelay(int delay) {
        this.delay = delay;
    }

}
