package models;

import javafx.scene.paint.Color;

public class AStarNode implements Comparable {

    private AStarNode parent;
    private Node uiNode; //reference to UI node

    private int gCost; //cost from start Node to this Node.
    private double hCost; //cost from this Node to End Node.
    private double fCost; //Sum of gCost and hCost

    public AStarNode(Node node) {
        this.uiNode = node;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
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

    public void calculateFCost() {
        this.fCost = gCost + hCost;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public AStarNode getParentNode() {
        return parent;
    }

    public void updateColor(Color color) {
        this.uiNode.updateColor(color);
    }

    public Point getPointCoordinate() {
        return this.uiNode.getPointCoordinate();
    }

    public boolean isOpen() {
        return this.uiNode.isOpen();
    }

    public Node getUiNode() {
        return this.uiNode;
    }

    @Override
    public boolean equals(Object obj) {
        Object o = ((AStarNode) obj).getUiNode();
        return this.uiNode.equals(o);
    }

    @Override
    public int compareTo(Object o) {
        AStarNode other = (AStarNode) o;
        if (this.fCost < other.fCost) {
            return -1;
        } else if (this.fCost == other.fCost) {
            return Double.compare(this.hCost, other.hCost);
        } else {
            return 1;
        }
    }

}
