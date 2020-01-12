package algospot.FORTRESS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
8
3
2 2 10
1 1 1
3 3 1
3
5 5 15
5 5 10
5 5 5
8
21 15 20
15 15 10
13 12 5
12 12 3
19 19 2
30 24 5
32 10 7
32 9 4
2
1 1 10
3 3 1
1
1 1 10
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
5
5 5 100
1 1 1
5 5 1
7 7 1
1 1 2
6
15 15 100
15 15 99
5 15 3
25 15 3
5 15 2
25 15 2

결과
2
2
5
1
0
8
3
4
 */
public class Main2 {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException{
        int totalCaseNum = Integer.parseInt(br.readLine());
        while(totalCaseNum-- > 0) run();
    }
    public static void run() throws IOException {
        int wallLen = Integer.parseInt(br.readLine());
        MTree mt = new MTree();

        while(wallLen-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            Node node = new Node(new Wall(st.nextToken(), st.nextToken(), st.nextToken()));
            mt.add(node);
        }
        Node ret = mt.findDeepestNode(mt.getRoot(), 0);
        System.out.println(mt.getLogestPath(ret));
    }
}

// 맨처음에 가장 큰 외벽을 받는 다는 가정을 반영한 자료구조이다.
class MTree {
    private final Node NONE = new Node(null);
    public MTree() {

    }
    public void add(Node newNode) {
        if(NONE.getLen() == 0) NONE.add(newNode);
        else {
            _add(this.getRoot(), newNode);
        }
    }
    private boolean _add(Node rootNode, Node newNode) {
        if(!rootNode.contains(newNode)) return false;
        if(rootNode.getLen() == 0) return rootNode.add(newNode);

        for(int i=0; i<rootNode.getLen();) {
            Node childNode = rootNode.getChild(i);
            if(_add(childNode, newNode)) return true;
            if(newNode.contains(childNode)) {
                rootNode.remove(childNode);
                newNode.add(childNode);
            } else {
                i++;
            }
        }
        return rootNode.add(newNode);
    }
    public Node getRoot() {
        if(NONE.getLen() == 0) return NONE;
        return NONE.getChild(0);
    }
    public Node findDeepestNode(Node rootNode, int height) {
        Node resultNode = rootNode;
        resultNode.height = height;

        for(int i=0, len = rootNode.getLen(); i<len; i++) {
            Node tempNode = findDeepestNode(rootNode.getChild(i), height+1);
            if(resultNode.height < tempNode.height) resultNode = tempNode;
        }
        return resultNode;
    }
    public int getLogestPath(Node startNode) {
        if(startNode == getRoot()) return 0;
        int resultPath = startNode.height;

        Node parentNode = startNode.getParent();
        int len = parentNode.getLen();

        for(int i=0; i<len; i++) {
            Node childNode = parentNode.getChild(i);
            if(childNode == startNode) continue;

            Node resultNode = findDeepestNode(childNode, 1);  // 부모->자식이므로 1을 더해준다.
            resultPath = Math.max(resultPath, resultNode.height+1); // startNode -> parent노드이므로 +1
        }
        return Math.max(resultPath, getLogestPath(parentNode)+1);
    }
}
