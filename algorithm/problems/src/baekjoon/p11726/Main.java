package baekjoon.p11726;
/*
https://www.acmicpc.net/problem/11726
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    int input = Integer.parseInt(br.readLine());
    System.out.println(solution(input));
  }

  public static int solution(int num) {
    if (num <= 3) return num;
    int pre1 = 3, pre2 = 2, result = 0;
    while (num-- > 3) {
      result = pre1 + pre2;
      pre2 = pre1;
      pre1 = result;
    }
    return result;
  }
}
