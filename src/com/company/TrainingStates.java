package com.company;

import java.util.ArrayList;

/**
 * Created by David on 10/11/2016.
 */
public class TrainingStates {

    private ArrayList<ArrayList<Vector>> h;
    private ArrayList<ArrayList<Vector>> z;
    private ArrayList<ArrayList<Vector>> delta; //0 - ij 1 - ji

    public TrainingStates() {
        h = new ArrayList<>(2);
        z = new ArrayList<>(2);
        delta = new ArrayList<>(2);
    }

    public Vector getH (int numberOfNetwork, int trainingStep) {
        return h.get(numberOfNetwork).get(trainingStep);
    }

    public void setH (int numberOfNetwork, int trainingStep, Vector vector) {
        h.get(numberOfNetwork).set(trainingStep, vector);
    }

    public Vector getZ (int numberOfNetwork, int trainingStep) {
        return z.get(numberOfNetwork).get(trainingStep);
    }

    public void setZ (int numberOfNetwork, int trainingStep, Vector vector) {
        z.get(numberOfNetwork).set(trainingStep, vector);
    }

    public Vector getDelta (int numberOfNetwork, int trainingStep) {
        return delta.get(numberOfNetwork).get(trainingStep);
    }

    public void setDelta (int numberOfNetwork, int trainingStep, Vector vector) {
        delta.get(numberOfNetwork).set(trainingStep, vector);
    }
}
