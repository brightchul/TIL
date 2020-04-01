package baekjoon.p2668;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/*
https://www.acmicpc.net/problem/2668
숫자 고르기

7
3
1
1
5
5
4
6

4
2
3
4
1
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        int[] arr = new int[len+1];
        List<Integer> list = new ArrayList<>();

        for(int i=1; i<=len; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        for(int i=1; i<=len; i++) {
            if(run(i, i, arr, new ArrayList<>()))
                list.add(i);
        }

        showResult(list);
    }
    public static boolean run(int origin, int one, int[] arr, List<Integer> list) {
        if(origin == arr[one]) return true;
        if(list.contains(arr[one])) {
            return list.contains(origin);
        }
        list.add(arr[one]);
        return run(origin, arr[one], arr, list);
    }
    public static void showResult(List<Integer>list) {
        StringBuilder sb = new StringBuilder();
        sb.append(list.size());
        for(int i=0; i<list.size(); i++)
            sb.append("\n").append(list.get(i));
        System.out.println(sb.toString());
    }
}
