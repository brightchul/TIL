package baekjoon.p1463;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void solution() {
        assertEquals(0, Main.solution(1));   // 0
        assertEquals(1, Main.solution(2));   // 1
        assertEquals(1, Main.solution(3));   // 1
        assertEquals(2, Main.solution(4));   // 2
        assertEquals(3, Main.solution(5));   // 3
        assertEquals(2, Main.solution(6));   // 2
        assertEquals(3, Main.solution(7));   // 3
        assertEquals(3, Main.solution(8));   // 3
        assertEquals(2, Main.solution(9));   // 2
        assertEquals(3, Main.solution(10));  // 3
        assertEquals(9, Main.solution(321));  // 9
        assertEquals(10, Main.solution(609));  // 10
        assertEquals(10, Main.solution(642));  // 10
        assertEquals(11, Main.solution(643));  // 11
        assertEquals(11, Main.solution(1185));  // 11

    }
}