package algospot.INSERTION;

// TODO: 할것.
/*
2
5
0 1 1 2 3
4
0 1 2 3

1
5
0 1 1 0 3
 */
/*
1차 시도, 연결 리스트를 이용해봄. 시간 초과..
 */
import java.io.*;
import java.util.StringTokenizer;
public class Main {
    final public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int totalCase = Integer.parseInt(br.readLine());
        while(totalCase-- > 0) {
            int len = Integer.parseInt(br.readLine());
            int[] arr = getIntArr(len);
            run(arr, len);
        }
        bw.flush();
        bw.close();
    }
    public static int[] getIntArr(int len) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int[] ret = new int[len];
        for(int i=0; i<len; i++) ret[i] = Integer.parseInt(st.nextToken());
        return ret;
    }
    public static void run(int[] arr, int len) throws IOException{
        LList llist = new LList();
        for(int i=0; i<len; i++) llist.add(i+1);

        MyNode oneNode = llist.getLastNode();
        for(int i = len-1; i > 0; i--) {
            if(arr[i] == 0) {
                oneNode = oneNode.prevNode;
            } else {
                MyNode removedNode = llist.remove(i-arr[i]);
                llist.insertNextNode(oneNode, removedNode);
            }
        }
        MyNode firstNode = llist.getFirstNode();
        for(int i=0; i<len; i++) {
            bw.write(firstNode.getValue() + "");
            bw.write(" ");
            firstNode = firstNode.nextNode;
        }
        bw.write("\n");
    }
}

class LList {
    final MyNode NONE = new MyNode(-1);
    LList() {
        NONE.prevNode = NONE.nextNode = NONE;
    }
    public void add(int i) {
        MyNode newNode = new MyNode(i);
        MyNode prevNode = NONE.prevNode;

        prevNode.nextNode = newNode;
        newNode.prevNode = prevNode;

        NONE.prevNode = newNode;
        newNode.nextNode = NONE;
    }
    public MyNode getLastNode() {
        return NONE.prevNode;
    }
    public MyNode getFirstNode() {
        return NONE.nextNode;
    }
    public MyNode remove(int idx) {
        int i = 0;
        MyNode node = NONE.nextNode;
        while(i != idx) {
            node = node.nextNode;
            i++;
        }
        MyNode prevNode = node.prevNode;
        MyNode nextNode = node.nextNode;
        prevNode.nextNode = nextNode;
        nextNode.prevNode = prevNode;
        node.resetRef();
        return node;
    }
    public void insertNextNode(MyNode prevNode, MyNode newNode) {
        MyNode nextNode = prevNode.nextNode;

        newNode.prevNode = prevNode;
        prevNode.nextNode = newNode;

        newNode.nextNode = nextNode;
        nextNode.prevNode = newNode;
    }

}
class MyNode {
    final int value;
    public MyNode prevNode;
    public MyNode nextNode;

    MyNode(int value) {
        this.value = value;
    }
    public void resetRef() {
        prevNode = null;
        nextNode = null;
    }
    public int getValue() {
        return value;
    }
}