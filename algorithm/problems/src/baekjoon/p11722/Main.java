package baekjoon.p11722;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    // 초기화 및 input 받기
    int[] cache = new int[10001];
    int len = Integer.parseInt(br.readLine());
    int[] inputArr = new int[len];
    StringTokenizer st = new StringTokenizer(br.readLine());

    for (int i = 0; i < len; i++)
      inputArr[i] = Integer.parseInt(st.nextToken());

    // solution
    int max = -1;
    for (int i = 0; i < len; i++) {
      int current = inputArr[i], count = 1;

      for (int j = i; j >= 0; j--) {
        int pre = inputArr[j];
        if (pre > current) {
          count = Math.max(count, cache[pre] + 1);
        }
      }
      max = Math.max(max, (cache[current] = count));
    }
    System.out.println(max);
  }
}
