package baekjoon.p9465;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void solution() {
        {
            int[][] arr = {
                {50, 10, 100, 20, 40},
                {30, 50, 70, 10, 60}
            };
            assertEquals(260, Main.solution(arr));
        }
        {
            int[][] arr = {
                    {10, 30, 10, 50, 100, 20, 40},
                    {20, 40, 30, 50, 60, 20, 80}
            };
            assertEquals(290, Main.solution(arr));
        }
    }
}