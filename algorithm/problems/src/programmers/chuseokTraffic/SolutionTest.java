package programmers.chuseokTraffic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {
  Solution s = new Solution();

  @Test
  public void solution1() {
    String[] arr = {"2016-09-15 00:00:00.000 3s"};
    assertEquals(1, s.solution(arr));
  }

  @Test
  public void solution2() {
    String[] arr = {"2016-09-15 23:59:59.999 0.001s"};
    assertEquals(1, s.solution(arr));
  }

  @Test
  public void solution3() {
    String[] arr = {"2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"};
    assertEquals(1, s.solution(arr));
  }

  @Test
  public void solution4() {
    String[] arr = {"2016-09-15 01:00:04.002 2.0s", "2016-09-15 01:00:07.000 2s"};
    assertEquals(2, s.solution(arr));
  }

  @Test
  public void solution5() {
    String[] arr = {
      "2016-09-15 20:59:57.421 0.351s",
      "2016-09-15 20:59:58.233 1.181s",
      "2016-09-15 20:59:58.299 0.8s",
      "2016-09-15 20:59:58.688 1.041s",
      "2016-09-15 20:59:59.591 1.412s",
      "2016-09-15 21:00:00.464 1.466s",
      "2016-09-15 21:00:00.741 1.581s",
      "2016-09-15 21:00:00.748 2.31s",
      "2016-09-15 21:00:00.966 0.381s",
      "2016-09-15 21:00:02.066 2.62s"
    };
    assertEquals(7, s.solution(arr));
  }

  @Test
  public void solution6() {
    String[] arr = {"2016-09-15 00:00:00.000 2.3s", "2016-09-15 23:59:59.999 0.1s"};
    assertEquals(1, s.solution(arr));
  }
}
