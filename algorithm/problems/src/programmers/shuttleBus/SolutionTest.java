package programmers.shuttleBus;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        {
            String[] arr = {"08:00", "08:01", "08:02", "08:03"};
            assertEquals(s.solution(1,1,5, arr), "09:00");
        }
        {
            String[] arr = {"09:10", "09:09", "08:00"};
            assertEquals(s.solution(2, 10, 2, arr), "09:09");
        }
        {
            String[] arr = {"00:01", "00:01", "00:01", "00:01", "00:01"};
            assertEquals(s.solution(1,1,5, arr), "00:00");
        }
        {
            String[] arr = {"23:59"};
            assertEquals(s.solution(1,1,1, arr), "09:00");
        }
        {
            String[] arr = {"23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"};
            assertEquals(s.solution(10,60,45, arr), "18:00");
        }

    }

    @Test
    public void convertMin() {
        assertEquals(s.convertMin("09:00"), 9 * 60);
        assertEquals(s.convertMin("10:30"), 10 * 60 + 30);
        assertEquals(s.convertMin("00:57"), 57);
        assertEquals(s.convertMin("21:35"), 21 * 60 + 35);
    }
}