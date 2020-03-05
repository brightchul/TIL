package algospot.RUNNINGMEDIAN;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void run() {
        Main m = new Main();
        assertEquals(m.run(10, 1, 0), 19830);
        assertEquals(m.run(10, 1, 1), 19850);
        assertEquals(m.run(10000, 1273, 4936), 2448920);
    }
}