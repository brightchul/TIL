package algospot.RUNNINGMEDIAN;

import java.io.*;
import java.util.*;

/*
https://algospot.com/judge/problem/read/RUNNINGMEDIAN
3
10 1 0
10 1 1
10000 1273 4936
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int totalCaseCount = Integer.parseInt(br.readLine());
        StringTokenizer st;

        while(totalCaseCount-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            int len = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            bw.write(run(len, a, b)+"\n");
        }
        bw.flush();
    }
    public static int run(int len, int a, int b) {
        PriorityQueue<Integer> nextH= new PriorityQueue<>();
        PriorityQueue<Integer> prevH = new PriorityQueue<>(Collections.reverseOrder());

        int ret = 0;
        RNG rng = new RNG(a, b);
        for(int i=0; i<len; i++) {
            if(prevH.size() <= nextH.size()) {
                prevH.add(rng.next());
            } else {
                nextH.add(rng.next());
            }

            if(nextH.size() > 0 && prevH.peek() > nextH.peek()) {
                int temp = prevH.poll();
                prevH.add(nextH.poll());
                nextH.add(temp);
            }
            ret = (ret + prevH.peek()) % 20090711;
        }
        return ret;
    }
}
class RNG {
    int seed = 1983;
    final int MOD = 20090711;
    int a, b;
    RNG(int a, int b) {
        this.a= a;
        this.b= b;
    }
    public int next() {
        int curValue = seed;
        seed = (int)((1L * seed * a + b) % MOD);
        return curValue;
    }
}