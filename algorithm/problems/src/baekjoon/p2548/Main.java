package baekjoon.p2548;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  public static void main(String[] args) throws IOException {
    int len = Integer.parseInt(br.readLine()), idx = 0;
    int[] arr = new int[len];
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");

    while (idx < len) {
      arr[idx++] = Integer.parseInt(st.nextToken());
    }
    Arrays.sort(arr);

    int mid = len / 2;
    bw.write(arr[mid - (len % 2 ^ 1)] + "");
    bw.flush();
  }
}
