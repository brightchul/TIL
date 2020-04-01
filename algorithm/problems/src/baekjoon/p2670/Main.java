package baekjoon.p2670;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
https://www.acmicpc.net/problem/2670
8
1.1
0.7
1.3
0.9
1.4
0.8
0.7
1.4
//1.638

5
2
2
2
2
2
// 32.000 나와야 함
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        double[] arr = new double[len];
        for(int i=0; i<len; i++)
            arr[i] = Double.parseDouble(br.readLine());
        double result = run(arr);
        System.out.printf("%.3f",result);
    }
    public static double run(double[] arr) {
        double result = 0.0;
        for(int i=0; i<arr.length; i++) {
            double temp = arr[i];
            result = Math.max(result, temp);
            for(int j=i+1; j<arr.length; j++) {
                temp *= arr[j];
                result = Math.max(result, temp);
            }
        }
        result = Math.round(result * 1000) / 1000.0;
        return result;
    }
}
