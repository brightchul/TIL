package baekjoon.p1740;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        long n = Long.parseLong(br.readLine());
        System.out.println(solution(n));
    }
    public static long solution(long n) {
        String binaryN = Long.toBinaryString(n);
        return Long.parseLong(binaryN, 3);
    }
}
