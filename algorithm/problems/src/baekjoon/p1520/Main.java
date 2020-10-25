package baekjoon.p1520;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static int[][] dirArr = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  private static int row, col;
  private static int[][] arr;
  private static int[][] visited;
  private static int[][] cache;

  public static void main(String[] args) throws IOException {

    StringTokenizer st = new StringTokenizer(br.readLine(), " ");

    row = nextInt(st);
    col = nextInt(st);
    initArr();
    dfs(0, 0);
    System.out.println(cache[0][0]);
  }

  public static int nextInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
  }

  public static void initArr() throws IOException {
    cache = new int[row][col];
    cache[row - 1][col - 1] = 1;

    visited = new int[row][col];

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

  public static int dfs(int rIdx, int cIdx) {
    if (cache[rIdx][cIdx] > 0 || visited[rIdx][cIdx] > 0) {
      return cache[rIdx][cIdx];
    }

    visited[rIdx][cIdx] = 1;

    for (int[] dir : dirArr) {
      if (!isOut(rIdx + dir[0], cIdx + dir[1])
              && arr[rIdx][cIdx] > arr[rIdx + dir[0]][cIdx + dir[1]]) {
        cache[rIdx][cIdx] += dfs(rIdx + dir[0], cIdx + dir[1]);
      }
    }
    return cache[rIdx][cIdx];
  }
}
