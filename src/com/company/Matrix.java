package com.company;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

    int n, m; //n rows, m columns
    private List<List<Double>> values;

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public List<List<Double>> getValues() {
        return values;
    }

    public Matrix() {
        n = 0;
        m = 0;
        values = new ArrayList<>();
    }

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            values.add(new ArrayList<>(m));
        }
    }

    public Matrix(List<List<Double>> values) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;

        Matrix matrix = (Matrix) o;

        if (getN() != matrix.getN()) return false;
        if (getM() != matrix.getM()) return false;
        return getValues().equals(matrix.getValues());

    }

    @Override
    public int hashCode() {
        int result = getN();
        result = 31 * result + getM();
        result = 31 * result + getValues().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "n=" + n +
                ", m=" + m +
                ", values=" + values +
                '}';
    }
}
