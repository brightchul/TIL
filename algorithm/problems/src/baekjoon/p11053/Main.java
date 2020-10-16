package baekjoon.p11053;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int[] cache;
  private static int N;

  public static void main(String[] args) throws IOException {
    int len = Integer.parseInt(br.readLine());
    int[] arr = initArr(len);

    System.out.println(lis(arr));
  }

  private static int lis(int[] arr) {
    arr[0] = Integer.MIN_VALUE;
    N = arr.length;
    cache = new int[N];
    Arrays.fill(cache, -1);

    return find(0, arr);
  }

  private static int find(int start, int[] arr) {
    if (cache[start] != -1) return cache[start];
    int ret = 0;
    for (int i = start + 1; i < N; i++) {
      if (arr[start] < arr[i]) {
        ret = Math.max(ret, find(i, arr) + 1);
      }
    }
    return cache[start] = ret;
  }

  private static int[] initArr(int len) throws IOException {
    int idx = 1;
    int[] arr = new int[len + 1];
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    while (idx <= len) arr[idx++] = Integer.parseInt(st.nextToken());

    return arr;
  }
}
