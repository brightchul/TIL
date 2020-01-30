package programmers.schoolWay;

import java.util.Arrays;

/*
잘못푼 코드.. 효율성에서 패쓰 하지 못함.
 */
public class WrongSolution {
    boolean[][] map;
    long count = 0;
    int destRow, destCol;
    public static void main(String[] args) {
        WrongSolution s = new WrongSolution();
        System.out.println(s.solution(4,3, new int[][]{{2,2}}));
    }
    public int solution(int m, int n, int[][] puddles) {
        destRow = n;
        destCol = m;
        count = 0;
        map = new boolean[n+1][m+1];
        for(boolean[] bArr : map)
            Arrays.fill(bArr, true);

        for(int[] one : puddles)
            map[one[1]][one[0]] = false;

        recursive(1, 1);
        return (int)(count);
    }
    public boolean recursive(int row, int col) {
        if(isOutOfBounder(row, col)) return false;
        if(!map[row][col]) return false;
        if(row == destRow && col == destCol) {
            count  = (count + 1) % 1000000007;
            return true;
        }

        boolean row_1 = isOutOfBounder(row+1, col);
        if(!row_1) {
           row_1 = map[row + 1][col] && recursive(row + 1, col);
        }
        boolean col_1 = isOutOfBounder(row, col+1);
        if(!col_1) {
            col_1 = map[row][col+1] && recursive(row, col + 1);
        }
        return map[row][col] = row_1 || col_1;
    }
    public boolean isOutOfBounder(int row, int col) {
        return row >= map.length || col >= map[0].length;
    }
}
