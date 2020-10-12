package baekjoon.p1932;
/*
https://www.acmicpc.net/problem/1932
5
7
3 8
8 1 0
2 7 4 4
4 5 2 6 5

return 30
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    int len = Integer.parseInt(br.readLine()), idx = 0;
    StringTokenizer st;
    int[][] cache = new int[len][len];

    while (idx < len) {
      st = new StringTokenizer(br.readLine(), " ");
      for (int i = 0; i <= idx; i++) {

        int one = Integer.parseInt(st.nextToken());
        cache[idx][i] = one + getMax(idx - 1, i - 1, idx - 1, i, cache);
      }
      idx++;
    }
    System.out.println(getMax(cache[len - 1]));
  }

  private static int getMax(int[] arr) {
    int result = Integer.MIN_VALUE;
    for (int one : arr) result = Math.max(result, one);
    return result;
  }

  private static int getMax(int row1, int col1, int row2, int col2, int[][] arr) {
    return Math.max(getValue(row1, col1, arr), getValue(row2, col2, arr));
  }

  private static int getValue(int row, int col, int[][] arr) {
    return isOut(row, col, arr.length) ? 0 : arr[row][col];
  }

  private static boolean isOut(int row, int col, int len) {
    return row < 0 || col < 0 || len <= row || len <= col;
  }
}
