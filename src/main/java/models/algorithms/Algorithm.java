package models.algorithms;

import java.util.HashMap;
import java.util.Map;

public enum Algorithm {
    A_STAR("A*"),
    DFS("Depth First Search");

    private String name;

    private static Map<String, Algorithm> algorithms = new HashMap<>();

    static {
        for (Algorithm algorithm : Algorithm.values()) {
            algorithms.put(algorithm.name, algorithm);
        }
    }

    Algorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Algorithm stringToAlgorithm(String algorithm) {
        return algorithms.get(algorithm);
    }
}


