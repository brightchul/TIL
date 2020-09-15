package programmers.coloringBook;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {
  int[][] visited;
  int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  int rowLen, colLen;

  public int[] solution(int m, int n, int[][] picture) {
    rowLen = m;
    colLen = n;
    visited = new int[rowLen][colLen];

    int comp = 0;
    int maxSize = 0;

    for (int row = 0; row < rowLen; row++) {
      for (int col = 0; col < colLen; col++) {
        if (picture[row][col] == 0) continue;

        // 중복으로 들어갈 수 있어서 그것을 제외시키기 위해 사용
        if (visited[row][col] != 0) continue;

        // visited에 0일경우 순회하지 않은 컴포넌트이니 컴포넌트를 세팅한다.
        ++comp;

        // 큐를 만들고 동일 칼라를 설정한다.
        Queue<Pair> q = new LinkedList<>();
        q.offer(pair(row, col));

        int color = picture[row][col];
        int size = 0;

        // 큐가 다 떨어지기 전까지 계속 무한으로 돈다.
        while (q.size() > 0) {
          Pair target = q.poll();
          if (visited[target.row][target.col] != 0) continue;

          size++;
          visited[target.row][target.col] = comp;

          for (int[] direction : directions) {
            int newRow = target.row + direction[0];
            int newCol = target.col + direction[1];

            if (isOutRange(newRow, newCol)) continue;
            if (picture[newRow][newCol] == color && visited[newRow][newCol] == 0) {
              q.offer(pair(newRow, newCol));
            }
          }
        }

        maxSize = Math.max(maxSize, size);
      }
    }
    return new int[] {comp, maxSize};
  }

  public boolean isOutRange(int row, int col) {
    if (row < 0 || row >= rowLen) return true;
    if (col < 0 || col >= colLen) return true;
    return false;
  }

  public Pair pair(int row, int col) {
    return new Pair(row, col);
  }

  static class Pair {
    int row, col;

    public Pair(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }
}
