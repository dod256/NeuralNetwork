package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vector {

    private int size;
    private List<Double> values;

    //if isTranspose == false then vector is column
    private boolean isTranspose;

    public int getSize() {
        return size;
    }

    public List<Double> getValues() {
        return values;
    }

    public Vector () {
        size = 0;
        values = new ArrayList<>();
        isTranspose = false;
    }

    public Vector (int size) {
        this.size = size;
        values = new ArrayList<>(size);
        isTranspose = false;
    }

    public Vector (List<Double> values, boolean isTranspose) {
        this.values = values;
        size = values.size();
        this.isTranspose = isTranspose;
    }

    public Vector (List<Double> values) {
        this.values = values;
        size = values.size();
        this.isTranspose = false;
    }

    public boolean isTranspose() {
        return isTranspose;
    }

    public double getValue (int i) {
        if (i >= size) {
            return 0;
        }
        return values.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector vector = (Vector) o;

        if (getSize() != vector.getSize()) return false;
        if (isTranspose() != vector.isTranspose()) return false;
        return getValues().equals(vector.getValues());

    }

    @Override
    public int hashCode() {
        int result = getSize();
        result = 31 * result + getValues().hashCode();
        result = 31 * result + (isTranspose() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "size=" + size +
                ", values=" + values +
                ", isTranspose=" + isTranspose +
                '}';
    }
}
