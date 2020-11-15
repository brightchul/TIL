package baekjoon.p2239;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int[][] valueArr;
    private static int[] coinArr;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int len = Integer.parseInt(st.nextToken());
        int value = Integer.parseInt(st.nextToken());

        initArr(len, value);
        System.out.println(solution(len, value));
    }

    private static void initArr(int len, int value) throws IOException {
        coinArr = new int[len];
        int idx = 0;
        while (idx < len) coinArr[idx++] = Integer.parseInt(br.readLine());

        valueArr = new int[len][];
        for (int i = 0; i < len; i++) valueArr[i] = new int[value + 1];
    }

    private static int solution(int len, int value) {
        for (int i = 0; i < len; i++) {
            int coin = coinArr[i];
            valueArr[i][0] = 1;
            for (int j = 1; j <= value; j++) {
                valueArr[i][j] = getValue(i, j - coin) + getValue(i - 1, j);
            }
        }
        return valueArr[len - 1][value];
    }

    private static int getValue(int i, int j) {
        if (i < 0 || valueArr.length <= i) return 0;
        if (j < 0 || valueArr[0].length <= j) return 0;
        return valueArr[i][j];
    }
}
