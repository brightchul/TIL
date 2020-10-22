package baekjoon.p2193;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static long[] cache = new long[91];

  public static void main(String[] args) throws IOException {
    int num = Integer.parseInt(br.readLine());
    cache[1] = cache[2] = 1;
    cache[3] = 2;

    System.out.println(solution(num));
  }

  public static long solution(int num) {
    if (cache[num] == 0) cache[num] = solution(num - 1) + solution(num - 2);
    return cache[num];
  }
}
