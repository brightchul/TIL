package baekjoon.p9465;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();

        int totalCaseCount = Integer.parseInt(BR.readLine());
        while(totalCaseCount-- > 0) {
            int colLen = Integer.parseInt(BR.readLine());
            int[][] arr = new int[2][colLen];

            StringTokenizer st = new StringTokenizer(BR.readLine(), " ");
            for(int i=0; i<colLen;i++)
                arr[0][i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(BR.readLine());
            for(int i=0; i<colLen;i++)
                arr[1][i] = Integer.parseInt(st.nextToken());

            sb.append(solution(arr)).append("\n");
        }
        System.out.println(sb.toString());
    }
    public static int solution(int[][] arr) {
        int result = 0;
        int colLen = arr[0].length;
        int[][] dp = new int[2][colLen];
        int[]rowSelect = {1, 0};
        for(int col = 0; col < colLen; col++) {
            for(int row = 0; row < 2; row++) {
                int temp = 0;
                if(isIn(col-1)) {
                    temp = Math.max(temp,dp[rowSelect[row]][col-1]);
                }
                if(isIn(col-2)) {
                    temp = max(temp,dp[0][col-2], dp[1][col-2]);
                }
                dp[row][col] = temp + arr[row][col];
                result = Math.max(result, dp[row][col]);
            }
        }

        return result;
    }
    private static boolean isIn(int col) {
        return col >= 0;
    }
    private static int max(int ...numbers) {
        int maxValue = Integer.MIN_VALUE;
        for(int number : numbers) {
            maxValue = Math.max(maxValue, number);
        }
        return maxValue;
    }
}
