package programmers.threeNTiling;

public class Solution {
  public int solution(int n) {
    if (n % 2 == 1) return 0;
    if (n == 2) return 3;
    if (n == 4) return 11;

    long one = 3, two = 11, three = 0, add = 2;
    for (int i = 6; i <= n; i += 2) {
      add += (one * 2);
      three = (3 * two + add) % 1000000007;
      one = two;
      two = three;
    }

    return (int) three;
  }
}
