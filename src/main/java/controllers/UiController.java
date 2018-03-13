package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import models.Algorithm;
import models.AlgorithmFactory;
import models.Node;
import models.SearchAlgorithm;
import views.UI;

public class UiController {

    private UI view;
    private SearchAlgorithm algorithm;
    private Node[][] world; //local reference to all of the nodes in the grid
    private Node start;
    private Node end;

    public UiController(UI view) {

        this.view = view;
        this.world = view.getWorld();

        //set default algorithm from the picker
        algorithm = getAlgorithmFromPicker();

        //set default delay speed for algorithm search
        int defaultDelay = (int) view.getDelaySlider().getValue();
        algorithm.setDelay(defaultDelay);

        addNodeClickHandlers();
        addBlockedSliderListener();
        addDelaySliderListener();
        addAlgorithmPickerHandler();

    }

    private void addNodeClickHandlers() {
        view.runOnAllNodes((i, j) -> addNodeClickHandler(world[i][j]));
    }

    private void addNodeClickHandler(Node node) {
        node.setOnMouseClicked(new NodeClickHandler());
    }

    private void addBlockedSliderListener() {
        view.getBlockedSlider().valueProperty().addListener((ov, oldVal, newVal) -> {
            int val = newVal.intValue();
            view.setPercentageBlocked(val);
            view.getBlockedSliderValueLabel().setText(val + "%");
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
        view.getAlgorithmPicker().setOnAction(event -> algorithm = getAlgorithmFromPicker());
    }

    private SearchAlgorithm getAlgorithmFromPicker() {
        Algorithm algorithmType = (Algorithm) view.getAlgorithmPicker().getValue();
        return AlgorithmFactory.makeAlgorithm(algorithmType);
    }

    private void resetWorld() {
        view.runOnAllNodes((i, j) -> world[i][j].resetNode());
    }

    private void resetBlock() {
        view.runOnAllNodes((i, j) -> {
            boolean blocked = view.randomlyBlock();
            world[i][j].setOpen(blocked);
        });
    }

    private void beginSearch() {
        Runnable task = () -> algorithm.search(world, start, end);

        Thread background = new Thread(task);
        background.setDaemon(true);
        background.start();
    }

    private class NodeClickHandler implements EventHandler {
        @Override
        public void handle(Event evt) {
            Node clickedNode = (Node) evt.getSource();
            if (clickedNode.isOpen()) {
                if (start == null) {
                    start = clickedNode;
                    start.highlight();
                } else if (end == null) {
                    end = clickedNode;
                    end.highlight();
                    beginSearch();
                } else {
                    resetWorld();
                    end = null;
                    start = clickedNode;
                    start.highlight();
                }
            }
        }
    }
}
