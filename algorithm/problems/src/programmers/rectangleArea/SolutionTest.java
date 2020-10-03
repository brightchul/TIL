package programmers.rectangleArea;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {
  Solution s = new Solution();
  Solution2 s2 = new Solution2();
  int[][] v = {{0, 1, 4, 4}, {3, 1, 5, 3}};
  int[][] v2 = {{1, 1, 6, 5}, {2, 0, 4, 2}, {2, 4, 5, 7}, {4, 3, 8, 6}, {7, 5, 9, 7}};

  @Test
  public void solution() {
    assertEquals(14, s.solution(v));
    assertEquals(38, s.solution(v2));
  }
  @Test
  public void solution2() {
    assertEquals(14, s2.solution(v));
    assertEquals(38, s2.solution(v2));
  }
}
