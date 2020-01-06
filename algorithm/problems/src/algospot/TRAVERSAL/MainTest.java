package algospot.TRAVERSAL;

import org.junit.Test;


import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void run() {
        String r = "27 16 9 12 54 36 72";
        String l = "9 12 16 27 36 54 72";
        String ret = "12 9 16 36 72 54 27";
        assertEquals(Main.run(r, l), ret);

        r = "409 479 10 838 150 441";
        l = "409 10 479 150 838 441";
        ret = "10 150 441 838 479 409";
        assertEquals(Main.run(r, l), ret);
    }
}