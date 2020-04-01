package baekjoon.p2667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
https://www.acmicpc.net/problem/2667
단지번호붙이기
// 오름차순 정렬
7
0110100
0110101
1110101
0000111
0100000
0111110
0111000

 */

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] countArr;

    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        char[][] board = new char[len][];
        int[][] cache = new int[len][len];
        countArr = new int[len * len];

        getInput(board, len);
        int count = run(board, cache, len);
        Arrays.sort(countArr);
        showResult(count);
    }
    public static void getInput(char[][] board, int len) throws IOException {
        int idx = 0;
        for(int i=0; i<len; i++) {
            board[idx++] = br.readLine().toCharArray();
        }
    }
    public static int run(char[][] board, int[][] cache, int len) {
        int count = 0;
        for(int y=0; y < len; y++) {
            for(int x=0; x<len; x++) {
                if(board[y][x] == '0') continue;
                if(cache[y][x] > 0) continue;
                count++;
                recur(x, y, count, board, cache);
            }
        }
        return count;
    }
    public static void recur(int x, int y, int count, char[][] board, int[][] cache) {
        if(x < 0 || y < 0) return;
        if(x >= board.length || y >= board.length) return;
        if(board[y][x] == '0'|| cache[y][x] > 0) return;

        cache[y][x] = count;
        countArr[count]++;

        recur(x-1, y, count, board, cache);
        recur(x, y-1, count, board, cache);
        recur(x+1, y, count, board, cache);
        recur(x, y+1, count, board, cache);
    }
    public static void showResult(int count) {
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        for(int i=countArr.length-count; i<countArr.length; i++) {
            sb.append("\n").append(countArr[i]);
        }
        System.out.println(sb.toString());
    }
}
