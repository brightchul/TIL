package baekjoon.p9095;
/*
https://www.acmicpc.net/problem/9095
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static int[] arr = new int[12];

  public static void main(String[] args) throws IOException {
    arr[1] = 1;
    arr[2] = 2;
    arr[3] = 4;

    int len = getInputNum();
    while (len-- > 0) {
      int target = getInputNum();
      System.out.println(getValue(target));
    }
  }

  public static int getInputNum() throws IOException {
    return Integer.parseInt(br.readLine());
  }

  public static int getValue(int num) {
    if (arr[num] > 0) return arr[num];
    return arr[num] = getValue(num - 1) + getValue(num - 2) + getValue(num - 3);
  }
}
