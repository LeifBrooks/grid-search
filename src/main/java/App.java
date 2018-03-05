import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;


public class App extends Application {

    public static final int TILE_SIZE = 10;
    public static final int HEIGHT = 100;
    public static final int WIDTH = 100;

    private Point start;
    private Point end;
    //TODO make slider for percentage blocked
    private final int PERCENTAGE_BLOCKED = 54;
    private Group tileGroup = new Group();
    private Node[][] world;
    private Searcher algo = new A_Star();

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        world = initWorld();

        GridPane root = new GridPane();
        root.setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        root.getChildren().addAll(tileGroup);

        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {

                Node node = new Node(x, y, determineIfBlocked());
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, new LocationSelectionHandler());
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
                world[i][j] = new Node(0, 0, false);
            }
        }
        return world;
    }

    private boolean determineIfBlocked() {
        Random rand = new Random();
        return rand.nextInt(100) + 1 > PERCENTAGE_BLOCKED;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("A*");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void resetWorld() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                world[i][j].resetNode();
            }
        }
    }

    private class LocationSelectionHandler implements EventHandler {
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
    }


}
