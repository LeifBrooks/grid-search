package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import models.A_Star;
import models.Node;
import models.Point;
import views.UI;

public class UiController implements EventHandler {

    private UI view;
    private Node[][] world;
    private Point start;
    private Point end;
    private Searcher algo = new A_Star();

    public void addListener() {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j].setOnMouseClicked(this);
            }
        }
    }

    public void setView(UI view) {
        this.view = view;
        this.world = view.getWorld();
    }

    public void resetWorld() {
        for (int i = 0; i < view.getHEIGHT(); i++) {
            for (int j = 0; j < view.getWIDTH(); j++) {
                world[i][j].resetNode();
            }
        }
    }

    @Override
    public void handle(Event evt) {
        Point xy = ((Node) evt.getSource()).getPointCoordinate();
        int x = xy.getX();
        int y = xy.getY();
        Point p = new Point(x, y);
        if (world[x][y].isOpen()) {
            if (start == null) {
                start = p;
                world[x][y].select(true);
            } else if (end == null) {
                end = p;
                world[x][y].select(true);
                beginSearch(new A_Star(), start, end);
            } else {
                world[start.getX()][start.getY()].select(false);
                world[end.getX()][end.getY()].select(false);
                start = p;
                resetWorld();
                end = null;
                world[x][y].select(true);
            }
        }
    }

    public void beginSearch(final Searcher algo, final Point START, final Point END) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                algo.search(world, START, END);
            }
        };

        Thread background = new Thread(task);
        background.setDaemon(true);
        background.start();
    }

    public UI getView() {
        return view;
    }
}
