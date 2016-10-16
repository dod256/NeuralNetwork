package com.company;

/**
 * Created by David on 10/15/2016.
 */
public class Input {

    private Vector firstInput;
    private Vector secondInput;
    private double l;

    public Vector getFirstInput() {
        return firstInput;
    }

    public Vector getSecondInput() {
        return secondInput;
    }

    public double getL() {
        return l;
    }

    public Input(Vector firstInput, Vector secondInput, double l) {
        this.firstInput = firstInput;
        this.secondInput = secondInput;
        this.l = l;
    }
}
