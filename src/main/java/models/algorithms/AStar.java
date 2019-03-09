package models.algorithms;

import javafx.scene.paint.Color;
import models.AStarNode;
import models.Node;

import java.util.*;

public class AStar extends SearchAlgorithm {

    private final int DIAGONAL_COST = 14;
    private final int ORTHOGONAL_COST = 10;
    private double heuristicMultiplier = 13;
    private PriorityQueue<AStarNode> frontier;
    private Set<AStarNode> closedSet;

    public AStar() {
        frontier = new PriorityQueue<>();
        closedSet = new HashSet<>();
    }

    @Override
    public void search(Node[][] world, Node startNode, Node endNode) {
        AStarNode currentNode = new AStarNode(startNode);
        AStarNode endNodeWrapper = new AStarNode(endNode);
        boolean goalFound = currentNode.equals(endNodeWrapper);
        boolean searchExhausted = false;
        while (!goalFound && !searchExhausted) {

            closedSet.add(currentNode);
            frontier.addAll(expandNeighbors(world, currentNode, endNodeWrapper));

            goalFound = frontier.contains(endNodeWrapper);
            searchExhausted = frontier.isEmpty();

            currentNode = frontier.poll();
        }

        if (goalFound) {
            //currentNode will be the goal
            ArrayList<AStarNode> path = getPath(currentNode);
            drawPath(path);
        }

        frontier.clear();
        closedSet.clear();
    }

    private List<AStarNode> expandNeighbors(Node[][] world, AStarNode currentNode, AStarNode endNode) {

    	currentNode.updateColor(Color.RED);

        List<AStarNode>  neighbors = getNeighbors(currentNode, world);
        Iterator<AStarNode> iter = neighbors.iterator();

        while(iter.hasNext()) {
            AStarNode neighbor = iter.next();
            int gCost = currentNode.getgCost() + calculateMovementCost(currentNode, neighbor);

            if (isNeighborUpdatable(neighbor, gCost)) {
                updateNeighbor(neighbor, currentNode, gCost, endNode);
                neighbor.updateColor(Color.RED);
                sleep();
            } else {
                iter.remove();
            }
        }

        currentNode.updateColor(Color.MEDIUMPURPLE);
        
        neighbors.forEach(neighbor -> neighbor.updateColor(Color.MEDIUMPURPLE));
        return neighbors;
    }

    private List<AStarNode> getNeighbors(AStarNode currentNode, Node[][] world) {
        ArrayList<AStarNode> neighbors = new ArrayList<>();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                // Do not add current node
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int neighborX = currentNode.getX() + xOffset;
                int neighborY =  currentNode.getY() + yOffset;

                if (!isOnMap(neighborX, neighborY, world)) {
                    continue;
                }
                neighbors.add(new AStarNode(world[neighborX][neighborY]));
            }
        }
        return neighbors;
    }

    private boolean isNeighborUpdatable(AStarNode neighbor, int gCost) {
        return isNeighborExpandable(neighbor) && (!frontier.contains(neighbor) || gCost < neighbor.getgCost());
    }

    private boolean isNeighborExpandable(AStarNode neighbor) {
        return neighbor.isOpen() && !closedSet.contains(neighbor);
    }

    private void updateNeighbor(AStarNode neighbor, AStarNode currentNode, int gCost, AStarNode endNode) {
        neighbor.setParent(currentNode);
        neighbor.setgCost(gCost);
        neighbor.sethCost(calculateHCost(neighbor.getX(), neighbor.getY(), endNode));
        neighbor.calculateFCost();
    }

    public double calculateHCost(int x1, int y1, AStarNode end) {
        int x2 = end.getX();
        int y2 = end.getY();
        return calculateEuclideanDistance(x1, y1, x2, y2) * this.heuristicMultiplier;
    }

    private double calculateEuclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    private int calculateMovementCost(AStarNode currentNode, AStarNode neighbor) {
        int xOffset = Math.abs(currentNode.getX() - neighbor.getX());
        int yOffset = Math.abs(currentNode.getY() - neighbor.getY());
        return isDiagonalNeighbor(xOffset, yOffset) ? DIAGONAL_COST : ORTHOGONAL_COST;
    }

    private boolean isDiagonalNeighbor(int xOffset, int yOffset) {
        return xOffset + yOffset == 2;
    }

    private boolean isOnMap(int x, int y, Node[][] world) {
        return x >= 0 && y >= 0 && x < world.length && y < world[0].length;
    }

    private void drawPath(ArrayList<AStarNode> path) {
        path.forEach(node -> node.updateColor(Color.LIGHTGREEN));
    }

    public void setHeuristicMultiplier(double heuristicMultiplier) {
        this.heuristicMultiplier = heuristicMultiplier;
    }

    private ArrayList<AStarNode> getPath(AStarNode end) {
        AStarNode parent = end.getParentNode();
        ArrayList<AStarNode> path = new ArrayList<>();
        path.add(end);
        while (parent != null) {
            path.add(parent);
            parent = parent.getParentNode();
        }
        Collections.reverse(path);
        return path;
    }
}
