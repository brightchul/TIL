package programmers.winter201031_1;

import java.util.Arrays;
import java.util.Comparator;

/*
 */
public class Solution {

  public static void main(String[] args) {
    int[][] arr = {{1, 3, 1}, {3, 5, 0}, {5, 4, 0}, {2, 5, 0}};
    Solution s = new Solution();
    System.out.println(s.solution(6, arr));
  }

  public String solution(int n, int[][] delivery) {
    String answer = "";
    int[] ansArr = new int[n + 1];
    Comparator comp =
        new Comparator<int[]>() {
          @Override
          public int compare(int[] o1, int[] o2) {
            return o2[2] - o1[2];
          }
        };

    Arrays.sort(delivery, comp);

    for (int[] arr : delivery) {
      if (arr[2] == 1) {
        ansArr[arr[0]] = ansArr[arr[1]] = 1;
      } else {
        if (ansArr[arr[0]] == 1) {
          ansArr[arr[1]] = -1;
        } else if (ansArr[arr[1]] == 1) {
          ansArr[arr[0]] = -1;
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    String str = "X?O";

    for (int i = 1; i <= n; i++) {
      sb.append(str.charAt(ansArr[i] + 1));
    }

    return sb.toString()  ;
  }
}
