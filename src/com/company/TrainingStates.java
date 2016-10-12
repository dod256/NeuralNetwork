package com.company;

import java.util.ArrayList;
import java.util.List;

public class TrainingStates {

    private ArrayList<ArrayList<ArrayList<Vector>>> h;
    private ArrayList<ArrayList<ArrayList<Vector>>> z;
    private ArrayList<ArrayList<ArrayList<Vector>>> delta; //0 - ij 1 - ji

    private ArrayList<Double> J;

    public double getJ (int t) {
        return J.get(t);
    }

    public void addJ (double newJ) {
        J.add(newJ);
    }

    public TrainingStates(int numberOfLayers, List<Integer>sizeOfLayers, int iterativeNumber) {
        h = new ArrayList<>();
        h.add(new ArrayList<>());
        h.add(new ArrayList<>());
        z = new ArrayList<>();
        z.add(new ArrayList<>());
        z.add(new ArrayList<>());
        delta = new ArrayList<>();
        delta.add(new ArrayList<>());
        delta.add(new ArrayList<>());
        J = new ArrayList<>();
        for (int i = 0; i < iterativeNumber; i++) {
            J.add(0.0);
            h.get(0).add(new ArrayList<>());
            h.get(1).add(new ArrayList<>());
            z.get(0).add(new ArrayList<>());
            z.get(1).add(new ArrayList<>());
            delta.get(0).add(new ArrayList<>());
            delta.get(1).add(new ArrayList<>());
            for (int j = 0; j < numberOfLayers; j++) {
                h.get(0).get(i).add(new Vector(sizeOfLayers.get(j)));
                h.get(1).get(i).add(new Vector(sizeOfLayers.get(j)));
                z.get(0).get(i).add(new Vector(sizeOfLayers.get(j)));
                z.get(1).get(i).add(new Vector(sizeOfLayers.get(j)));
                delta.get(0).get(i).add(new Vector(sizeOfLayers.get(j)));
                delta.get(1).get(i).add(new Vector(sizeOfLayers.get(j)));
            }
        }
    }

    public Vector getH (int numberOfNetwork, int trainingStep, int layer) {
        return h.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setH (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        h.get(numberOfNetwork).get(trainingStep).set(layer, vector);
    }

    public Vector getZ (int numberOfNetwork, int trainingStep, int layer) {
        return z.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setZ (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        z.get(numberOfNetwork).get(trainingStep).set(layer, vector);
    }

    public Vector getDelta (int numberOfNetwork, int trainingStep, int layer) {
        return delta.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setDelta (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        delta.get(numberOfNetwork).get(trainingStep).set(layer, vector);
    }
}
