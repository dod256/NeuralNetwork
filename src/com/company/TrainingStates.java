package com.company;

import java.util.ArrayList;

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

    public TrainingStates() {
        h = new ArrayList<>(2);
        z = new ArrayList<>(2);
        delta = new ArrayList<>(2);
    }

    public Vector getH (int numberOfNetwork, int trainingStep, int layer) {
        return h.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setH (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        h.get(numberOfNetwork).get(layer).set(trainingStep, vector);
    }

    public Vector getZ (int numberOfNetwork, int trainingStep, int layer) {
        return z.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setZ (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        z.get(numberOfNetwork).get(layer).set(trainingStep, vector);
    }

    public Vector getDelta (int numberOfNetwork, int trainingStep, int layer) {
        return delta.get(numberOfNetwork).get(trainingStep).get(layer);
    }

    public void setDelta (int numberOfNetwork, int trainingStep, int layer, Vector vector) {
        delta.get(numberOfNetwork).get(layer).set(trainingStep, vector);
    }
}
