package controllers;

import models.Node;
import models.Point;

public interface Searcher {

    void search(Node[][] world, Point start, Point end);

    void setDelay(int delay);
    
}
