package algospot.NERD2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
3
4
72 50
57 67
74 55
64 60
5
1 5
2 4
3 3
4 2
5 1
4
1 2
2 3
3 4
4 5


각각 답이 8, 15, 4이다.
 */
public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int TEST_COUNT = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i=0; i<TEST_COUNT; i++) {
            int result = 0;
            int INPUT_COUNT = Integer.parseInt(br.readLine());
            MyLinkedList list = new MyLinkedList();
            for(int j=0; j<INPUT_COUNT; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                int[] one = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
                result += run(list, one);
            }
            System.out.println(result);
        }
    }
    public static int run(MyLinkedList list, int[] one) {
        // 일단 단순하게 전부 순회해서 체크하는 방식으로 해본다.
        int size = list.size();
        MyNode node = list.getFirstNode();
        for(int i=0; i<size; i++) {
            // 두 노드를 체크한다.
            if((one[0] - node.item[0]) * (one[1] - node.item[1]) > 0) {
                if(one[0] < node.item[0]) {
                    return list.size();
                } else {
                    node = list.remove(node);
                }
            }
            node = node.next();
        }
        list.add(one);
        return list.size();
    }
}

class MyLinkedList {
    private MyNode NONE = new MyNode(null);
    private int size = 0;
    MyLinkedList() {
        NONE.prev = NONE.next = NONE;
    }
    public void add(int[] arr) {
        MyNode lastNode = getLastNode();
        MyNode prevNode = lastNode.prev;
        MyNode newNode = new MyNode(arr);
        newNode.next = lastNode;
        lastNode.prev = newNode;

        prevNode.next = newNode;
        newNode.prev = prevNode;
        size++;
    }
    public MyNode getFirstNode() {
        return NONE.next;
    }
    public MyNode getLastNode() {
        return NONE.prev;
    }
    public MyNode remove(MyNode deletedNode) {
        if(size == 0) return NONE;

        MyNode prevNode = deletedNode.prev;
        MyNode nextNode = deletedNode.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        deletedNode.reset();

        size--;
        return prevNode;
    }
    public int size() {
        return size;
    }
    public boolean isNodeDone(MyNode node) {
        return node == NONE;
    }

}

class MyNode {
    public int[] item;
    protected MyNode next;
    protected MyNode prev;
    public MyNode(int[] item) {
        this.item = item;
    }
    public void reset() {
        prev = next = null;
        item = null;
    }
    public MyNode next() {
        return next;
    }
}
