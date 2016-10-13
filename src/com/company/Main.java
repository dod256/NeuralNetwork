package com.company;
import java.io.*;
import java.util.*;

public class Main {

    BufferedReader br;
    StringTokenizer st;
    boolean eof;

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

}
