package programmers.threeNTiling;

public class Solution {
  public int solution(int n) {
    if (n % 2 == 1) return 0;

    long[] K = new long[n + 1];
    long[] A = new long[n + 1];
    K[1] = 1;
    K[2] = 3;
    A[2] = 2;

    for (int i = 4; i <= n; i += 2) {
      A[i] = A[i - 2] + K[i - 4] * 2;
      K[i] = (3 * K[i - 2] + A[i]) % 1000000007;
    }

    return (int) K[n];
  }
}
