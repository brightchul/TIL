package baekjoon.p2294;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  private static final int IMPOSSIBLE = 1_000_000_003;
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int len, target;
  private static int[] arr;
  private static int[][] cache;

  public static void main(String[] args) throws IOException {
    init();
    System.out.println(solution());
  }

  public static void init() throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine());
    len = Integer.parseInt(st.nextToken());
    target = Integer.parseInt(st.nextToken());

    cache = new int[len][target + 1];
    arr = new int[len];
    for (int i = 0; i < len; i++) {
      arr[i] = Integer.parseInt(br.readLine());
    }
  }

  public static int solution() {
    for (int i = 0; i < len; i++) {
      int one = arr[i];
      for (int j = 1; j <= target; j++) {
        cache[i][j] = Math.min(getValue(i, j - one) + 1, getValue(i - 1, j));
      }
    }
    return cache[len - 1][target] == IMPOSSIBLE ? -1 : cache[len-1][target];
  }

  public static int getValue(int row, int col) {
    if (row < 0 || col < 0) return IMPOSSIBLE;
    if (row > cache.length || col > cache[0].length) return IMPOSSIBLE;
    return cache[row][col];
  }
}
