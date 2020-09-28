package programmers.walkerHeaven;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

  Solution s = new Solution();

  @Test
  public void solution() {
    int[][] t1 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    assertEquals(6, s.solution(3, 3, t1));

    int[][] t2 = {{0, 2, 0, 0, 0, 2}, {0, 0, 2, 0, 1, 0}, {1, 0, 0, 2, 2, 0}};
    assertEquals(2, s.solution(3, 6, t2));
  }
}
