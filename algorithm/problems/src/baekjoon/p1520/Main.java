package baekjoon.p1520;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int row, col, count;
  private static int[][] arr;
  private static int[][] cache;

  public static void main(String[] args) throws IOException {

    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    count = 0;
    row = nextInt(st);
    col = nextInt(st);
    initArr();
    dfs(0, 0);
    System.out.println(count);
  }

  public static int nextInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
  }

  public static void initArr() throws IOException {
    cache = new int[row][col];
    cache[row-1][col-1] = 1;

    arr = new int[row][col];
    StringTokenizer st;

    for (int r = 0; r < row; r++) {
      st = new StringTokenizer(br.readLine(), " ");
      for (int c = 0; c < col; c++) arr[r][c] = nextInt(st);
    }
  }

  public static boolean isOut(int rIdx, int cIdx) {
    return rIdx < 0 || rIdx >= row || cIdx < 0 || cIdx >= col;
  }

  public static int[][] dirArr = {
          {-1, 0}
  }

  public static int dfs(int rIdx, int cIdx) {
    if(cache[rIdx][cIdx] > 0) {
      return cache[rIdx][cIdx];
    }

    if (!isOut(rIdx - 1, cIdx) && arr[rIdx][cIdx] > arr[rIdx - 1][cIdx]) dfs(rIdx - 1, cIdx);
    if (!isOut(rIdx + 1, cIdx) && arr[rIdx][cIdx] > arr[rIdx + 1][cIdx]) dfs(rIdx + 1, cIdx);
    if (!isOut(rIdx, cIdx - 1) && arr[rIdx][cIdx] > arr[rIdx][cIdx - 1]) dfs(rIdx, cIdx - 1);
    if (!isOut(rIdx, cIdx + 1) && arr[rIdx][cIdx] > arr[rIdx][cIdx + 1]) dfs(rIdx, cIdx + 1);
  }
}
