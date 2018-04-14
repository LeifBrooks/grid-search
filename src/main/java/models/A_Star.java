package models;

import javafx.scene.paint.Color;

import java.util.*;

public class A_Star extends SearchAlgorithm {

    private final int DIAGONAL_COST = 14;
    private final int ORTHOGONAL_COST = 10;
    private double heuristicMultiplier = 12;
    private PriorityQueue<Node> frontier;
    private Set<Node> closedSet;

    public A_Star() {
        frontier = new PriorityQueue<>();
        closedSet = new HashSet<>();
    }

    @Override
    public void search(Node[][] world, Node startNode, Node endNode) {
        Node currentNode = startNode;
        boolean goalFound = startNode.equals(endNode);
        boolean searchExhausted = false;
        while (!goalFound && !searchExhausted) {

            closedSet.add(currentNode);
            frontier.addAll(expandNeighbors(world, currentNode, endNode));

            goalFound = frontier.contains(endNode);
            searchExhausted = frontier.isEmpty();

            currentNode = frontier.poll();
        }

        if (goalFound) {
            ArrayList<Node> path = getPath(endNode);
            drawPath(path);
        }

        frontier.clear();
        closedSet.clear();
    }

    private List<Node> expandNeighbors(Node[][] world, Node currentNode, Node endNode) {

        List<Node> neighbors = new ArrayList<>();

    	currentNode.updateColor(Color.RED);

        Point currentPoint = currentNode.getPointCoordinate();
        int x = currentPoint.getX();
        int y = currentPoint.getY();

        //offset is used to get all neighbor nodes relative to the currentNode
        //ie: up, down, left, right, diagonals.
        //this is used to determine the movement cost from currentNode to neighbor

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                // Do not add current node
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int neighborX = x + xOffset;
                int neighborY = y + yOffset;

                if (!isOnMap(neighborX, neighborY, world)) {
                    continue;
                }

                Node neighbor = world[neighborX][neighborY];

                int gCost = currentNode.getgCost() + calculateMovementCost(xOffset, yOffset);

                if (isNeighborUpdatable(neighbor, gCost)) {
                    updateNeighbor(neighbor, currentNode, gCost, endNode);
                    neighbors.add(neighbor);
                	neighbor.updateColor(Color.RED);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        currentNode.updateColor(Color.MEDIUMPURPLE);
        
        neighbors.forEach(neighbor -> neighbor.updateColor(Color.MEDIUMPURPLE));
        return neighbors;
    }

    private boolean isNeighborUpdatable(Node neighbor, int gCost) {
        return isNeighborExpandable(neighbor) && (!frontier.contains(neighbor) || gCost < neighbor.getgCost());
    }

    private boolean isNeighborExpandable(Node neighbor) {
        return neighbor.isOpen() && !closedSet.contains(neighbor);
    }

    private void updateNeighbor(Node neighbor, Node currentNode, int gCost, Node endNode) {
        neighbor.setParent(currentNode);
        neighbor.setgCost(gCost);
        neighbor.sethCost(calculateHCost(neighbor.getPointCoordinate().getX(), neighbor.getPointCoordinate().getY(), endNode));
        neighbor.calculateFCost();
    }

    public double calculateHCost(int x1, int y1, Node end) {
        int x2 = end.getPointCoordinate().getX();
        int y2 = end.getPointCoordinate().getY();
        return calculateEuclideanDistance(x1, y1, x2, y2) * this.heuristicMultiplier;
    }

    private double calculateEuclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    private int calculateMovementCost(int xOffset, int yOffset) {
        return isDiagonalNeighbor(xOffset, yOffset) ? DIAGONAL_COST : ORTHOGONAL_COST;
    }

    private boolean isDiagonalNeighbor(int xOffset, int yOffset) {
        return Math.abs(xOffset) + Math.abs(yOffset) == 2;
    }

    private boolean isOnMap(int x, int y, Node[][] world) {
        return x >= 0 && y >= 0 && x < world.length && y < world[0].length;
    }

    private void drawPath(ArrayList<Node> path) {
        path.forEach(node -> node.updateColor(Color.LIGHTGREEN));
    }

    public void setHeuristicMultiplier(double heuristicMultiplier) {
        this.heuristicMultiplier = heuristicMultiplier;
    }

    private ArrayList<Node> getPath(Node end) {
        Node parent = end.getParentNode();
        ArrayList<Node> path = new ArrayList<>();
        path.add(end);
        while (parent != null) {
            path.add(parent);
            parent = parent.getParentNode();
        }
        Collections.reverse(path);
        return path;
    }
}
