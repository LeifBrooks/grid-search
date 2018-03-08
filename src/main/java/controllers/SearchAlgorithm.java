package controllers;

import models.Node;
import models.Point;

public abstract class SearchAlgorithm {

    protected int delay;

    abstract public void search(Node[][] world, Point start, Point end);

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
}
