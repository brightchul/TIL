package baekjoon.p1806;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
10 15
5 1 3 5 10 7 4 9 2 8

답2
없으면 0

10 9
1 1 1 1 1 1 1 1 1 8

10 8
1 1 1 1 1 1 1 1 1 8


 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int TOTAL_LEN = Integer.parseInt(st.nextToken());
        int STANDARD_SUM = Integer.parseInt(st.nextToken());
        int[] arr = getIntArr(TOTAL_LEN);
        System.out.println(run(arr, STANDARD_SUM));
    }
    public static int[] getIntArr(int len) throws IOException{
        int[] ret = new int[len];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for(int i=0; i<len; i++) ret[i] = Integer.parseInt(st.nextToken());
        return ret;
    }
    // two pointer algorithm
    public static int run(int[] arr, int limit) {
        int startIdx = 0, endIdx = 0, partialSum = 0, len = arr.length;
        int ret = Integer.MAX_VALUE;

        while(endIdx <= len) {
            if(partialSum < limit) {
                if(endIdx == len) break;
                partialSum += arr[endIdx++];
            } else {
                ret = Math.min(ret, endIdx- startIdx);
                partialSum -= arr[startIdx++];
            }
        }

        return ret == Integer.MAX_VALUE ? 0 : ret;
    }
    public static int run2(int[] arr, int limit) {
        int ret = Integer.MAX_VALUE;
        int len = arr.length;
        int[] cache = new int[len];

        for(int i=0; i<len; i++) {
            int one = arr[i];
            for(int idx = 0; idx <= i; idx++) {
                cache[idx] += one;
                if(cache[idx] >= limit) {
                    ret = Math.min(ret, i - idx + 1);
                }
            }
        }
        return ret == Integer.MAX_VALUE ? 0 : ret;
    }

}
