package models.algorithms;

public class AlgorithmFactory {

    public static SearchAlgorithm makeAlgorithm(Algorithm type) {
        switch (type) {
            case A_STAR:
                return new AStar();
            case DFS:
                return new DFS();
            case BFS:
                return new BFS();
            default:
                return null;
        }
    }
}
