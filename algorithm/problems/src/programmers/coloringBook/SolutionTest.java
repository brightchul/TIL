package programmers.coloringBook;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SolutionTest {
  Solution s = new Solution();

//  @Test
//  public void solution() {
//    int m = 6, n = 4;
//    int[][] picture = {
//      {1, 1, 1, 0}, {1, 2, 2, 0}, {1, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 3}, {0, 0, 0, 3}
//    };
//    assertArrayEquals(s.solution(m, n, picture), new int[] {4, 5});
//  }

  @Test
  public void solution2() {
    int m = 6, n = 4;
    int[][] picture = {
      {1, 1, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}
    };
    assertArrayEquals(s.solution(m, n, picture), new int[] {2, 6});
  }
}
