package baekjoon.p2671;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
10011001의 경우
find()를 사용하면 10011, 0(no match), 01 하므로 false가 나온다.
matches()를 사용하면 문자열 전체에 적용되는지를 체크하므로 true가 나온다.
1001 1001 이렇게 매치가 가능하다.

TODO : 유한오토마타로도 풀어보기
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Pattern pt = Pattern.compile("(100+1+|01)+");
    public static void main(String[] args) throws IOException {
        String input = br.readLine();
        System.out.println(run(input) ? "SUBMARINE" : "NOISE");
    }
    public static boolean run(String input) {
        StringBuilder sb = new StringBuilder();
        Matcher mat = pt.matcher(input);
        return mat.matches();
    }
    // 아래는 틀린 메서드이다.
    public static boolean runFalsy(String input) {
        StringBuilder sb = new StringBuilder();
        Matcher mat = pt.matcher(input);
        while(mat.find()) {
            System.out.println(mat.group());
            sb.append(mat.group());
        }
        return sb.toString().equals(input);
    }
}
