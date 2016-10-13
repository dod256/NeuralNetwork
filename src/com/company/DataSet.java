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

        xi.add(Helper.getRandVector(inputSize));
        xj.add(xi.get(0));
        lij.add(1.0);
        Vector mean = new Vector(inputSize);
        for (int i = 1; i < numberOfData; i++) {
            Vector newRandVector = Helper.getRandVector(inputSize);

            Vector mutatatedNewRandVector = mutate(newRandVector);

            mean = MyMath.multiplyDV(i - 1, mean);
            mean = MyMath.additionVV(mean, mutatatedNewRandVector);
            mean = MyMath.multiplyDV(1.0 / i, mean);

            mean = Helper.getRandVector(inputSize);

            xi.add(mutatatedNewRandVector);
            xj.add(mean);
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
