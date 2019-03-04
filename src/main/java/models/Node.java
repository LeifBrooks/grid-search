package models;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Node extends Rectangle {

    private boolean open;
    private Point coordinate;

    public Node(Point point, int tileSize, boolean open) {
        setWidth(tileSize);
        setHeight(tileSize);
        this.open = open;
        this.coordinate = point;
        setFill(open ? Color.WHITE : Color.NAVY);
        setStroke(Color.BLACK);

        relocate(point.getX() * tileSize, point.getY() * tileSize);
    }

    @Override
    public boolean equals(Object obj) {
        Node o = (Node) obj;
        return this.coordinate.equals(o.coordinate);
    }

    public void resetNode() {
        setFill(open ? Color.WHITE : Color.NAVY);
    }

    public boolean isOpen() {
        return open;
    }

    public void highlight() {
        setFill(Color.GOLD);
    }

    public Point getPointCoordinate() {
        return this.coordinate;
    }

    public void setOpen(boolean open) {
        this.open = open;
        setFill(open ? Color.WHITE : Color.NAVY);
    }

    public void updateColor(Color color) {
        Platform.runLater(() -> {
            try {
                this.setFill(color);
            } finally {
            }
        });
    }
}
