package com.company;

import java.util.ArrayList;
import java.util.List;

import static com.company.Helper.mutate;

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

        xi.add(Helper.getRandVector(numberOfData));
        xj.add(xj.get(0));
        lij.add(1.0);
        for (int i = 1; i < numberOfData; i++) {
            Vector newRandVector = Helper.getRandVector(numberOfData);

            Vector mutatatedNewRandVector = mutate(newRandVector);

            Vector oldMean = xj.get(i - 1);
            List<Double> newMean = new ArrayList<>();
            for (int j = 0; j < oldMean.getSize(); j++) {
                newMean.add((oldMean.getValue(j)*(i-1) + mutatatedNewRandVector.getValue(j))/i);
            }

            xi.add(mutatatedNewRandVector);
            xj.add(new Vector(newMean));
            if (mutatatedNewRandVector.equals(newRandVector)) {
                lij.add(1.0);
            } else {
                lij.add(-1.0);
            }
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
