package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import models.Node;
import models.Point;
import views.UI;

public class UiController {

    private UI view;
    private Node[][] world; //local reference to all of the nodes in the grid
    private Point start;
    private Point end;
    private Searcher algorithm;

    public UiController(UI view) {
        this.view = view;
        this.world = view.getWorld();
        this.algorithm = getAlgorithmFromName((String) view.getAlgorithmPicker().getValue());
        addNodeClickHandler();
        addAlgorithmPickerHandler();
        addBlockedSliderListener();
        addDelaySliderListener();
    }

    private void addNodeClickHandler() {
        for (Node[] aWorld : world) {
            for (int j = 0; j < world[0].length; j++) {
                aWorld[j].setOnMouseClicked(new NodeClickHandler());
            }
        }
    }

    private void addAlgorithmPickerHandler() {
        view.getAlgorithmPicker().setOnAction(event -> {
            String algorithmName = (String) view.getAlgorithmPicker().getValue();
            algorithm = getAlgorithmFromName(algorithmName);
            algorithm.setDelay(15);
        });
    }

    private Searcher getAlgorithmFromName(String name) {
        Algorithm algorithmType = Algorithm.stringToAlgorithm(name);
        return AlgorithmFactory.getAlgorithm(algorithmType);
    }

    private void addBlockedSliderListener() {
        view.getBlockedSlider().valueProperty().addListener((ov, old_val, new_val) -> {
            int val = new_val.intValue();
            view.setPercentageBlocked(val);
            view.getBlockedSliderLabel().setText(val + "");
            resetBlock();
        });
    }

    private void addDelaySliderListener() {
        view.getDelaySlider().valueProperty().addListener((ov, old_val, new_val) -> {
            int val = new_val.intValue();
            view.getDelaySliderLabel().setText(val + "");
            algorithm.setDelay(val);
        });
    }

    private void resetWorld() {
        for (int i = 0; i < view.getHEIGHT(); i++) {
            for (int j = 0; j < view.getWIDTH(); j++) {
                world[i][j].resetNode();
            }
        }
    }

    private void resetBlock() {
        for (int i = 0; i < view.getHEIGHT(); i++) {
            for (int j = 0; j < view.getWIDTH(); j++) {
                boolean blocked = view.determineIfBlocked();
                world[i][j].setOpen(blocked);
            }
        }
    }

    private void beginSearch(final Searcher algorithm, final Point START, final Point END) {
        Runnable task = () -> {
            algorithm.search(world, START, END);
        };

        Thread background = new Thread(task);
        background.setDaemon(true);
        background.start();
    }

    private class NodeClickHandler implements EventHandler {
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
                    beginSearch(algorithm, start, end);
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
    }

}
