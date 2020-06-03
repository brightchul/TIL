package baekjoon.p1463;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int INF = 1000000003;
    private static int[] dp = new int[1000001];
    static {
        Arrays.fill(dp, -1);
        dp[0] = dp[1] = 0;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        System.out.println(solution(input));
    }
    public static int solution(int input) {
        if(dp[input] > -1) return dp[input];
        else {
            int three=INF, two=INF, one=INF;

            if(input % 3 == 0)
                three = dp[input/3] > -1 ? dp[input/3] : solution(input/3);

            if(input % 2 == 0)
                two = dp[input/2] > -1 ? dp[input/2] : solution(input/2);

            one = dp[input-1] > -1 ? dp[input-1] : solution(input-1);

            int value = Math.min(Math.min(three, two), one)+1;
            dp[input] = value;
            return value;
        }
    }
}
