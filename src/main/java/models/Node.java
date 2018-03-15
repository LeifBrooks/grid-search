package models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Node extends Rectangle implements Comparable {

    private boolean open;
    private Point coordinate;
    private int gCost; //cost from start Node to this Node.
    private double hCost; //cost from this Node to End Node.
    private double fCost; //Sum of gCost and hCost
    private Node parent;

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
        setgCost(0);
        sethCost(0);
        setfCost(0);
        setParent(null);
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

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public double gethCost() {
        return hCost;
    }

    public void sethCost(double hCost) {
        this.hCost = hCost;
    }

    public double getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void calculateFCost() {
        this.fCost = gCost + hCost;
    }

    public Node getParentNode() {
        return parent;
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        if (this.fCost < other.fCost) {
            return -1;
        } else if (this.fCost == other.fCost) {
            if (this.hCost < other.hCost) {
                return -1;
            } else if (this.hCost == other.hCost) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public void setOpen(boolean open) {
        this.open = open;
        setFill(open ? Color.WHITE : Color.NAVY);
    }

/*    public void updateColor(Color color) {
        Platform.runLater(() -> {
            try {
                this.setFill(color);
            } finally {
            }
        });
    }*/
}
