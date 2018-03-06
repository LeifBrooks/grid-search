package controllers;

import java.util.HashMap;
import java.util.Map;

public enum Algorithm {
    A_STAR("A*");

    private String type;

    private static Map<String, Algorithm> map = new HashMap<>();

    static {
        for (Algorithm algorithm : Algorithm.values()) {
            map.put(algorithm.type, algorithm);
        }
    }

    Algorithm(String type) {
        this.type = type;
    }

    public static Algorithm stringToAlgorithm(String algorithm) {
        return map.get(algorithm);
    }
}


