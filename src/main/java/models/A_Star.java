package models;

import controllers.Searcher;
import javafx.scene.paint.Color;

import java.util.*;

public class A_Star implements Searcher {

    public static final int DIAGONAL_COST = 14;
    public static final int ORTHOGONAL_COST = 10;
    public static final int HEURISTIC_MULTIPLIER = 14;
    public final int DELAY = 5;
    private PriorityQueue<Node> openSet;
    private Set<Node> closedSet;

    public A_Star() {
        openSet = new PriorityQueue<>();
        closedSet = new HashSet<>();
    }

    //current node is start node add to closed set
    //add current node neighbors to openset with h,g,f costs
    //search nodes in openset for smallest fCost, if tie, use hCost, if another tie, random select
    //new node is current node and is added to closedset
    //new neighbors added to openset.
    @Override
    public void search(Node[][] world, Point start, Point end) {
        Node startNode = world[start.getX()][start.getY()];
        Node currentNode = startNode;
        Node endNode = world[end.getX()][end.getY()];
        boolean goalFound = startNode.equals(endNode);
        boolean searchExhausted = false;
        while (!goalFound && !searchExhausted) {

            closedSet.add(currentNode);
            openSet.addAll(getOpenNeighbors(world, currentNode, end));

            goalFound = openSet.contains(endNode);
            searchExhausted = openSet.isEmpty();

            currentNode = openSet.poll();
        }

        if (goalFound) {
            ArrayList<Node> path = getPath(startNode, endNode);
            drawPath(path);
        }

    }

    public void drawPath(ArrayList<Node> path) {
        for (Node node : path) {
            node.setFill(Color.LIGHTGREEN);
        }
    }

    public ArrayList<Node> getPath(Node start, Node end) {
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

    //TODO add costs to nodes
    public List<Node> getOpenNeighbors(Node[][] world, Node currentNode, Point end) {
        List<Node> neighbors = new ArrayList<>();
        Point p = currentNode.getPointCoordinate();
        currentNode.setFill(Color.RED);
        int x = p.getX();
        int y = p.getY();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                // Do not add current node
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                //TODO check for diagonal and add costs to neighbor. orthogonal = 10, diagonal = 14
                int neighborX = x + xOffset;
                int neighborY = y + yOffset;

                if (!isOnMap(neighborX, neighborY, world.length, world[0].length)) {
                    continue;
                }

                Node neighbor = world[neighborX][neighborY];

                if (neighbor.isOpen() && !closedSet.contains(neighbor)) {
                    int parentGCost = currentNode.getgCost();
                    int gCost = parentGCost + calculateMovementCost(xOffset, yOffset);
                    if (openSet.contains(neighbor)) {
                        //UPDATE COSTS
                        if (gCost < neighbor.getgCost()) {
                            neighbor.setParent(currentNode);
                            neighbor.setgCost(gCost);
                            neighbor.sethCost(calculateHCost(neighborX, neighborY, end, HEURISTIC_MULTIPLIER));
                            neighbor.calculateFCost();
                        }
                    } else {
                        neighbor.setParent(currentNode);
                        neighbor.setgCost(gCost);
                        neighbor.sethCost(calculateHCost(neighborX, neighborY, end, HEURISTIC_MULTIPLIER));
                        neighbor.calculateFCost();
                        neighbors.add(neighbor);
                        neighbor.setFill(Color.MEDIUMPURPLE);
                        try {
                            Thread.sleep(DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        currentNode.setFill(Color.MEDIUMPURPLE);
        return neighbors;
    }

    public double calculateHCost(int x1, int y1, Point end, int HEURISTIC_MULTIPLIER) {
        int x2 = end.getX();
        int y2 = end.getY();
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) * HEURISTIC_MULTIPLIER;
    }

    public int calculateMovementCost(int xOffset, int yOffset) {
        return isDiagonalNeighbor(xOffset, yOffset) ? DIAGONAL_COST : ORTHOGONAL_COST;
    }

    public boolean isDiagonalNeighbor(int xOffset, int yOffset) {
        return Math.abs(xOffset) + Math.abs(yOffset) == 2;
    }

    public boolean isOnMap(int x, int y, int height, int width) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

}
