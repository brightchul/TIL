package baekjoon.p2757;
/*
https://www.acmicpc.net/problem/2757

R1C1
R3C1
R1C3
R299999999C26
R52C52
R53C17576
R53C17602
R0C0
 */

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static void main(String[] args) throws IOException {
    Pattern pattern = Pattern.compile("\\d+");
    int row = 0, col = 0;

    while (true) {
      Matcher matcher = pattern.matcher(br.readLine());
      if (matcher.find()) row = Integer.parseInt(matcher.group());
      if (matcher.find()) col = Integer.parseInt(matcher.group());
      if (row == 0 && col == 0) break;
      bw.write(solution(row, col));
      bw.write("\n");
    }
    bw.flush();
    bw.close();
  }

  public static String solution(int row, int col) {
    StringBuilder sb = new StringBuilder();

    while (col > 0) {
      sb.append(ALPHA.charAt((col - 1) % 26));
      col = (col - 1) / 26;
    }
    sb.reverse();
    sb.append(row);
    return sb.toString();
  }
}
