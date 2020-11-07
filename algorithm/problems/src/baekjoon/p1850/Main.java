package baekjoon.p1850;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    long one = Long.parseLong(st.nextToken());
    long two = Long.parseLong(st.nextToken());

    long big = Math.max(one, two);
    long small = Math.min(one, two);
    long temp;

    while (true) {
      temp = big % small;
      if(temp == 0) {
        System.out.println("1".repeat((int)small));
        break;
      }
      big = small;
      small = temp;
    }
  }
}
