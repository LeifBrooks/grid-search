package models.algorithms;

import javafx.scene.paint.Color;
import models.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DFS extends SearchAlgorithm {

    Map<Node,List<Node>> adjacency;
    BitSet visited;

    @Override
    public void search(Node[][] world, Node start, Node end) {

        Node currentNode = start;

        boolean goalFound = currentNode.equals(end);
        boolean searchExhausted = false;

        visited = new BitSet(world.length * world[0].length);
        Stack<Node> stack = new Stack<>();
        adjacency = new HashMap<>();

        while(!goalFound && !searchExhausted) {

            currentNode.updateColor(Color.LIGHTGREEN);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            markAsVisited(world.length,visited,currentNode);
            stack.push(currentNode);
            if(!adjacency.containsKey(currentNode)) {
                adjacency.put(currentNode,getNeighbors(currentNode,world));
            }

            currentNode = getNextNode(world.length, currentNode, stack);

            goalFound = currentNode != null && currentNode.equals(end);
            searchExhausted = stack.isEmpty();
        }

    }

    private Node getNextNode(int n, Node currentNode, Stack<Node> stack) {
        Node nextNode = null;
        while(!stack.isEmpty() && nextNode == null) {
            List<Node> neighbors = adjacency.get(currentNode);
            for(Node neighbor : neighbors) {
                nextNode = neighbor;
                if (hasBeenVisited(n, visited, nextNode)) {
                    nextNode = null;
                } else {
                    break;
                }
            }

            if (nextNode == null) {
                currentNode = stack.pop();
            }
        }
        return nextNode;
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

    private boolean hasBeenVisited(int n, BitSet visited, Node node) {
        return visited.get(node.getGridX() * n + node.getGridY());
    }

    private void markAsVisited(int n, BitSet visited, Node node) {
        visited.set(node.getGridX() * n + node.getGridY());
    }

}
