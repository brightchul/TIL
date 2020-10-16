package baekjoon.p11053;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main2 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        List<Integer> list = initList(len);
        System.out.println(lis(list));
    }

    // 단순히 완전탐색 방법 O(2^n) 이다.
    private static int lis(List<Integer> list) {
        int len = list.size();
        if (len == 0) return 0;
        int result = 1;

        for (int i = 0; i < len - 1; i++) {
            int one = list.get(i);
            List<Integer> newList = new ArrayList<>();
            for (int j = i + 1; j < len; j++) {
                if (one < list.get(j)) newList.add(list.get(j));
            }
            result = Math.max(result, 1 + lis(newList));
        }
        return result;
    }
    private static List<Integer> initList(int len) throws IOException {
        int idx = 0;
        List<Integer> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        while (st.hasMoreTokens()) list.add(Integer.parseInt(st.nextToken()));

        return list;
    }
}
