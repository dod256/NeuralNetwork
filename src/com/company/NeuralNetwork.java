package com.company;

import java.util.ArrayList;

public class NeuralNetwork {

    public int numberOfLayers;
    public ArrayList<Integer> sizeOfLayers;
    private int iterativeNumber;

    private TrainingStates states;
    
    private ArrayList<Matrix> w;
    private ArrayList<Vector> b;
    private ArrayList<Matrix> JW;
    private ArrayList<Vector> Jb;



    private double eps, lambda, mu, tau, beta, c;

    public void init (int numberOfLayers, int sizeOfLayers) {
        states = new TrainingStates();
        //set sixes for all arrayLists
    }

    private void forwardPropagation (int t) {
        for (int i = 1; i < numberOfLayers; i++) {
            states.setZ(0, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(0, t, i - 1)), b.get(i)));
            states.setH(0, t, i, MyMath.applyTanh(states.getZ(0, t, i)));
            states.setZ(1, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(1, t, i - 1)), b.get(i)));
            states.setH(1, t, i, MyMath.applyTanh(states.getZ(0, t, i)));
        }
    }

    public void trainNetwork () {
        DataSet x = new DataSet();
        for (int t = 0; t < iterativeNumber; t++) {
            states.setH(0, t, 0, x.getXi(t));
            states.setH(1, t, 0, x.getXj(t));
            double lij = x.getLij(t);
            forwardPropagation(t);
            // Computing gradient
            c = 1 - lij * (tau - MyMath.squaredEuclideanDistance(states.getH(0, t, numberOfLayers - 1), states.getH(1, t, numberOfLayers - 1)));
            double gdc = c; //g'(c)
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
            states.addJ(0.0);
            if (t > 0 && Math.abs(states.getJ(t - 1) - states.getJ(t)) < eps) {
                break;
            }
        }
    }
}
