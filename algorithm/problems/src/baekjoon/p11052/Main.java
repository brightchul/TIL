package baekjoon.p11052;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
https://www.acmicpc.net/problem/11052
4
1 5 6 7
return 10

5
10 9 8 7 6
return 50

10
1 1 2 3 5 8 13 21 34 55
return 55


 */

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int len, targetCount;
    private static int[][] cache;

    public static void main(String[] args) throws IOException {
        targetCount = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        len = st.countTokens();
        cache = new int[len + 1][targetCount + 1];
        int max = -1;

        for (int i = 1; i <= len; i++) {
            int price = Integer.parseInt(st.nextToken());

            for (int j = 1; j <= targetCount; j++) {
                cache[i][j] = Math.max(getValue(i, j - i) + (j >= i ? price : 0), getValue(i - 1, j));
                max = Math.max(max, cache[i][j]);
            }

        }
        System.out.println(max);

    }

    private static int getValue(int row, int col) {
        if (row < 0 || col < 0) return 0;
        if (row >= len || col >= targetCount) return 0;
        return cache[row][col];
    }

}
