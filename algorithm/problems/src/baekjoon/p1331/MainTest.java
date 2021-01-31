package baekjoon.p1331;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

  /**
   *  1. L자형으로 이동하지 않은 경우
   *  2. 중복인 경우
   *  3. 처음과 끝이 만나지 못하는 경우
   */
  @Test
  public void solution() {
    String[] tArr1 = {
      "A1", "B3", "A5", "C6", "E5", "F3", "D2", "F1", "E3", "F5", "D4", "B5", "A3", "B1", "C3",
      "A2", "C1", "E2", "F4", "E6", "C5", "A6", "B4", "D5", "F6", "E4", "D6", "C4", "B6", "A4",
      "B2", "D1", "F2", "D3", "E1", "C2"
    };
    assertEquals("맞는 경우 : ", Main.solution(tArr1), Main.VALID);

    String[] tArr2 = {
      "A1", "B3", "A5", "C6", "F5", "F3", "D2", "F1", "E3", "F5", "D4", "B5", "A3", "B1", "C3",
      "A2", "C1", "E2", "F4", "E6", "C5", "A6", "B4", "D5", "F6", "E4", "D6", "C4", "B6", "A4",
      "B2", "D1", "F2", "D3", "E1", "C2"
    };
    assertEquals("도중에 경로가 맞지 않는 경우 : ", Main.solution(tArr2), Main.INVALID);

    String[] tArr3 = {
      "A1", "B3", "A5", "C6", "E5", "F3", "D2", "F1", "E3", "F5", "D4", "B5", "A3", "B1", "C3",
      "A2", "C1", "E2", "F4", "E6", "C5", "A6", "B4", "D5", "F6", "E4", "D6", "C4", "B6", "A4",
      "B2", "D1", "F2", "D3", "E1", "A1"
    };
    assertEquals("A1이 중복으로 들어감 : ", Main.solution(tArr3), Main.INVALID);
  }

  @Test
  public void checkFalse() {
    assertFalse(Main.check("A1", "A1"));
    assertFalse(Main.check("A1", "A3"));
    assertFalse(Main.check("A3", "A1"));
    assertFalse(Main.check("A1", "C1"));
    assertFalse(Main.check("C1", "A1"));
    assertFalse(Main.check("A1", "A3"));
    assertFalse(Main.check("A1", "E6"));
    assertFalse(Main.check("E6", "A1"));
  }

  public void checkTrue() {
    assertTrue(Main.check("A1", "B3"));
    assertTrue(Main.check("B3", "A1"));
    assertTrue(Main.check("D1", "F2"));
    assertTrue(Main.check("F2", "D1"));
    assertTrue(Main.check("A1", "C2"));
    assertTrue(Main.check("C2", "A1"));
  }
}
