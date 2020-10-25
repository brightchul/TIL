package baekjoon.p1915;
/*
https://www.acmicpc.net/problem/1915

4 4
0100
0111
1110
0010

return 4

 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int row, col;

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    row = Integer.parseInt(st.nextToken());
    col = Integer.parseInt(st.nextToken());

    int[][] arr = new int[row + 1][col + 1];
    int result = Integer.MIN_VALUE;

    for (int r = 1; r <= row; r++) {
      char[] inputArr = br.readLine().toCharArray();

      for (int c = 1; c <= col; c++) {
        if (inputArr[c - 1] == '0') continue;

        arr[r][c] = Math.min(arr[r - 1][c - 1], Math.min(arr[r][c - 1], arr[r - 1][c])) + 1;
        result = Math.max(result, arr[r][c]);
      }
    }
    System.out.println(result * result);
  }
}
