package programmers.leastCommonMultipleOfNValue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {
  Solution s = new Solution();

  @Test
  public void solution() {
    int[] a = {2, 6, 8, 14};
    assertEquals(s.solution(a), 168);
  }

  @Test
  public void solution2() {
    int[] a = {1, 2, 3};
    assertEquals(s.solution(a), 6);
  }
}
