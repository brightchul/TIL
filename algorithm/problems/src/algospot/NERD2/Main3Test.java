package algospot.NERD2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Main3Test {
    Main3 m;
    @Before
    public void init() {
        m = new Main3();
        m.coords.put(100, 700);
        m.coords.put(300, 600);
        m.coords.put(500, 500);
        m.coords.put(700, 400);
        m.coords.put(-100, -100);
        m.coords.put(-300, -300);
    }


    @Test
    public void isContained() {
        // map이기 때문에 동일 x좌표에 서로다른 y이 오지 않는다.
        assertEquals(m.isContained(200,10), true);
        assertEquals(m.isContained(200,1000), false);
        assertEquals(m.isContained(200,600), false);

        assertEquals(m.isContained(600,10), true);
        assertEquals(m.isContained(600,1000), false);
        assertEquals(m.isContained(600,400), false);

        assertEquals(m.isContained(6,10), true);
        assertEquals(m.isContained(6,1000), false);
        assertEquals(m.isContained(6,700), false);
    }

    // 삭제 좌표가 맨 왼쪽이고 하나도 삭제가 안됨
    @Test
    public void removeContained_아무것도삭제안하는경우() {
        // 자기보다 x좌표가 작은 애들을 삭제 하므로
        // x값이 최소값이면 y값은 의미가 없음.
        int size = m.size();
        m.removeContained(-400, 0);
        assertEquals(size, m.size());
    }

    // 삭제 좌표가 맨 오른쪽이고 전부다 삭제
    @Test
    public void removeContained_모두삭제경우() {
        // 전부 삭제
        m.removeContained(10000, 10000);
        assertEquals(m.size(), 0);
    }
    @Test
    public void removeContained_기본() {
        // 중간에 특정 값이 있고 몇개만 삭제된다.
        m.removeContained(0, 10000);
        assertEquals(m.size(), 4);
    }
}