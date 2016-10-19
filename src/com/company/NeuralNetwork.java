package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class NeuralNetwork {

    private final String LOG_LEVEL = "DEBUG";

    private int numberOfLayers;
    private List<Integer> sizeOfLayers;
    private int iterativeNumber;

    private TrainingStates states;
    private DataSet x;
    
    private List<Matrix> w;
    private List<Vector> b;
    private List<Matrix> JW;
    private List<Vector> Jb;

    private double eps, lambda, mu, tau, beta, c;

    public void init () throws IOException {
        Random rand = new Random();
        iterativeNumber = 500;

        x = new DataSet();
        x.initFromFile();

        numberOfLayers = 3;
        sizeOfLayers = new ArrayList<>();
        sizeOfLayers.add(x.getInputSize());
        sizeOfLayers.add(5);
        sizeOfLayers.add(x.getInputSize());

        w = new ArrayList<>();
        b = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            b.add(new Vector(sizeOfLayers.get(i)));
            if (i > 0) {
                List<List<Double>> values = new ArrayList<>();
                for (int j = 0; j < sizeOfLayers.get(i); j++) {
                    values.add(new ArrayList<>());
                    for (int k = 0; k < sizeOfLayers.get(i - 1); k++) {
                        values.get(j).add(rand.nextDouble() * 100);
                    }
                }
                w.add(new Matrix(values)); //Change to uniform distribution
            } else {
                w.add(new Matrix());
            }
        }

        JW = new ArrayList<>();
        Jb = new ArrayList<>();
        JW.add(new Matrix());
        Jb.add(new Vector());
        for (int i = 1; i < numberOfLayers; i++) {
            JW.add(new Matrix(sizeOfLayers.get(i), sizeOfLayers.get(i - 1)));
            Jb.add(new Vector(sizeOfLayers.get(i)));
        }

        states = new TrainingStates(numberOfLayers, sizeOfLayers, iterativeNumber);

        eps = 0.001;
        lambda = 0.2;
        mu = 1;
        tau = 1.1;
        beta = 1;

    }

    private void forwardPropagation (int t) {
        for (int i = 1; i < numberOfLayers; i++) {
            states.setZ(0, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(0, t, i - 1)), b.get(i)));
            states.setH(0, t, i, MyMath.applyTanh(states.getZ(0, t, i)));
            states.setZ(1, t, i, MyMath.additionVV(MyMath.multiplyMV(w.get(i), states.getH(1, t, i - 1)), b.get(i)));
            states.setH(1, t, i, MyMath.applyTanh(states.getZ(1, t, i)));
        }
    }

    public boolean test (Vector v1, Vector v2) {
        if ("DEBUG".equals(LOG_LEVEL)) {
            Writer.printVector(v1);
            Writer.printVector(v2);
        }
        for(int i = 1; i < numberOfLayers; i++) {
            v1 = MyMath.applyTanh(MyMath.additionVV(MyMath.multiplyMV(w.get(i), v1), b.get(i)));
            v2 = MyMath.applyTanh(MyMath.additionVV(MyMath.multiplyMV(w.get(i), v2), b.get(i)));
        }
        if ("DEBUG".equals(LOG_LEVEL)) {
            Writer.printVector(v1);
            Writer.printVector(v2);
            System.out.println(MyMath.squaredEuclideanDistance(v1, v2));
        }
        if (tau > MyMath.squaredEuclideanDistance(v1, v2)) {
            return true;
        } else {
            return false;
        }
    }

    private double g (double z) {
        return (1.0 / beta) * Math.log(1 + Math.exp(beta * z));
    }

    private double gDerivative (double z) {
        double exp = Math.exp(beta * z);
        return exp / (1 + exp);
    }

    public void saveNetwork () throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File("net.txt"));
        out.println("Eps: " + eps);
        out.println("Lambda: " + lambda);
        out.println("Mu: " + mu);
        out.println("Tau: " + tau);
        out.println("Beta: " + beta);
        out.println("NumberOfLayers: " + numberOfLayers);
        for (int i = 0; i < numberOfLayers; i++) {
            out.println("SizeOfLayerNumber " + i + ": " + sizeOfLayers.get(i));
        }
        for (int i = 1; i < numberOfLayers; i++) {
            out.println("Layer " + i + ": " + w.get(i).getN() + " " + w.get(i).getM());
            for (int j = 0; j < w.get(i).getN(); j++) {
                for (int k = 0; k < w.get(i).getM(); k++) {
                    out.print(String.format("%.2f ", w.get(i).getValue(j, k)));
                }
                out.println(String.format("   %.2f", b.get(i).getValue(j)));
            }
        }
        out.close();
    }

    public void restoreNetFromFile () throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("net.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        eps = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        lambda = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        mu = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        tau = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        beta = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        numberOfLayers = Integer.parseInt(st.nextToken());
        sizeOfLayers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            st.nextToken();
            sizeOfLayers.add(Integer.parseInt(st.nextToken()));
        }
        w = new ArrayList<>();
        w.add(new Matrix());
        b = new ArrayList<>();
        b.add(new Vector());
        for (int i = 1; i < numberOfLayers; i++) {
            List<List<Double>> values = new ArrayList<>();
            List<Double> newB = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            st.nextToken();
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                values.add(new ArrayList<>());
                for (int k = 0; k < m; k++) {
                    values.get(j).add(Double.parseDouble(st.nextToken()));
                }
                newB.add(Double.parseDouble(st.nextToken()));
            }
            w.add(new Matrix(values));
            b.add(new Vector(newB));
        }
    }

    public void printNetwork (int t) {
        if (t == -1) {
//            t = x.getNumberOfData() - 1;
        }
        System.out.println("Time: " + (t + 1));
        for (int i = 1; i < numberOfLayers; i++) {
            System.out.println("Layer:" + i);
            for (int j = 0; j < w.get(i).getN(); j++) {
                    for (int k = 0; k < w.get(i).getM(); k++) {
                    System.out.print(String.format("%.2f ", w.get(i).getValue(j, k)));
                    }
                    System.out.println(String.format("   %.2f", b.get(i).getValue(j)));
                }
            }
/*
            System.out.println("x1: ");
            Vector curv = states.getH(0, t, 0);
            for (int j = 0; j < curv.getSize(); j++) {
                System.out.print(String.format("%.2f ", curv.getValue(j)));
            }
            System.out.println("");
            System.out.println("x2: ");
            curv = states.getH(1, t, 0);
            for (int j = 0; j < curv.getSize(); j++) {
                System.out.print(String.format("%.2f ", curv.getValue(j)));
            }
            System.out.println("");
            System.out.println("h1: ");
            curv = states.getH(0, t, numberOfLayers - 1);
            for (int j = 0; j < curv.getSize(); j++) {
                System.out.print(String.format("%.2f ", curv.getValue(j)));
            }
            System.out.println("");
            System.out.println("h2: ");
            curv = states.getH(1, t, numberOfLayers - 1);
            for (int j = 0; j < curv.getSize(); j++) {
                System.out.print(String.format("%.2f ", curv.getValue(j)));
            }
            System.out.println("");
*/
    }

    public void trainNetwork () throws IOException {
        init();
        for (int t = 0; t < iterativeNumber; t++) {
            if ("DEBUG".equals(LOG_LEVEL)) {
                System.out.println("Time: " + (t));
            }
            Input in = x.getRandomInput();
            states.setH(0, t, 0, in.getFirstInput());
            states.setH(1, t, 0, in.getSecondInput());
            double lij = in.getL();

            forwardPropagation(t);

            // Computing gradient
            if ("DEBUG".equals(LOG_LEVEL)) {
                System.out.println(lij + " " + MyMath.squaredEuclideanDistance(states.getH(0, t, numberOfLayers - 1), states.getH(1, t, numberOfLayers - 1)));
            }
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
