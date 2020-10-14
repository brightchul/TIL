package baekjoon.p2156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*

6
1000
1000
1
1
1000
1000

return 4000

 */

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    int len = Integer.parseInt(br.readLine());
    int[] arr = new int[len + 1];
    int[] cache = new int[len + 1];

    if (len == 1) {
      System.out.println(Integer.parseInt(br.readLine()));
      return;
    }

    arr[1] = cache[1] = Integer.parseInt(br.readLine());
    arr[2] = Integer.parseInt(br.readLine());
    cache[2] = arr[2] + cache[1];

    for (int i = 3; i <= len; i++) {
      arr[i] = Integer.parseInt(br.readLine());
      cache[i] = getMax(arr[i] + getMax(cache[i - 2], cache[i - 3] + arr[i - 1]), cache[i - 1]);
    }
    System.out.println(cache[len]);
  }

  public static int getMax(int... arr) {
    int result = -1;
    for (int one : arr) result = Math.max(result, one);
    return result;
  }
}
