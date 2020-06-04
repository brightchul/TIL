package baekjoon.p1912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(BR.readLine());
        int[] inputArr = new int[len];
        StringTokenizer st = new StringTokenizer(BR.readLine(), " ");
        for(int i=0; i<len; i++)
            inputArr[i] = Integer.parseInt(st.nextToken());

        System.out.println(solution(inputArr));
    }
    public static int solution(int[] arr) {
        int len = arr.length;
        int[] dp = new int[len];
        int result = dp[0] = arr[0];
        for(int i=1; i<len; i++) {
            dp[i] = Math.max(dp[i-1] + arr[i], arr[i]);
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}
