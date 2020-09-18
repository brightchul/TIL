package programmers.rectangleArea;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {
  Solution s = new Solution();
  int[][] v = {{0, 1, 4, 4}, {3, 1, 5, 3}};
  int[][] v2 = {{1, 1, 6, 5}, {2, 0, 4, 2}, {2, 4, 5, 7}, {4, 3, 8, 6}, {7, 5, 9, 7}};

  @Test
  public void solution() {
    assertEquals(14, s.solution(v));
    assertEquals(38, s.solution(v2));
  }
}
