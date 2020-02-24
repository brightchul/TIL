package programmers.moreSpicy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinHeapTest {
    MinHeap heap;

    @Before
    public void init() {
        heap = new MinHeap(3);
    }

    @Test
    public void add() {
        heap.add(4);
        assertEquals(heap.peekMin(), 4);
        heap.add(7);
        assertEquals(heap.peekMin(), 4);
        heap.add(1);
        assertEquals(heap.peekMin(), 1);

        heap.popMin();
        heap.popMin();

        heap.add(2);
        assertEquals(heap.peekMin(), 2);
        heap.add(1);
        assertEquals(heap.peekMin(), 1);
    }

    @Test
    public void popMin() {
        heap.add(3);
        heap.add(5);
        heap.add(1);

        assertEquals(heap.popMin(), 1);
        assertEquals(heap.popMin(), 3);

        heap.add(2);
        heap.add(1);
        assertEquals(heap.popMin(), 1);
        assertEquals(heap.popMin(), 2);
    }

    @Test
    public void length() {
        assertEquals(heap.length(), 0);

        heap.add(1);
        assertEquals(heap.length(), 1);
        heap.add(2);
        assertEquals(heap.length(), 2);
        heap.add(3);
        assertEquals(heap.length(), 3);
        heap.add(3);
        assertEquals(heap.length(), 3);
        heap.add(3);
        assertEquals(heap.length(), 3);


        heap.popMin();
        assertEquals(heap.length(), 2);

        heap.add(3);
        heap.add(3);
        assertEquals(heap.length(), 3);


        heap.popMin();
        heap.popMin();
        assertEquals(heap.length(), 1);
        heap.popMin();
        assertEquals(heap.length(), 0);
        heap.popMin();
        assertEquals(heap.length(), 0);
        heap.popMin();
        assertEquals(heap.length(), 0);
        heap.popMin();
        assertEquals(heap.length(), 0);
    }
    @Test
    public void test123() {
        MinHeap heap = new MinHeap(8);
        heap.add(1);
        heap.add(10);
        heap.add(15);
        heap.add(3);
        heap.add(5);
        heap.add(7);
        heap.add(2);
        heap.add(4);

        while(heap.length() > 0)
            System.out.println(
                    heap.popMin());
    }
}