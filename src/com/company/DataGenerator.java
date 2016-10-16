package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by David on 10/16/2016.
 */
public class DataGenerator {

    public static void gen () throws FileNotFoundException {
        Random rnd = new Random();
        ArrayList<Matrix> firstType = new ArrayList<>();
        ArrayList<Matrix> secondType = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            List<List<Double>> value = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                value.add(new ArrayList<>());
                for (int k = 0; k < 4; k++) {
                    value.get(j).add((double) (Math.abs(rnd.nextInt()) % 2));
                }
            }
            boolean type = false;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (value.get(j).get(k) == 1.0 && value.get(j + 1).get(k) == 1.0 &&
                            value.get(j).get(k + 1) == 1.0 && value.get(j + 1).get(k + 1) == 1.0) {
                        type = true;
                    }
                }
            }

            if (type) {
                firstType.add(new Matrix(value));
            } else {
                secondType.add(new Matrix(value));
            }
        }
        PrintWriter out = new PrintWriter(new File("n0.txt"));
        out.println(firstType.size() + " " + 16);
        for (int i = 0; i < firstType.size(); i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    out.print(firstType.get(i).getValue(j, k) + " ");
                }
            }
            out.println("");
        }
        out.close();
        out = new PrintWriter(new File("n1.txt"));
        out.println(secondType.size() + " " + 16);
        for (int i = 0; i < secondType.size(); i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    out.print(secondType.get(i).getValue(j, k) + " ");
                }
            }
            out.println("");
        }
        out.close();
    }


}
