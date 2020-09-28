package programmers.walkerHeaven;

/** https://programmers.co.kr/learn/courses/30/lessons/1832 */
class Solution {
  final int MOD = 20170805;
  int m, n;
  int[][] cityMap, cache;

  public int solution(int m, int n, int[][] cityMap) {
    this.m = m;
    this.n = n;
    this.cityMap = cityMap;

    cache = new int[m][n];
    cache[0][0] = 1;

    for (int row = 0; row < m; row++) {
      for (int col = 0; col < n; col++) {
        if (cityMap[row][col] > 0) continue;

        if (!isOut(row - 1, col)) {
          cache[row][col] += getValue(row - 1, col, Direction.UP) % MOD;
        }
        if (!isOut(row, col - 1)) {
          cache[row][col] += getValue(row, col - 1, Direction.LEFT) % MOD;
        }
      }
    }
    return cache[m - 1][n - 1] % MOD;
  }

  public int getValue(int row, int col, Direction direction) {
    if (isOut(row, col)) return 0;

    switch (cityMap[row][col]) {
      case 2:
        return direction == Direction.UP
            ? getValue(row - 1, col, direction)
            : getValue(row, col - 1, direction);
      case 0:
        return cache[row][col];
      default:
        return 0;
    }
  }

  public boolean isOut(int row, int col) {
    return (row < 0 || row >= m) || (col < 0 || col >= n);
  }

  static enum Direction {
    NONE,
    UP,
    LEFT
  }
}
