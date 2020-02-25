package programmers.diskController;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinHeapTest {

    MinHeap heap;
    int[] ar0_3 = {0,3};
    int[] ar1_2 = {1,2};
    int[] ar2_1 = {2,1};
    int[] ar4_10 = {4,10};
    int[] ar3_30 = {3,30};

    @Before
    public void init() {
        heap = new MinHeap();
    }

    @Test
    public void popMin() {
        heap.add(ar0_3);
        heap.add(ar1_2);
        heap.add(ar2_1);
        assertEquals(
                heap.popMin(), ar2_1);

        heap.add(ar4_10);
        heap.add(ar3_30);
        assertEquals(heap.popMin(), ar1_2);
    }

    @Test
    public void length() {
        heap.add(ar0_3);
        heap.add(ar1_2);
        heap.add(ar2_1);
        heap.popMin();
        assertEquals(heap.length(), 2);

        heap.add(ar4_10);
        heap.add(ar3_30);
        heap.popMin();
        assertEquals(heap.length(), 3);
    }
    @Test
    public void test1() {
        heap.add(ar0_3);
        heap.add(ar1_2);
        heap.add(ar3_30);
        heap.add(ar4_10);
        heap.add(ar2_1);
        while(heap.length() > 0){
            int[] one = heap.popMin();
            System.out.println(one[0] + "," + one[1]);
        }
    }
}