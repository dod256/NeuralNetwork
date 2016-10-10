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

    void main () {

    }

    @Override
    public void run() {
        br = new BufferedReader(new InputStreamReader(System.in));
        main();
    }

}
