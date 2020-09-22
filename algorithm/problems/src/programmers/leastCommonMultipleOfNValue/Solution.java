package programmers.leastCommonMultipleOfNValue;
// https://programmers.co.kr/learn/courses/30/lessons/12953s

class Solution {
  public int solution(int[] arr) {
    int result = 1;
    for (int one : arr) result = getLCM(result, one);
    return result;
  }

  public int getLCM(int n1, int n2) {
    return (n1 * n2) / getGCD(n1, n2);
  }

  public int getGCD(int n1, int n2) {
    int big = Math.max(n1, n2);
    int small = Math.min(n1, n2);
    int temp = 0;

    while (big % small > 0) {
      temp = big % small;
      big = small;
      small = temp;
    }
    return small;
  }
}
