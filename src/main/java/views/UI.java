package views;

import controllers.Algorithm;
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
import models.Node;

import java.util.Arrays;
import java.util.Random;

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

    public void createContent() {
        world = createWorld();
        grid = createGrid();


        HBox bottom = new HBox();

        VBox algorithmPickerBox = new VBox();
        Label algorithmPickerLabel = new Label("Algorithms");
        algorithmPicker = createAlgorithmPicker();
        algorithmPickerBox.getChildren().addAll(algorithmPickerLabel, algorithmPicker);

        VBox blockedSliderBox = new VBox();
        Label blockedSliderLabel = new Label("Percentage Blocked");
        blockedSlider = createBlockedSlider();
        blockedSliderBox.getChildren().addAll(blockedSliderLabel, blockedSlider);
        blockedSliderValueLabel = new Label(Double.toString(blockedSlider.getValue()));

        VBox delaySliderLabelBox = new VBox();
        Label delaySliderLabel = new Label("Delay");
        delaySlider = createDelaySlider();
        delaySliderLabelBox.getChildren().addAll(delaySliderLabel, delaySlider);

        delaySliderValueLabel = new Label(Double.toString(delaySlider.getValue()));

        bottom.getChildren().addAll(algorithmPickerBox, blockedSliderBox, blockedSliderValueLabel, delaySliderLabelBox, delaySliderValueLabel);
        bottom.setPrefSize(BORDER_PANE_WIDTH, BOTTOM_BORDER_HEIGHT);

        borderPane = new BorderPane();
        borderPane.setCenter(grid);
        borderPane.setBottom(bottom);
        borderPane.setPrefSize(BORDER_PANE_WIDTH, BORDER_PANE_HEIGHT);
    }

    public Parent getContent() {
        return borderPane;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        Group tileGroup = new Group();
        grid.getChildren().addAll(tileGroup);
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                tileGroup.getChildren().add(world[x][y]);
            }
        }
        return grid;
    }

    private Node[][] createWorld() {
        Node[][] world = new Node[HEIGHT][WIDTH];
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                world[x][y] = new Node(x, y, TILE_SIZE, randomlyBlock());
            }
        }
        return world;
    }

    public boolean randomlyBlock() {
        Random rand = new Random();
        return rand.nextInt(100) + 1 > percentageBlocked;
    }

    private ComboBox createAlgorithmPicker() {
        ComboBox algorithmPicker = new ComboBox();
        ObservableList<Algorithm> algorithms = FXCollections.observableArrayList();

        Arrays.stream(Algorithm.values()).forEach(algorithms::addAll);

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

        algorithmPicker.setItems(algorithms);
        algorithmPicker.getSelectionModel().selectFirst();

        return algorithmPicker;
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

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
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

}
