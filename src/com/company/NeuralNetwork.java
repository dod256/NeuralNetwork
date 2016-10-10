package com.company;

import java.util.ArrayList;

/**
 * Created by David on 10/9/2016.
 */
public class NeuralNetwork {

    public int numberOfLayers;
    public ArrayList<Integer> sizeOfLayers;
    private int iterativeNumber;

    private TrainingStates states;
    
    private ArrayList<Matrix> w;
    private ArrayList<Vector> b;
    private ArrayList<Matrix> JW;
    private ArrayList<Vector> Jb;
    private ArrayList<Double> J;



    private double eps, lambda, mu, tau, beta, c;

    public void init (int numberOfLayers, int sizeOfLayers) {
        J = new ArrayList<>();
        states = new TrainingStates();
        //set sixes for all arrayLists
    }

    private void forwardPropagation () {
        for (int i = 1; i < numberOfLayers; i++) {
            states.setZ(0, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(0, i - 1)), b.get(i)));
            states.setH(0, i, MyMath.applyTanh(states.getZ(0, i)));
            states.setZ(1, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(1, i - 1)), b.get(i)));
            states.setH(1, i, MyMath.applyTanh(states.getZ(0, i)));
        }
    }

    public void trainNetwork () {
        DataSet x = new DataSet();
        int N = 0;
        for (int i = 0; i < x.getSize(); i++) {
            for (int j = 0; j < 1; j++) {
                for (int t = 0; t < iterativeNumber; t++) {
                    states.setH(0, 0, x.getXi());
                    states.setH(1, 0, x.getXj());
                    double lij = x.getLij();
                    forwardPropagation();
                    // Computing gradient
                    c = 1 - lij * (tau - MyMath.squaredEuclideanDistance(states.getH(0, numberOfLayers - 1), states.getH(1, numberOfLayers - 1)));
                    double gdc = c; //g'(c)
                    states.setDelta(0, numberOfLayers - 1, MyMath.multiplyElementWiseVV(MyMath.multiplyDV(gdc * lij,
                                    MyMath.subtractionVV(states.getH(0, numberOfLayers - 1), states.getH(1, numberOfLayers - 1))),
                            MyMath.applyTanhDerivative(states.getZ(0, numberOfLayers - 1))));
                    states.setDelta(1, numberOfLayers - 1, MyMath.multiplyElementWiseVV(MyMath.multiplyDV(gdc * lij,
                                    MyMath.subtractionVV(states.getH(1, numberOfLayers - 1), states.getH(0, numberOfLayers - 1))),
                            MyMath.applyTanhDerivative(states.getZ(1, numberOfLayers - 1))));
                    for (int cur = numberOfLayers - 2; cur > 0; cur--) {
                        // set delta1[i] and delta2[i]
                    }
                    for (int cur = 1; cur < numberOfLayers; cur++) {
                        //Set JW and Jb
                    }
                    for (int cur = 1; cur < numberOfLayers; cur++) {
                        w.set(cur, MyMath.subtractionMM(w.get(cur), MyMath.multiplyDM(mu, JW.get(cur))));
                        b.set(cur, MyMath.subtractionVV(b.get(cur), MyMath.multiplyDV(mu, Jb.get(cur))));
                    }
                    J.add(0.0);
                    if (t > 0 && Math.abs(J.get(t - 1) - J.get(t)) < eps) {
                        break;
                    }
                }
            }
        }
    }
}
