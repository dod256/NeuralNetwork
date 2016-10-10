package com.company;

import java.util.ArrayList;

/**
 * Created by David on 10/9/2016.
 */
public class Vector {

    private int size;
    private ArrayList<Double> values;
    private boolean isTranspose;

    public int getSize() {
        return size;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    Vector () {
        size = 0;
        values = new ArrayList<>();
        isTranspose = false;
    }

    Vector (ArrayList<Double> values, boolean isTranspose) {
        this.values = values;
        size = values.size();
        this.isTranspose = isTranspose;
    }

    Vector (ArrayList<Double> values) {
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
}
