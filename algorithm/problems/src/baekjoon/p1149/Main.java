package baekjoon.p1149;
/*
https://www.acmicpc.net/problem/1149
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    System.out.println(solution());
  }

  public static int solution() throws IOException {
    int len = Integer.parseInt(br.readLine());

    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    if (len == 1) {
      return getMin(prsInt(st), prsInt(st), prsInt(st));
    }

    int[][] arr = new int[len][3];
    arr[0][0] = prsInt(st);
    arr[0][1] = prsInt(st);
    arr[0][2] = prsInt(st);

    int idx = 0;
    while (++idx < len) {
      st = new StringTokenizer(br.readLine(), " ");

      arr[idx][0] = prsInt(st) + getMin(arr[idx - 1][1], arr[idx - 1][2]);
      arr[idx][1] = prsInt(st) + getMin(arr[idx - 1][0], arr[idx - 1][2]);
      arr[idx][2] = prsInt(st) + getMin(arr[idx - 1][0], arr[idx - 1][1]);
    }
    return getMin(arr[idx - 1]);
  }

  public static int prsInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
  }

  public static int getMin(int... arr) {
    int result = Integer.MAX_VALUE;
    for (int one : arr) {
      result = Math.min(result, one);
    }
    return result;
  }
}
