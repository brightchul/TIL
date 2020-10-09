package baekjoon.p2579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    System.out.println(solution());
  }

  public static int solution() throws IOException {
    int idx = 1, len = inputValue();

    int[] arr = new int[len + 1];
    int[][] cache = new int[len + 1][2];

    while (idx <= len) arr[idx++] = inputValue();

    idx = 1;
    cache[1][0] = cache[1][1] = arr[1];

    while (++idx <= len) {
      cache[idx][0] = arr[idx] + cache[idx - 1][1];
      cache[idx][1] = arr[idx] + getMax(cache[idx - 2]);
    }
    return getMax(cache[idx - 1]);
  }

  public static int getMax(int[] arr) {
    return Math.max(arr[0], arr[1]);
  }

  public static int inputValue() throws IOException {
    return Integer.parseInt(br.readLine());
  }
}
