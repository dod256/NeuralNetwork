package com.company;

/**
 * Created by David on 10/15/2016.
 */
public class MyObject {

    private Vector description;
    private int type;

    public MyObject(Vector description, int type) {
        this.description = description;
        this.type = type;
    }

    public Vector getDescription() {

        return description;
    }

    public int getType() {
        return type;
    }
}
