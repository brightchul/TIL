package algospot.BOGGLE;
/*
https://algospot.com/judge/problem/read/BOGGLE
보글 게임

입력
1
URLPM
XPRET
GIAET
XTNZY
XOQRS
6
PRETTY
GIRL
REPEAT
KARA
PANDORA
GIAZAPX

출력
PRETTY YES
GIRL YES
REPEAT YES
KARA NO
PANDORA NO
GIAZAPX YES

메모이제이션이 필요한 문제이다.
 */
import java.io.*;
import java.util.Arrays;

public class Main {
    final static int BOARD_SIZE = 5;
    final static int[][] way = new int[][]{{-1,-1}, {-1,0}, {-1, 1},{0, -1}, {0, 1},{1, -1}, {1, 0}, {1, 1}};
    static boolean[][][] cache = new boolean[BOARD_SIZE][BOARD_SIZE][];
    static char[][] board = new char[BOARD_SIZE][];
    static String targetWord;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int totalCaseNum = Integer.parseInt(br.readLine());
        for(int i=0; i<totalCaseNum; i++) {
            // 게임판 저장
            for(int j=0; j<BOARD_SIZE; j++) board[j] = br.readLine().toCharArray();

            // 대상 단어들 저장
            int wordCaseNum = Integer.parseInt(br.readLine());

            for(int j=0; j<wordCaseNum; j++){
                targetWord = br.readLine();
                initCache(targetWord.length());
                bw.write(String.format("%s %s\n", targetWord, run() ? "YES" : "NO"));
            }
        }
        bw.flush();
        bw.close();
    }
    public static boolean run() {
        for(int row=0; row<BOARD_SIZE; row++) {
            for(int col=0; col< BOARD_SIZE; col++) {
                if(recursive(col, row,0)) return true;
            }
        }
        return false;
    }
    public static boolean recursive(int x, int y, int idx) {
        if(!isBorder(x, y) || idx==targetWord.length() || !cache[y][x][idx]) return false;
        if(targetWord.charAt(idx) != board[y][x]) return cache[y][x][idx] = false;
        if(idx == targetWord.length()-1) return true;

        for(int d=0; d<8; d++) {
            if(recursive(x+way[d][0], y+way[d][1], idx+1)) return true;
        }
        return cache[y][x][idx] = false;
    }
    public static boolean isBorder(int x, int y) {
        if(x < 0 || x >= BOARD_SIZE) return false;
        if(y < 0 || y >= BOARD_SIZE) return false;
        return true;
    }
    public static void initCache(int len) {
        for(int row=0; row<BOARD_SIZE; row++) {
            for(int col=0; col<BOARD_SIZE; col++) {
                cache[row][col] = new boolean[len];
                Arrays.fill(cache[row][col], true);
            }
        }
    }
}
