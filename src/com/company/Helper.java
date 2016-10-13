package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {

    private  static Random intRandom = new Random();

    public static Vector getRandVector(int len) {
        List<Double> rand = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            rand.add(Math.random() * 10);
        }
        return new Vector(rand);
    }


    public static Vector mutate(Vector oldVector) {
        if (Math.random() < 0.1) {
            int ind = Math.abs(intRandom.nextInt()) % oldVector.getSize();
            List<Double> list = oldVector.getValues();
            list.set(ind, list.get(ind) + 20);
            return new Vector(list);
        }
        return oldVector;
    }
}
