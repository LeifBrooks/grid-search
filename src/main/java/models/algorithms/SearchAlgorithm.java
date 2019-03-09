package models.algorithms;

import models.Node;

public abstract class SearchAlgorithm {

    protected int delay;

    public abstract void search(Node[][] world, Node start, Node end);

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
