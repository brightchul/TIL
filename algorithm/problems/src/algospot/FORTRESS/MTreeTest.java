package algospot.FORTRESS;

import org.junit.Before;
import org.junit.Test;

import java.util.StringTokenizer;

import static org.junit.Assert.*;

public class MTreeTest {
    MTree mt;
    public void addCaseNode(String input) {
        String[] arr = input.split("\n");
        int len = Integer.parseInt(arr[0]);
        StringTokenizer st;
        for(int i=1; i<=len; i++) {
            st = new StringTokenizer(arr[i], " ");
            mt.add(new Node(new Wall(st.nextToken(), st.nextToken(), st.nextToken())));
        }
    }
    public int runTest(String input) {
        addCaseNode(input);
        int ret = mt.getLogestPath(mt.findDeepestNode(mt.getRoot(), 0));
        return ret;
    }
    @Before
    public void init() {
        mt = new MTree();
    }

    /*
    1
    3
    2 2 10
    1 1 1
    3 3 1
     */
    @Test
    public void case1() {
        String c = "3\n" + "2 2 10\n" + "1 1 1\n" + "3 3 1";
        assertEquals(runTest(c), 2);
    }
    /*
    1
    3
    5 5 15
    5 5 10
    5 5 5
     */
    @Test
    public void case2() {
        String c = "3\n" + "5 5 15\n" + "5 5 10\n" + "5 5 5";
        assertEquals(runTest(c), 2);
    }
    /*
    1
    8
    21 15 20
    15 15 10
    13 12 5
    12 12 3
    19 19 2
    30 24 5
    32 10 7
    32 9 4
     */
    @Test
    public void case3() {
        String c = "8\n" + "21 15 20\n" + "15 15 10\n" + "13 12 5\n" + "12 12 3\n" + "19 19 2\n" + "30 24 5\n"
                + "32 10 7\n" + "32 9 4\n";
        assertEquals(runTest(c), 5);
    }
    /*
    1
    2
    1 1 10
    3 3 1
     */
    @Test
    public void case4() {
        String c = "2\n" + "1 1 10\n" + "3 3 1";
        assertEquals(runTest(c), 1);
    }
    /*
    1
    1
    1 1 10
     */
    @Test
    public void case5() {
        String c = "1\n" + "1 1 10";
        assertEquals(runTest(c), 0);
    }
    /*
    9
    1 1 500
    3 3 100
    3 3 50
    2 2 30
    4 4 10
    203 203 100
    202 202 30
    204 204 10
    203 203 50
     */
    @Test
    public void case6() {
        String c = "9\n" + "1 1 500\n" + "3 3 100\n" + "3 3 50\n" + "2 2 30\n" + "4 4 10\n" + "203 203 100\n"
                + "202 202 30\n" + "204 204 10\n" + "203 203 50";
        assertEquals(runTest(c), 8);
    }
    /*
    1
    5
    5 5 100
    1 1 1
    5 5 1
    7 7 1
    1 1 2
     */
    @Test
    public void case7() {
        String c = "5\n" + "5 5 100\n" + "1 1 1\n" + "5 5 1\n" + "7 7 1\n" + "1 1 2";
        assertEquals(runTest(c), 3);
    }
    /*
    1
    6
    15 15 100
    15 15 99
    5 15 3
    25 15 3
    5 15 2
    25 15 2
     */
    @Test
    public void case8() {
        String c = "6\n" + "15 15 100\n" + "15 15 99\n" + "5 15 3\n" + "25 15 3\n" + "5 15 2\n" + "25 15 2";
        assertEquals(runTest(c), 4);
    }

}