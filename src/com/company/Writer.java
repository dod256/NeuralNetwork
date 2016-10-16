package com.company;

/**
 * Created by David on 10/16/2016.
 */
public class Writer {

    public static void printVector (Vector v) {
        for (int i = 0; i < v.getSize(); i++) {
            System.out.print(v.getValue(i) + " ");
        }
        System.out.println("");
    }
}
