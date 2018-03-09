package controllers;

import models.Node;
import models.Point;

public abstract class SearchAlgorithm {

    protected int delay;

    protected abstract void search(Node[][] world, Point start, Point end);

    protected void setDelay(int delay) {
        this.delay = delay;
    }

}
