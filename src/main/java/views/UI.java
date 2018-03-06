package views;

import controllers.UiController;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Node;

import java.util.Random;

public class UI extends HBox {

    public final int TILE_SIZE = 10;
    public final int HEIGHT = 100;
    public final int WIDTH = 100;
    //TODO make slider for percentage blocked
    private final int PERCENTAGE_BLOCKED = 40;
    public Node[][] world;
    private Group tileGroup = new Group();
    private UiController controller;

    public UI(UiController controller) {
        this.controller = controller;
    }

    public Parent createContent() {
        world = initWorld();

        GridPane root = new GridPane();
        root.setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        root.getChildren().addAll(tileGroup);

        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {

                Node node = new Node(x, y, TILE_SIZE, determineIfBlocked());
                world[x][y] = node;

                tileGroup.getChildren().add(node);
            }
        }
        return root;
    }

    public Node[][] initWorld() {
        Node[][] world = new Node[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                world[i][j] = new Node(0, 0, TILE_SIZE, false);
            }
        }
        return world;
    }

    private boolean determineIfBlocked() {
        Random rand = new Random();
        return rand.nextInt(100) + 1 > PERCENTAGE_BLOCKED;
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
}
