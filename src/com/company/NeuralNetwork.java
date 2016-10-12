package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    public int numberOfLayers;
    public List<Integer> sizeOfLayers;
    private int iterativeNumber;

    private TrainingStates states;
    private DataSet x;
    
    private List<Matrix> w;
    private List<Vector> b;
    private List<Matrix> JW;
    private List<Vector> Jb;

    private double eps, lambda, mu, tau, beta, c;

    public void init () {
        Random rand = new Random();
        numberOfLayers = 3;
        sizeOfLayers = new ArrayList<>();
        sizeOfLayers.add(5);
        sizeOfLayers.add(3);
        sizeOfLayers.add(6);
        iterativeNumber = 1000;

        w = new ArrayList<>();
        b = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            b.add(new Vector(sizeOfLayers.get(i)));
            if (i > 0) {
                List<List<Double>> values = new ArrayList<>();
                for (int j = 0; j < sizeOfLayers.get(i); j++) {
                    values.add(new ArrayList<>());
                    for (int k = 0; k < sizeOfLayers.get(i - 1); k++) {
                        values.get(j).add(rand.nextDouble() % 10);
                    }
                }
                w.add(new Matrix(values)); //Change to uniform distribution
            } else {
                w.add(new Matrix());
            }
        }

        JW = new ArrayList<>();
        Jb = new ArrayList<>();
        for (int i = 0; i < iterativeNumber; i++) {
            JW.add(new Matrix());
            Jb.add(new Vector());
        }

        states = new TrainingStates(numberOfLayers, sizeOfLayers, iterativeNumber);
        x = new DataSet(10, sizeOfLayers.get(0));

        eps = 0.001;
        lambda = 1;
        mu = 1;
        tau = 1;
        beta = 1;

    }

    private void forwardPropagation (int t) {
        for (int i = 1; i < numberOfLayers; i++) {
            states.setZ(0, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(0, t, i - 1)), b.get(i)));
            states.setH(0, t, i, MyMath.applyTanh(states.getZ(0, t, i)));
            states.setZ(1, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(1, t, i - 1)), b.get(i)));
            states.setH(1, t, i, MyMath.applyTanh(states.getZ(0, t, i)));
        }
    }

    private double g (double z) {
        return (1.0 / beta) * Math.log(1 + Math.exp(beta * z));
    }

    private double gDerivative (double z) {
        double exp = Math.exp(beta * z);
        return exp / (1 + exp);
    }


    public void trainNetwork () {
        init();
        for (int t = 0; t < iterativeNumber; t++) {
            states.setH(0, t, 0, x.getXi(t));
            states.setH(1, t, 0, x.getXj(t));
            double lij = x.getLij(t);
            forwardPropagation(t);
            // Computing gradient
            c = 1 - lij * (tau - MyMath.squaredEuclideanDistance(states.getH(0, t, numberOfLayers - 1), states.getH(1, t, numberOfLayers - 1)));
            double gdc = gDerivative(c);
            states.setDelta(0, t, numberOfLayers - 1, MyMath.multiplyElementWiseVV(MyMath.multiplyDV(gdc * lij,
                            MyMath.subtractionVV(states.getH(0, t, numberOfLayers - 1), states.getH(1, t, numberOfLayers - 1))),
                            MyMath.applyTanhDerivative(states.getZ(0, t, numberOfLayers - 1))));
            states.setDelta(1, t, numberOfLayers - 1, MyMath.multiplyElementWiseVV(MyMath.multiplyDV(gdc * lij,
                                    MyMath.subtractionVV(states.getH(1, t, numberOfLayers - 1), states.getH(0, t, numberOfLayers - 1))),
                            MyMath.applyTanhDerivative(states.getZ(1, t, numberOfLayers - 1))));
            for (int cur = numberOfLayers - 2; cur > 0; cur--) {
                states.setDelta(0, t, cur, MyMath.multiplyElementWiseVV(MyMath.multiplyMV(MyMath.transposeM(w.get(cur + 1)),
                        states.getDelta(0, t, cur + 1)), states.getZ(0, t, cur)));
                states.setDelta(1, t, cur, MyMath.multiplyElementWiseVV(MyMath.multiplyMV(MyMath.transposeM(w.get(cur + 1)),
                        states.getDelta(1, t, cur + 1)), states.getZ(1, t, cur)));
            }
            for (int cur = 1; cur < numberOfLayers; cur++) {
                JW.set(cur, MyMath.multiplyDM(lambda, w.get(cur)));
                Jb.set(cur, MyMath.multiplyDV(lambda, b.get(cur)));
                for (int i = 0; i <= t; i++) {
                    JW.set(cur, MyMath.additionMM(JW.get(cur), MyMath.multiplyVV(states.getDelta(0, i, cur), MyMath.transposeV(states.getH(0, i, cur - 1)))));
                    JW.set(cur, MyMath.additionMM(JW.get(cur), MyMath.multiplyVV(states.getDelta(1, i, cur), MyMath.transposeV(states.getH(1, i, cur - 1)))));
                    Jb.set(cur, MyMath.additionVV(Jb.get(cur), MyMath.additionVV(states.getDelta(0, i, cur), states.getDelta(1, i, cur))));
                }
            }
            for (int cur = 1; cur < numberOfLayers; cur++) {
                w.set(cur, MyMath.subtractionMM(w.get(cur), MyMath.multiplyDM(mu, JW.get(cur))));
                b.set(cur, MyMath.subtractionVV(b.get(cur), MyMath.multiplyDV(mu, Jb.get(cur))));
            }

            double J = 0;
            for (int i = 0; i <= t; i++) {
                J += 0.5 * g(1 - lij * (tau - MyMath.squaredEuclideanDistance(states.getH(0, i, numberOfLayers - 1), states.getH(1, i, numberOfLayers - 1))));
            }
            for (int i = 1; i < numberOfLayers; i++) {
                J += 0.5 * lambda * (MyMath.FrobeniusNorm(w.get(i)) + MyMath.VectorNorm(b.get(i)));
            }
            states.addJ(J);
            if (t > 0 && Math.abs(states.getJ(t - 1) - states.getJ(t)) < eps) {
                break;
            }
        }
    }
}
