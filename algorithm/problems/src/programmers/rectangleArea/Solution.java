package programmers.rectangleArea;

/*
1차 시도
효율성테스트를 통과하지는 못했다.
 */

import java.util.Set;
import java.util.TreeSet;

public class Solution {
  public long solution(int[][] rectangles) {

    Set<Integer> xSet = new TreeSet<>();
    Set<Integer> ySet = new TreeSet<>();
    setArr(xSet, ySet, rectangles);

    int[] xArr = convertIntArr(xSet);
    int[] yArr = convertIntArr(ySet);

    long area = 0L;

    for (int xIdx = 0; xIdx < xArr.length; xIdx++) {
      for (int yIdx = 0; yIdx < yArr.length; yIdx++) {

        int x = xArr[xIdx], y = yArr[yIdx];
        for (int[] rect : rectangles) {
          if (rect[0] <= x && x < rect[2] && rect[1] <= y && y < rect[3]) {
            area += (long) (xArr[xIdx + 1] - x) * (yArr[yIdx + 1] - y);
            break;
          }
        }
      }
    }

    return area;
  }

  public void setArr(Set<Integer> xSet, Set<Integer> ySet, int[][] rectangles) {
    for (int[] rec : rectangles) {
      xSet.add(rec[0]);
      xSet.add(rec[2]);
      ySet.add(rec[1]);
      ySet.add(rec[3]);
    }
  }

  public int[] convertIntArr(Set<Integer> set) {
    int[] result = new int[set.size()];
    int idx = 0;
    for (int one : set) {
      result[idx++] = one;
    }
    return result;
  }
}
