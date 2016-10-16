package com.company;

import java.io.*;
import java.util.*;

import static com.company.Helper.mutate;

public class DataSet {

    private int numberOfData;
    private int inputSize;

    ArrayList<MyObject> x;

    ArrayList<Vector> xi;
    ArrayList<Vector> xj;
    ArrayList<Double> lij;

    public DataSet() {

    }

    void initRandomData (int numberOfData, int inputSize) {
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

    void initFromFile () throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("NeuralNetwork.env")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        int numberOfTypes = Integer.parseInt(st.nextToken());
        numberOfData = 0;
        x = new ArrayList<>();
        for (int f = 0; f < numberOfTypes; f++) {
            br = new BufferedReader(new FileReader(new File("n" + f + ".txt")));
            st = new StringTokenizer(br.readLine());
            int numberOfLines = Integer.parseInt(st.nextToken());
            inputSize = Integer.parseInt(st.nextToken());
            numberOfData += numberOfLines;
            for (int t = 0; t < numberOfLines; t++) {
                st = new StringTokenizer(br.readLine());
                ArrayList<Double> newVector = new ArrayList<>();
                for (int i = 0; i < inputSize; i++) {
                    newVector.add(Double.parseDouble(st.nextToken()));
                }
                x.add(new MyObject(new Vector(newVector), f));
            }
        }
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public int getInputSize() {
        return inputSize;
    }

    public Input getRandomInput() {
        Random rnd = new Random();
        int n1 = Math.abs(rnd.nextInt()) % x.size();
        int n2 = Math.abs(rnd.nextInt()) % x.size();
        return new Input(x.get(n1).getDescription(), x.get(n2).getDescription(),
                x.get(n1).getType() == x.get(n2).getType() ? 1 : -1);
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
