package com.company;
import java.io.*;
import java.util.*;

public class Main implements Runnable{

    BufferedReader br;
    StringTokenizer st;
    boolean eof;

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    String nextToken()
    {
        while(st == null || !st.hasMoreTokens())
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                eof = true;
                return "0";
            }
        return st.nextToken();
    }

    int nextInt()
    {
        return Integer.parseInt(nextToken());
    }

    void createNet(NeuralNetwork net) throws IOException {
        net.trainNetwork();
        net.saveNetwork();
    }

    void testNet(NeuralNetwork net) throws IOException {
        net.restoreNetFromFile();
        DataSet x = new DataSet();
        x.initFromFile();
        int right = 0;
        int wrong = 0;
        for (int i = 0; i < 1; i++) {
            Input in = x.getRandomInput();
            boolean test = net.test(in.getFirstInput(), in.getSecondInput());
            if ((test && in.getL() == 1) || (!test && in.getL() == -1)) {
                right++;
            } else {
                wrong++;
            }
        }
        System.out.println(right + " " + wrong);
    }

    void gen () throws FileNotFoundException {
        DataGenerator.gen();
    }


    void main () throws IOException {
        NeuralNetwork net = new NeuralNetwork();
        Scanner in = new Scanner(System.in);
        //String s = in.nextLine();
        String s = "create";
        if ("create".equals(s)) {
            createNet(net);
        } else {
            if ("gen".equals(s)) {
                gen();
            } else {
                testNet(net);
            }
        }
    }

    @Override
    public void run() {
        try {
            main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
