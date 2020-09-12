package programmers.terrainMovement;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 1차시도. 성능 때문에 시랲
 * https://programmers.co.kr/learn/courses/30/lessons/62050?language=java [[1, 4, 8, 10], [5, 5, 5,
 * 5], [10, 10, 10, 10], [10, 10, 10, 20]]
 */
public class Solution {
  int rowLen, colLen;
  int[][] land;
  int height;
  int[][] directions = {{+1, 0}, {0, +1}};

  public int solution(int[][] land, int height) {
    this.land = land;
    this.height = height;
    rowLen = land.length;
    colLen = land[0].length;

    // 받아서 각 좌표간의 비용으로 된 배열을 만든다.
    int[][] costArr = getCostTotalArr();

    // 그 배열을 비용 오름차순으로 정렬한다.
    Arrays.sort(costArr, new AscCost());

    // 크루스칼 알고리즘 ㄱㄱ
    int result = getTotalCost(costArr);

    return result;
  }

  public int getTotalCost(int[][] arr) {
    int[][] checkArr = new int[rowLen * colLen][4];

    int totalCost = 0;

    for (int[] one : checkArr) Arrays.fill(one, -1);

    for (int[] one : arr) {
      if (!isConnect(one[0], one[1], checkArr, makeArr())) {
        pushOne(checkArr[one[0]], one[1]);
        pushOne(checkArr[one[1]], one[0]);
        totalCost += one[2];
      }
    }
    return totalCost;
  }

  public void pushOne(int[] arr, int value) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == -1) {
        arr[i] = value;
        return;
      }
    }
  }

  public boolean isConnect(int start, int end, int[][] arr, int[] passed) {
    if (passed[start] != -1) return false;
    else passed[start] = start;

    int[] oneArr = arr[start];

    if (containOne(oneArr, end)) return true;

    for (int one : oneArr) {
      if (one == -1) continue;
      if (isConnect(one, end, arr, passed)) return true;
    }
    return false;
  }

  public int[] makeArr() {
    int[] arr = new int[rowLen * colLen];
    Arrays.fill(arr, -1);
    return arr;
  }

  public int[][] getCostTotalArr() {
    int[][] someArr = new int[rowLen * colLen * 2 - rowLen - colLen][];

    int idx = 0;
    for (int row = 0; row < rowLen; row++) {
      for (int col = 0; col < colLen; col++) {
        for (int[] dir : directions) {
          if (isInRange(row + dir[0], col + dir[1])) {
            someArr[idx++] = getCostArr(row, col, row + dir[0], col + dir[1]);
          }
        }
      }
    }
    return someArr;
  }

  public boolean containOne(int[] arr, int target) {
    for (int one : arr) if (one == target) return true;
    return false;
  }

  public int[] getCostArr(int row1, int col1, int row2, int col2) {
    return new int[] {getNum(row1, col1), getNum(row2, col2), getCost(row1, col1, row2, col2)};
  }

  public boolean isInRange(int row, int col) {
    if (row < 0 || col < 0) return false;
    if (row >= rowLen || col >= colLen) return false;
    return true;
  }

  public int getNum(int row, int col) {
    return row * colLen + col;
  }

  public int getCost(int row1, int col1, int row2, int col2) {
    int gap = Math.abs(land[row1][col1] - land[row2][col2]);
    return gap > height ? gap : 0;
  }

  static class AscCost implements Comparator<int[]> {

    @Override
    public int compare(int[] a, int[] b) {
      return a[2] - b[2];
    }
  }
}
