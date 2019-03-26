package models.algorithms;

import javafx.scene.paint.Color;
import models.Node;

import java.util.*;
import java.util.stream.Collectors;

public class BFS extends SearchAlgorithm {

    private BitSet visited;
    private Queue<Node> queue;


    @Override
    public void search(Node[][] world, Node start, Node end) {

        visited = new BitSet(world.length * world[0].length);
        queue = new ArrayDeque<>();

        Node currentNode = start;

        boolean goalFound = currentNode.equals(end);
        boolean searchExhausted = false;

        while(!goalFound && !searchExhausted) {

            currentNode.updateColor(Color.LIGHTGREEN);
            sleep();

            visited.set(getNodeIndex(world.length, currentNode));
            expandNeighbors(world, currentNode);

            currentNode = queue.poll();

            goalFound = visited.get(getNodeIndex(world.length,end));
            searchExhausted = currentNode == null;
        }

    }

    private int getNodeIndex(int worldLength, Node node) {
        return node.getGridX() * worldLength + node.getGridY();
    }

    private void expandNeighbors(Node[][] world, Node currentNode) {
        List<Node> neighbors = getNeighbors(currentNode,world);
        for(Node neighbor : neighbors) {
            int index = getNodeIndex(world.length,neighbor);
            if(!visited.get(index)) {
                queue.add(neighbor);
                visited.set(index);
                currentNode.updateColor(Color.LIGHTGREEN);
            }
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

}
