package baekjoon.p1331;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/*
// https://www.acmicpc.net/problem/1331 나이트 투어

A1
B3
A5
C6
E5
F3
D2
F1
E3
F5
D4
B5
A3
B1
C3
A2
C1
E2
F4
E6
C5
A6
B4
D5
F6
E4
D6
C4
B6
A4
B2
D1
F2
D3
E1
C2

return Valid
 */

public class Main {
  static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
  static final String INVALID = "Invalid";
  static final String VALID = "Valid";

  public static void main(String[] args) throws IOException {
    String[] textArr = new String[36];
    for (int i = 0; i < 36; i++) textArr[i] = BR.readLine();

    System.out.println(solution(textArr));
  }

  public static String solution(String[] arr) {
    String startPoint, one;
    startPoint = one = arr[0];

    Set<String> s = new HashSet<>();
    s.add(one);

    for (int i = 1; i < 36; i++) {
      String temp = arr[i];

      if (s.contains(temp) || !check(one, temp)) return INVALID;

      one = temp;
      s.add(temp);
    }
    return check(startPoint, one) ? VALID : INVALID;
  }

  public static boolean check(String one, String temp) {
    int alpha = Math.abs(one.charAt(0) - temp.charAt(0));
    int number = Math.abs(one.charAt(1) - temp.charAt(1));

    if (alpha == 1 && number == 2) return true;
    return alpha == 2 && number == 1;
  }
}

/*
String tArr1 = String[]{"A1","B3","A5","C6","E5","F3","D2","F1","E3","F5","D4","B5","A3","B1","C3","A2","C1","E2","F4","E6","C5","A6","B4","D5","F6","E4","D6","C4","B6","A4","B2","D1","F2","D3","E1","C2"}

 */