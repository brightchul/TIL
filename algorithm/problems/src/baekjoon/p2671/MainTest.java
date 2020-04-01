package baekjoon.p2671;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    Main m = new Main();
    @Test
    public void run() {
        assertEquals(m.run("10010111"), false);
        assertEquals(m.run("100000000001101"), true);
        assertEquals(m.run("10010110000001111101"), true);
        assertEquals(m.run("01010101011000111"), true);
        assertEquals(m.run("01010101011000110"), false);
        assertEquals(m.run("010101010011000111"), false);
        assertEquals(m.run("1010"), false);
        assertEquals(m.run("01100010011001"), false);
        assertEquals(m.run("1000"), false);
        assertEquals(m.run("10011001"), true);
    }
}