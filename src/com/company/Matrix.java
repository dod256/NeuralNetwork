package com.company;

import java.util.ArrayList;

/**
 * Created by David on 10/9/2016.
 */
public class Matrix {

    int n, m; //n rows, m columns
    private ArrayList<ArrayList<Double>> values;

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public ArrayList<ArrayList<Double>> getValues() {
        return values;
    }

    public Matrix() {
    }

    public Matrix(ArrayList<ArrayList<Double>> values) {
        this.values = values;
        n = values.size();
        if (n == 0) {
            m = 0;
        } else {
            m = values.get(0).size();
        }
    }

    public double getValue (int i, int j) {
        if (j >= n || j >= m) {
            return 0;
        }
        return values.get(i).get(j);
    }
}
