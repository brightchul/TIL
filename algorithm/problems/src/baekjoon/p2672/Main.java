package baekjoon.p2672;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
4
52.7 22.9 27.3 13.3
68.8 57.6 23.2 8.0
20.0 48.0 37.0 23.5
41.5 36.2 27.3 21.4
//1853.61
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        short[][] cache = new short[10001][10001];
        short len = (short)Integer.parseInt(br.readLine());
        StringTokenizer st;
        int count = 0;
        while(len-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            short x1 = (short)(Double.parseDouble(st.nextToken()) * 10);
            short y1 = (short)(Double.parseDouble(st.nextToken()) * 10);
            short x2 = (short)(x1 + (Double.parseDouble(st.nextToken())*10));
            short y2 = (short)(y1 + (Double.parseDouble(st.nextToken())*10));

            for(short x=x1; x<x2; x++) {
                for(short y=y1; y<y2; y++) {
                    if(cache[y][x] == 0) {
                        cache[y][x]++;
                        count++;
                    }
                }
            }
        }
        double result = count / 100.0;
        if(result % 1 > 0) {
            System.out.printf("%.2f", result);
        } else {
            System.out.printf("%d", (int)result);
        }
    }
}
