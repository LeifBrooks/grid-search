package models.algorithms;

import javafx.scene.paint.Color;
import models.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DFS extends SearchAlgorithm {

    private Map<Node,List<Node>> adjacency;
    private BitSet visited;
    private Stack<Node> stack;


    @Override
    public void search(Node[][] world, Node start, Node end) {

        visited = new BitSet(world.length * world[0].length);
        stack = new Stack<>();
        adjacency = new HashMap<>();

        Node currentNode = start;

        boolean goalFound = currentNode.equals(end);
        boolean searchExhausted = false;

        while(!goalFound && !searchExhausted) {

            currentNode.updateColor(Color.LIGHTGREEN);
            sleep();

            visited.set(getNodeIndex(world.length, currentNode));
            stack.push(currentNode);
            expandNeighbors(world, currentNode);

            currentNode = getNextNode(world.length, currentNode);

            goalFound = currentNode != null && currentNode.equals(end);
            searchExhausted = stack.isEmpty();
        }

    }

    private int getNodeIndex(int worldLength, Node node) {
        return node.getGridX() * worldLength + node.getGridY();
    }

    private void expandNeighbors(Node[][] world, Node currentNode) {
        if(!adjacency.containsKey(currentNode)) {
            adjacency.put(currentNode,getNeighbors(currentNode,world));
        }
    }

    private List<Node> getNeighbors(Node currentNode, Node[][] world) {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                // Do not add current node
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int neighborX = currentNode.getGridX() + xOffset;
                int neighborY =  currentNode.getGridY() + yOffset;

                if (!isOnMap(neighborX, neighborY, world)) {
                    continue;
                }
                neighbors.add(world[neighborX][neighborY]);
            }
        }
        return neighbors.stream().filter(Node::isOpen).collect(Collectors.toList());
    }

    private boolean isOnMap(int x, int y, Node[][] world) {
        return x >= 0 && y >= 0 && x < world.length && y < world[0].length;
    }

    private Node getNextNode(int worldLength, Node currentNode) {
        Node nextNode = null;
        while (!stack.isEmpty() && nextNode == null) {
            List<Node> neighbors = adjacency.get(currentNode);
            for (Node neighbor : neighbors) {
                if (!visited.get(getNodeIndex(worldLength, neighbor))) {
                    nextNode = neighbor;
                    break;
                }
            }

            if (nextNode == null) {
                currentNode = stack.pop();
            }
        }
        return nextNode;
    }

}
