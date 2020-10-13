package baekjoon.p10844;
/*
https://www.acmicpc.net/problem/10844
1
return 9

2
return 17
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
  private static final int DIV = 1_000_000_000;
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    int len = Integer.parseInt(br.readLine());
    long[][] arr = new long[len + 1][10];
    Arrays.fill(arr[0], 1);
    Arrays.fill(arr[1], 1);

    for (int row = 2; row <= len; row++) {
      for (int col = 1; col < 10; col++) {
        switch (col) {
          case 1:
            arr[row][col] = (arr[row - 2][col] + arr[row - 1][col + 1]) % DIV;
            break;
          case 9:
            arr[row][col] = arr[row - 1][col - 1];
            break;
          default:
            arr[row][col] = (arr[row - 1][col - 1] + arr[row - 1][col + 1]) % DIV;
            break;
        }
      }
    }

    long result = 0;
    for (int i = 1; i < 10; i++) result += arr[len][i];

    System.out.println(result % DIV);
  }
}
