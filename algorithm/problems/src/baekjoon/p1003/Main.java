package baekjoon.p1003;

import java.io.*;

class Main {
    private static int[][] arr = new int[41][2];
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static {
        arr[0][0] = arr[1][1] = 1;
        arr[0][1] = arr[1][0] = 0;
    }
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        int totalCount = Integer.parseInt(br.readLine());
        while(totalCount-- > 0) {
            int input = Integer.parseInt(br.readLine());
            int[] result = solution(input);
            sb.append(result[0]).append(" ").append(result[1]).append("\n");
        }
        System.out.print(sb.toString());
    }
    public static int[] solution(int num) {
        if(hasValue(num)) return arr[num];
        else {
            int[] preArr1 = solution(num-1);
            int[] preArr2 = solution(num-2);
            arr[num][0] = preArr1[0] + preArr2[0];
            arr[num][1] = preArr1[1] + preArr2[1];
        }
        return arr[num];
    }
    public static boolean hasValue(int idx) {
        return arr[idx][0] + arr[idx][1] > 0;
    }
}
