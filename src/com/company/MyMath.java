package com.company;

import java.util.ArrayList;

public class MyMath {

    public static Vector multiplyMV (Matrix m, Vector v) {
        if (m.getM() != v.getSize()) {
            return null;
        }
        ArrayList<Double> ans = new ArrayList<>();

        for (int i = 0; i < m.getN(); i++) {
            double cur = 0;
            for (int j = 0; j < m.getM(); j++) {
                cur += v.getValue(j) * m.getValue(i, j);
            }
            ans.add(cur);
        }

        return new Vector(ans);
    }

    public static Matrix multiplyVV (Vector a, Vector b) {
        if (a.getSize() != b.getSize() || a.isTranspose() || !b.isTranspose()) {
            return null;
        }
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();

        for (int i = 0; i < a.getSize(); i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < b.getSize(); j++) {
                ans.get(i).add(a.getValue(i) * b.getValue(j));
            }
        }

        return new Matrix(ans);
    }

    public static Matrix multiplyDM (double d, Matrix m) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        for (int i = 0; i < m.getN(); i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < m.getM(); j++) {
                ans.get(i).add(d * m.getValue(i, j));
            }
        }
        return new Matrix(ans);
    }

    public static Matrix additionMM (Matrix a, Matrix b) {
        if (a.getN() != b.getN() || a.getM() != b.getM()) {
            return null;
        }
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        for (int i = 0; i < a.getN(); i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < a.getM(); j++) {
                ans.get(i).add(a.getValue(i, j) + b.getValue(i, j));
            }
        }
        return new Matrix(ans);
    }

    public static Matrix subtractionMM (Matrix a, Matrix b) {
        if (a.getN() != b.getN() || a.getM() != b.getM()) {
            return null;
        }
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        for (int i = 0; i < a.getN(); i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < a.getM(); j++) {
                ans.get(i).add(a.getValue(i, j) - b.getValue(i, j));
            }
        }
        return new Matrix(ans);
    }

    public static Vector additionVV (Vector a, Vector b) {
        if (a.getSize() != b.getSize()) {
            return null;
        }
        ArrayList<Double> ans = new ArrayList<>();

        for (int i = 0; i < a.getSize(); i++) {
            ans.add(a.getValue(i) + b.getValue(i));
        }

        return new Vector(ans);
    }

    public static Vector subtractionVV (Vector a, Vector b) {
        if (a.getSize() != b.getSize()) {
            return null;
        }
        ArrayList<Double> ans = new ArrayList<>();

        for (int i = 0; i < a.getSize(); i++) {
            ans.add(a.getValue(i) - b.getValue(i));
        }

        return new Vector(ans);
    }

    public static Vector multiplyDV (double a, Vector b) {
        ArrayList<Double> ans = new ArrayList<>();

        for (int i = 0; i < b.getSize(); i++) {
            ans.add(a * b.getValue(i));
        }

        return new Vector(ans);
    }

    public static Vector multiplyElementWiseVV (Vector a, Vector b) {
        if (a.getSize() != b.getSize()) {
            return null;
        }
        ArrayList<Double> ans = new ArrayList<>();
        for (int i = 0; i < a.getSize(); i++) {
            ans.add(a.getValue(i) * b.getValue(i));
        }
        return new Vector(ans);
    }

    public static Vector applyTanh (Vector a) {
        ArrayList<Double> ans = new ArrayList<>();
        for(int i = 0; i < a.getSize(); i++) {
            ans.add(Math.tanh(a.getValue(i)));
        }
        return new Vector(ans);
    }

    public static Vector applyTanhDerivative (Vector a) {
        ArrayList<Double> ans = new ArrayList<>();
        for(int i = 0; i < a.getSize(); i++) {
            double tmp = Math.tanh(a.getValue(i));
            ans.add(1 - tmp * tmp);
        }
        return new Vector(ans);
    }

    public static double squaredEuclideanDistance (Vector a, Vector b) {
        double ans = 0;
        for(int i = 0; i < a.getSize(); i++) {
            ans += (a.getValue(i) - b.getValue(i)) * (a.getValue(i) - b.getValue(i));
        }
        return ans;
    }

    public static Matrix TransposeM (Matrix a) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();

        for (int i = 0; i < a.getM(); i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < a.getN(); j++) {
                ans.get(i).add(a.getValue(j, i));
            }
        }

        return new Matrix(ans);
    }

}
