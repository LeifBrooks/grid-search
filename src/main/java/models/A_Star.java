package models;

import controllers.SearchAlgorithm;
import javafx.scene.paint.Color;

import java.util.*;

public class A_Star extends SearchAlgorithm {

    private static final int DIAGONAL_COST = 14;
    private static final int ORTHOGONAL_COST = 10;
    private static final int HEURISTIC_MULTIPLIER = 15;
    private PriorityQueue<Node> openSet;
    private Set<Node> closedSet;

    public A_Star() {
        openSet = new PriorityQueue<>();
        closedSet = new HashSet<>();
    }

    /**
     * Current node is start node add to closed set.
     * add current node neighbors to openset with h,g,f costs
     * search nodes in openset for smallest fCost, if tie, use hCost, if another tie, random select
     * new node is current node and is added to closedset
     * new neighbors added to openset.
     */
    @Override
    public void search(Node[][] world, Point start, Point end) {
        Node startNode = world[start.getX()][start.getY()];
        Node currentNode = startNode;
        Node endNode = world[end.getX()][end.getY()];
        boolean goalFound = startNode.equals(endNode);
        boolean searchExhausted = false;
        while (!goalFound && !searchExhausted) {

            closedSet.add(currentNode);
            openSet.addAll(expandNeighbors(world, currentNode, end));

            goalFound = openSet.contains(endNode);
            searchExhausted = openSet.isEmpty();

            currentNode = openSet.poll();
        }

        if (goalFound) {
            ArrayList<Node> path = getPath(endNode);
            drawPath(path);
        }

        openSet.clear();
        closedSet.clear();
    }

    private List<Node> expandNeighbors(Node[][] world, Node currentNode, Point endPoint) {

        List<Node> neighbors = new ArrayList<>();
        Point p = currentNode.getPointCoordinate();

        currentNode.setFill(Color.RED);

        int x = p.getX();
        int y = p.getY();

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

                if (!isOnMap(neighborX, neighborY, world.length, world[0].length)) {
                    continue;
                }

                Node neighbor = world[neighborX][neighborY];

                if (neighbor.isOpen() && !closedSet.contains(neighbor)) {
                    int parentGCost = currentNode.getgCost();
                    int gCost = parentGCost + calculateMovementCost(xOffset, yOffset);
                    //update costs/parent if its cheaper to get to neighbor via currentNode
                    if (openSet.contains(neighbor)) {
                        if (gCost < neighbor.getgCost()) {
                            neighbor.setParent(currentNode);
                            neighbor.setgCost(gCost);
                            neighbor.sethCost(calculateHCost(neighborX, neighborY, endPoint, HEURISTIC_MULTIPLIER));
                            neighbor.calculateFCost();
                        }
                    } else {
                        neighbor.setParent(currentNode);
                        neighbor.setgCost(gCost);
                        neighbor.sethCost(calculateHCost(neighborX, neighborY, endPoint, HEURISTIC_MULTIPLIER));
                        neighbor.calculateFCost();
                        neighbors.add(neighbor);

                        neighbor.setFill(Color.RED);


                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

        currentNode.setFill(Color.MEDIUMPURPLE);


        for (Node neighbor : neighbors) {

            neighbor.setFill(Color.MEDIUMPURPLE);


        }
        return neighbors;
    }

    public double calculateHCost(int x1, int y1, Point end, int HEURISTIC_MULTIPLIER) {
        return calculateEuclideanDistance(x1, y1, end.getX(), end.getY()) * HEURISTIC_MULTIPLIER;
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

    private boolean isOnMap(int x, int y, int height, int width) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    private void drawPath(ArrayList<Node> path) {
        for (Node node : path) {
            node.setFill(Color.LIGHTGREEN);
        }
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
