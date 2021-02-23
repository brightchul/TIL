package baekjoon.p15649;

/*
백트래킹
https://www.acmicpc.net/problem/15649
3 1

4 2

input : 4 4

output :
1 2 3 4
1 2 4 3
1 3 2 4
1 3 4 2
1 4 2 3
1 4 3 2
2 1 3 4
2 1 4 3
2 3 1 4
2 3 4 1
2 4 1 3
2 4 3 1
3 1 2 4
3 1 4 2
3 2 1 4
3 2 4 1
3 4 1 2
3 4 2 1
4 1 2 3
4 1 3 2
4 2 1 3
4 2 3 1
4 3 1 2
4 3 2 1

 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        boolean[] isUsed = new boolean[N+1];

        run(N, M, isUsed, 0, "");
        System.out.println(sb.toString());
    }
    public static void run(int N, int M, boolean[] isUsed, int cur, String text) {
        if(cur == M) {
            sb.append(text).append("\n");
            return;
        }
        for(int i = 1; i<=N; i++) {
            if(isUsed[i]) continue;
            isUsed[i] = true;
            run(N, M, isUsed, cur + 1, text + i + " ");
            isUsed[i] = false;
        }
    }
}
