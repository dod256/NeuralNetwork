package com.company;

import java.util.ArrayList;
import java.util.Random;

public class DataSet {

    private int numberOfData;

    private int inputSize;

    ArrayList<Vector> xi;
    ArrayList<Vector> xj;
    ArrayList<Double> lij;

    public DataSet(int numberOfData, int inputSize) {
        this.numberOfData = numberOfData;
        this.inputSize = inputSize;
        xi = new ArrayList<>();
        xj = new ArrayList<>();
        lij = new ArrayList<>();
        for (int i = 0; i < numberOfData; i++) {
            xi.add(new Vector(inputSize));
            xj.add(new Vector(inputSize));
            lij.add(1.0);
        }
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public int getInputSize() {
        return inputSize;
    }

    public Vector getXi (int t) {
        return xi.get(t);
    }

    public Vector getXj (int t) {
        return xj.get(t);
    }

    public double getLij (int t) {
        return lij.get(t);
    }
}
