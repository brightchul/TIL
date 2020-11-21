package baekjoon.p2240;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
7 2
2
1
1
2
2
1
1

7 2
1
2
2
2
2
2
2
 */

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int[][] cache;

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    int len = Integer.parseInt(st.nextToken());
    int jumpCount = Integer.parseInt(st.nextToken());
    cache = new int[jumpCount + 1][len];

    for (int i = 0; i < len; i++) {
      int curTree = Integer.parseInt(br.readLine());

      for (int j = 0; j <= jumpCount; j++) {
        int tree = j % 2 == 0 ? 1 : 2;

        cache[j][i] =
            Math.max(getValue(j, i - 1), getValue(j - 1, i - 1)) + (tree == curTree ? 1 : 0);
      }
    }

    int result = 0;
    for (int i = 0; i <= jumpCount; i++) {
      result = Math.max(result, cache[i][len - 1]);
    }
    System.out.println(result);
  }

  private static int getValue(int row, int col) {
    if (row < 0 || col < 0) return 0;
    if (row >= cache.length || col >= cache[0].length) return 0;
    return cache[row][col];
  }
}
