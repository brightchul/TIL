package baekjoon.p2669;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
https://www.acmicpc.net/problem/2669
직사각형 네개의 합집합의 면적 구하기
1 2 4 4
2 3 5 7
3 1 6 5
7 3 8 6
// 중간에 " "이 다르게 들어가기도 하므로 char[]로 적용하면 안될 수도 있다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int[][] board = new int[101][101];
        int count = 0;

        for(int i=0; i<4; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            for(int y=y1; y<y2; y++) {
                for(int x=x1; x < x2; x++) {
                    if(board[y][x] == 0) {
                        board[y][x] = 1;
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
