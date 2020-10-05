package programmers.rank;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

  int[][] input = {{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}};
  int[][] input2 = {{1, 2}, {2, 3}, {3, 4}, {5, 6}, {6, 7}, {7, 8}};

  @Test
  public void solution() {
    Solution s = new Solution();

    assertEquals(2, s.solution(5, input));
    assertEquals(0, s.solution(8, input2));
  }

  @Test
  public void solution2() {
    Solution2 s2 = new Solution2();

    assertEquals(2, s2.solution(5, input));
    assertEquals(0, s2.solution(8, input2));
  }
}
