package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import models.Algorithm;
import models.Node;
import models.Point;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiConsumer;

public class UI {

    private final int BORDER_PANE_WIDTH = 700;
    private final int BORDER_PANE_HEIGHT = 800;
    private final int BOTTOM_BORDER_HEIGHT = 50;
    private final int TILE_SIZE = 7;
    private final int HEIGHT = 100;
    private final int WIDTH = 100;
    private int percentageBlocked = 40;

    private BorderPane borderPane;
    private GridPane grid;
    private Node[][] world;
    private ComboBox algorithmPicker;
    private Slider blockedSlider;
    private Label blockedSliderValueLabel;
    private Slider delaySlider;
    private Label delaySliderValueLabel;

    public UI() {
        createContent();
    }

    private void createContent() {
        world = createWorld();
        grid = createGrid();

        HBox bottomContainer = new HBox();

        VBox algorithmPickerBox = createAlgorithmPickerVBox();

        VBox blockedSliderBox = createBlockedSliderVBox();
        blockedSliderValueLabel = new Label((int) blockedSlider.getValue() + "%");

        VBox delaySliderBox = createDelaySliderVBox();
        delaySliderValueLabel = new Label((int) delaySlider.getValue() + "");

        bottomContainer.getChildren().addAll(algorithmPickerBox, blockedSliderBox, blockedSliderValueLabel, delaySliderBox, delaySliderValueLabel);
        bottomContainer.setPrefSize(BORDER_PANE_WIDTH, BOTTOM_BORDER_HEIGHT);

        borderPane = new BorderPane();
        borderPane.setCenter(grid);
        borderPane.setBottom(bottomContainer);
        borderPane.setPrefSize(BORDER_PANE_WIDTH, BORDER_PANE_HEIGHT);
    }

    public Parent getContent() {
        return borderPane;
    }

    public Node[][] getWorld() {
        return world;
    }

    public ComboBox getAlgorithmPicker() {
        return algorithmPicker;
    }

    public Slider getBlockedSlider() {
        return blockedSlider;
    }

    public Label getBlockedSliderValueLabel() {
        return blockedSliderValueLabel;
    }

    public void setPercentageBlocked(int percentageBlocked) {
        this.percentageBlocked = percentageBlocked;
    }

    public Slider getDelaySlider() {
        return delaySlider;
    }

    public Label getDelaySliderValueLabel() {
        return delaySliderValueLabel;
    }

    public void runOnAllNodes(BiConsumer<Integer, Integer> function) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                function.accept(i, j);
            }
        }
    }

    public boolean randomlyBlock() {
        return new Random().nextInt(100) + 1 > percentageBlocked;
    }

    private Node[][] createWorld() {
        Node[][] world = new Node[HEIGHT][WIDTH];
        runOnAllNodes((i, j) -> world[i][j] = new Node(new Point(i, j), TILE_SIZE, randomlyBlock()));
        return world;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        Group tileGroup = new Group();
        grid.getChildren().addAll(tileGroup);
        runOnAllNodes((i, j) -> tileGroup.getChildren().add(world[i][j]));
        return grid;
    }

    private VBox createAlgorithmPickerVBox() {
        VBox algorithmPickerBox = new VBox();
        Label algorithmPickerLabel = new Label("Algorithms");
        algorithmPicker = createAlgorithmPicker();
        algorithmPickerBox.getChildren().addAll(algorithmPickerLabel, algorithmPicker);
        return algorithmPickerBox;
    }

    private ComboBox createAlgorithmPicker() {
        ComboBox algorithmPicker = new ComboBox();

        ObservableList<Algorithm> algorithms = FXCollections.observableArrayList();
        Arrays.stream(Algorithm.values()).forEach(algorithms::addAll);
        algorithmPicker.setItems(algorithms);

        algorithmPicker.setConverter(new StringConverter<Algorithm>() {
            @Override
            public String toString(Algorithm algorithm) {
                return algorithm.getType();
            }

            @Override
            public Algorithm fromString(String type) {
                return Algorithm.stringToAlgorithm(type);
            }
        });

        algorithmPicker.getSelectionModel().selectFirst();

        return algorithmPicker;
    }

    private VBox createBlockedSliderVBox() {
        VBox blockedSliderBox = new VBox();
        Label blockedSliderLabel = new Label("Percentage Blocked");
        blockedSlider = createBlockedSlider();
        blockedSliderBox.getChildren().addAll(blockedSliderLabel, blockedSlider);
        return blockedSliderBox;
    }

    private Slider createBlockedSlider() {
        Slider blockedSlider = new Slider();
        blockedSlider.setMin(0);
        blockedSlider.setMax(100);
        this.percentageBlocked = 40;
        blockedSlider.setValue(percentageBlocked);
        blockedSlider.setShowTickLabels(true);
        blockedSlider.setShowTickMarks(true);
        blockedSlider.setMajorTickUnit(50);
        blockedSlider.setMinorTickCount(5);
        blockedSlider.setBlockIncrement(1);
        return blockedSlider;
    }

    private VBox createDelaySliderVBox() {
        VBox delaySliderBox = new VBox();
        Label delaySliderLabel = new Label("Delay");
        delaySlider = createDelaySlider();
        delaySliderBox.getChildren().addAll(delaySliderLabel, delaySlider);
        return delaySliderBox;
    }

    private Slider createDelaySlider() {
        Slider delaySlider = new Slider();
        delaySlider.setMin(0);
        delaySlider.setMax(100);
        delaySlider.setValue(5);
        delaySlider.setShowTickLabels(true);
        delaySlider.setShowTickMarks(true);
        delaySlider.setMajorTickUnit(50);
        delaySlider.setMinorTickCount(5);
        delaySlider.setBlockIncrement(1);
        return delaySlider;
    }

}
