package views;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Node;

import java.util.Random;

public class UI {

    public final int TILE_SIZE = 7;
    public final int HEIGHT = 100;
    public final int WIDTH = 100;
    private int percentageBlocked = 40;
    
    private GridPane grid;
    private Node[][] world;
    private ComboBox algorithmPicker;
    private Slider blockedSlider;
    private Label blockedSliderLabel;
    private Slider delaySlider;
    private Label delaySliderLabel;


    public Parent createContent() {
        world = createWorld();
        grid = createGrid();

        HBox bottom = new HBox();
        algorithmPicker = createAlgorithmPicker();

        blockedSlider = createBlockedSlider();
        blockedSliderLabel = new Label(Double.toString(blockedSlider.getValue()));

        delaySlider = createDelaySlider();
        delaySliderLabel = new Label(Double.toString(delaySlider.getValue()));

        bottom.getChildren().addAll(algorithmPicker, blockedSlider, blockedSliderLabel, delaySlider, delaySliderLabel);
        bottom.setPrefSize(700, 50);

        BorderPane bp = new BorderPane();
        bp.setCenter(grid);
        bp.setBottom(bottom);
        bp.setPrefSize(700, 800);


        return bp;
    }

    public GridPane createGrid() {
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

    public Node[][] createWorld() {
        Node[][] world = initWorld();
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                Node node = new Node(x, y, TILE_SIZE, determineIfBlocked());
                world[x][y] = node;
            }
        }
        return world;
    }

    private ComboBox createAlgorithmPicker() {
        ComboBox algorithmPicker = new ComboBox();
        algorithmPicker.getItems().addAll(
                "A*"
        );
        algorithmPicker.getSelectionModel().selectFirst();
        algorithmPicker.setPromptText("Algorithm");
        return algorithmPicker;
    }

    private Slider createBlockedSlider() {
        Slider blockedSlider = new Slider();
        blockedSlider.setMin(1);
        blockedSlider.setMax(99);
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
        delaySlider.setValue(15);
        delaySlider.setShowTickLabels(true);
        delaySlider.setShowTickMarks(true);
        delaySlider.setMajorTickUnit(50);
        delaySlider.setMinorTickCount(5);
        delaySlider.setBlockIncrement(1);
        return delaySlider;
    }

    private Node[][] initWorld() {
        Node[][] world = new Node[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                world[i][j] = new Node(0, 0, TILE_SIZE, false);
            }
        }
        return world;
    }

    public boolean determineIfBlocked() {
        Random rand = new Random();
        return rand.nextInt(100) + 1 > percentageBlocked;
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

    public Label getBlockedSliderLabel() {
        return blockedSliderLabel;
    }

    public void setPercentageBlocked(int percentageBlocked) {
        this.percentageBlocked = percentageBlocked;
    }

    public Slider getDelaySlider() {
        return delaySlider;
    }

    public Label getDelaySliderLabel() {
        return delaySliderLabel;
    }

}
