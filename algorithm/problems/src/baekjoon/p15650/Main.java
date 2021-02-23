package baekjoon.p15650;
/*
https://www.acmicpc.net/problem/15650
N과 M(2)

 */
import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main{
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static boolean[] isUsed;
    private static HashSet<String> set;
    private static StringBuilder sb;
    private static String[] arr;

    private static int N, M;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " " );
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new String[M];
        isUsed = new boolean[N+1];
        run(1, 0);
        bw.flush();
        bw.close();
    }
    public static void run(int start, int cur) throws IOException {
        if(cur == M) {
            bw.write(String.join(" ", arr) + "\n");
            return;
        }

        for(int i = start; i <= N; i++) {
            if(isUsed[i]) continue;

            isUsed[i] = true;
            arr[cur] = String.valueOf(i);
            run(i,cur+1);
            isUsed[i] = false;
        }
    }
}

class Main2 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static boolean[] isUsed;
    private static HashSet<String> set;
    private static StringBuilder sb;

    public static void main(String[] args)throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " " );
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        isUsed = new boolean[N+1];
        set = new HashSet<>();
        sb = new StringBuilder();
        run(N, M, 0, "");
        System.out.println(sb.toString());
    }

    public static void run(int N, int M, int cur, String text) {
        if (M == cur) {
            for (String one : set) {
                int count = 0;
                for (String oneText : text.split(" ")) {
                    if (one.indexOf(oneText) > -1) count++;
                }
                if (count == M) return;
            }

            set.add(text);
            sb.append(text).append("\n");
            return;
        }
        for (int i = 1; i <= N; i++) {
            if (isUsed[i]) continue;
            isUsed[i] = true;
            run(N, M, cur + 1, text + i + " ");
            isUsed[i] = false;
        }
    }
}
