package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import models.Node;
import models.Point;
import views.UI;

public class UiController {

    private UI view;
    private SearchAlgorithm algorithm;
    private Node[][] world; //local reference to all of the nodes in the grid
    private Point start;
    private Point end;

    public UiController(UI view) {

        this.view = view;
        this.world = view.getWorld();

        //set default algorithm from the picker
        Algorithm defaultAlgorithmType = (Algorithm) view.getAlgorithmPicker().getValue();
        this.algorithm = AlgorithmFactory.makeAlgorithm(defaultAlgorithmType);

        //set default delay speed for algorithm search
        int defaultDelay = (int) view.getDelaySlider().getValue();
        algorithm.setDelay(defaultDelay);

        addNodeClickHandler();
        addBlockedSliderListener();
        addDelaySliderListener();
        addAlgorithmPickerHandler();

    }

    private void addNodeClickHandler() {
        view.loopThroughNodesAndRun((i, j) -> world[i][j].setOnMouseClicked(new NodeClickHandler()));
    }

    private void addBlockedSliderListener() {
        view.getBlockedSlider().valueProperty().addListener((ov, oldVal, newVal) -> {
            int val = newVal.intValue();
            view.setPercentageBlocked(val);
            view.getBlockedSliderValueLabel().setText(val + "");
            resetBlock();
        });
    }

    private void addDelaySliderListener() {
        view.getDelaySlider().valueProperty().addListener((ov, oldVal, newVal) -> {
            int val = newVal.intValue();
            view.getDelaySliderValueLabel().setText(val + "");
            algorithm.setDelay(val);
        });
    }

    private void addAlgorithmPickerHandler() {
        view.getAlgorithmPicker().setOnAction(event -> {
            Algorithm algorithmType = (Algorithm) view.getAlgorithmPicker().getValue();
            algorithm = AlgorithmFactory.makeAlgorithm(algorithmType);
        });
    }

    private void resetWorld() {
        view.loopThroughNodesAndRun((i, j) -> world[i][j].resetNode());
    }

    private void resetBlock() {
        view.loopThroughNodesAndRun((i, j) -> {
            boolean blocked = view.randomlyBlock();
            world[i][j].setOpen(blocked);
        });
    }

    private void beginSearch(final Point START, final Point END) {
        Runnable task = () -> algorithm.search(world, START, END);

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
                    beginSearch(start, end);
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
