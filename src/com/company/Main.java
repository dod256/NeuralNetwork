package com.company;
import java.io.*;
import java.util.*;

public class Main {//implements Runnable{

    BufferedReader br;
    StringTokenizer st;
    boolean eof;
/*
    public static void main(String[] args) {
        new Thread(new Main()).start();
    }
*/

    public static void main(String[] args) {
        NeuralNetwork net = new NeuralNetwork();
        net.trainNetwork();
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

    /*
    void main () {
        NeuralNetwork net = new NeuralNetwork();
        net.trainNetwork();
    }

    @Override
    public void run() {
        br = new BufferedReader(new InputStreamReader(System.in));
        main();
    }
    */
}
