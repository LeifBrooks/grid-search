package controllers;

import models.A_Star;

public class AlgorithmFactory {

    public static SearchAlgorithm makeAlgorithm(Algorithm type) {
        switch (type) {
            case A_STAR:
                return new A_Star();
            default:
                return null;
        }
    }
}
