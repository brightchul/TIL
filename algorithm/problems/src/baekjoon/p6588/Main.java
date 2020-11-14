package baekjoon.p6588;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final int NUM = 1000000;
    private static final int[] arr = new int[NUM + 1];
    private static int[] primeArr;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        initPrimeArr();
        while (true) {
            int target = Integer.parseInt(br.readLine());
            if (target == 0) break;
            int resultIdx = getSolutionIdx(target);


            if(resultIdx > -1) {
                sb.append(target).append(" = ").append(primeArr[resultIdx]).append(" + ").append(target-primeArr[resultIdx]);
            } else {
                sb.append("Goldbach's conjecture is wrong.");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static int getSolutionIdx(int target) {
        int last = target / 2;
        for (int i = 0; primeArr[i] <= last; i++) {
            if (arr[target - primeArr[i]] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void initPrimeArr() {
        arr[0] = arr[1] = 1;
        int last = (int) Math.sqrt(NUM);
        for (int idx = 2; idx <= last; idx++) {
            if (arr[idx] > 0) continue;
            for (int i = idx * idx; i <= NUM; i += idx) {
                arr[i] = 1;
            }
        }
        int len = 0;
        int[] temp = new int[last];
        for (int i = 2; i <= last; i++) {
            if (arr[i] == 0) {
                temp[len++] = i;
            }
        }
        primeArr = Arrays.copyOf(temp, len);
    }
}

