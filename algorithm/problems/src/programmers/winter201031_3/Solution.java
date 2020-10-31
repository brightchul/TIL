package programmers.winter201031_3;

import java.util.Arrays;

public class Solution {
  int rowLen, colLen;

  public static void main(String[] args) {
    Solution s = new Solution();
    int[][] a = {{0, 0, 1, 1}, {1, 1, 1, 1}, {2, 2, 2, 1}, {0, 0, 0, 2}};
    System.out.println(Arrays.toString(s.solution(a)));
  }

  public int[] solution(int[][] v) {
    int[] answer = new int[3];
    rowLen = v.length;
    colLen = v[0].length;

    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        if (v[i][j] == -1) continue;
        else {
          answer[v[i][j]] += 1;
          recur(i, j, v, v[i][j]);
        }
      }
    }

    return answer;
  }

  public void recur(int row, int col, int[][] arr, int value) {
    if (row < 0 || row >= rowLen) return;
    if (col < 0 || col >= colLen) return;
    if (arr[row][col] == -1) return;
    if (arr[row][col] == value) arr[row][col] = -1;
    else return;

    recur(row - 1, col, arr, value);
    recur(row, col - 1, arr, value);
    recur(row + 1, col, arr, value);
    recur(row, col + 1, arr, value);
  }
}
