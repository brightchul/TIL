package algospot.FORTRESS;
/*
https://algospot.com/judge/problem/read/FORTRESS
알고스팟 > FORTRESS
예제 입력 (1번째에는 가장 큰 외벽의 값을 넣는다.)
2
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

출력
2
5

2
4
2 2 10
0 0 1
2 2 1
4 4 1
2
1 1 10
3 3 1

1
2
1 1 10
3 3 1

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


1
3
2 2 10
1 1 1
3 3 1

3
5
3 3 100
100 100 1
4 4 10
2 2 30
3 3 50
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
8
10 10 1000
10 10 90
10 10 80
10 10 70
10 10 50
10 10 1
10 20 10
10 20 1


1
8
10 10 1000
10 10 90
10 10 80
10 10 70
10 10 50
10 10 1
10 20 10
10 20 1

1
5
5 5 100
1 1 1
5 5 1
7 7 1
1 1 2

//4
1
6
15 15 100
15 15 99
5 15 3
25 15 3
5 15 2
25 15 2
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        int totalCaseNum = Integer.parseInt(br.readLine());
        while(totalCaseNum-- > 0) {
            int wallCount = Integer.parseInt(br.readLine());
            MyTree mt = new MyTree();
            while(wallCount-- > 0) {
                st = new StringTokenizer(br.readLine(), " ");
                Node node = new Node(new Wall(st.nextToken(), st.nextToken(), st.nextToken()));
                mt.add(node);
            }
            Node deepNode = mt.findDeepNode(mt.getRoot(), 0);
//            System.out.println(deepNode);
//            System.out.println(deepNode.height);
//            System.out.println(mt.toString(mt.getRoot()));
            System.out.println(mt.findMaxLength(deepNode));
        }
    }
}

class MyTree {
    Node NONE = new Node(new Wall("0", "0", "1000000000"));
    public MyTree() {}
    public void add(Node target) {
        if(NONE.list.size() == 0) {
            NONE.add(target);
            return;
        }
        Node parentRoot = NONE;
        Node root = parentRoot.list.size() > 1 ? parentRoot : NONE.list.get(0);
        // root 에 속하는지 파악한다.
        loop : while(true) {
            if(root.contains(target)) {
                // root에 속하면 root안의 다른 list의 개발 항목에도 속하는지 봐야 한다.
                for(int idx = 0; idx<root.list.size();) {
                    Node child = root.list.get(idx);

              //for(Node child : root.list) {// list에서 remove하면 당겨지면서 에러가 발생가능
                    // list중에 1개에 포함 당하게 되면 그 1개에 다시 과정을 반복한다.
                    // 로직상 포함당하는 것은 1개밖에 없다. list안의 노드 중에 2개 이상의 노드에 한번에 포함당할일은 없다.
                    if(child.contains(target)) {
                        parentRoot = root;
                        root = child;
                        continue loop;
                    }
                    // list중에 역으로 1개 이상 포함하게 되면 그것들을 target.list에 담고 해당 list에서 없애고 그 자리를 차지한다.
                    // 로직상 하나에 포함당하고 다른 하나엔 포함하는 그런 경우는 없다.
                    else if(target.contains(child)) {
                        root.remove(child);
                        target.add(child);
                    } else {
                        idx++;
                    }
                }
                root.add(target);
                break loop;
            } else {
                // target에 root가 속하는지 본다.
                // target에 root가 속하게 되면 root를 target.list에 담고, parentRoot.list에서 root를 대신 한다.
                // target에 root가 속하지 않는다면, 서로 떨어진 성벽이다. 상위 부모의 list에 담는다.
                if(target.contains(root)) {
                    parentRoot.remove(root);
                    target.add(root);
                    parentRoot.add(target);
                    break loop;
                } else {
                    parentRoot.add(target);
                    break loop;
                }
            }
        }
    }
    public Node findDeepNode(Node root, int heightCount) {
        root.height = heightCount;
        if(root.list.size() == 0) {
            return root;
        }
        Node resultNode = root;
        for(Node one : root.list) {
            Node tempNode = findDeepNode(one, heightCount+1);
            if(resultNode.height < tempNode.height) resultNode = tempNode;
        }
        return resultNode;
    }
    public int findMaxLength(Node target) {
        // 최상위 노드일때는 그 위가 없으니 컷한다.
        if(target.parentNode == NONE) return 0;
        // 그게 아니라면
        // 부모 노드를 가져와서 findDeepNode를 찾는다.
        Node root = target.parentNode;
        int maxLength = 0;
        for(Node one : root.list) {
            if(one != target) {
                // 자식이 없더라도 해당 parent가 경로 길이가 +1이다
                int tempHeight = findDeepNode(one, 0).height+1;   // 부모와 길이가 1이므로 더해준다.
                maxLength = Math.max(maxLength, tempHeight);
            }
        }

        // 현재위치에서 찾는 결과가 그 상위에서 찾는 결과 중 큰 값을 찾는다.
        int upperReslut = findMaxLength(root);
        maxLength = Math.max(maxLength, upperReslut);
        return maxLength + 1;   // 자신과 부모노드간의 길이를 마지막에 더해준다.
    }
    public String toString(Node node) {
        return node.toString();
    }
    public Node getRoot() {
        return NONE.list.get(0);
    }
}

class Node {
    private Wall value;
    public int height = 0;
    public Node parentNode;
    public ArrayList<Node> list = new ArrayList<>();
    public Node(Wall wall) {
        value = wall;
    }

    public boolean contains(Node node) {
        return value.contains(node.value);
    }
    public boolean add(Node node) {
        node.parentNode = this;
        return list.add(node);
    }
    public boolean remove(Node node) {
        node.parentNode = null;
        return list.remove(node);
    }
    public Node getChild(int idx) {return this.list.get(idx);}
    public Node getParent(){return this.parentNode;}
    public int getLen() {return this.list.size();}
    public String toString() {
        return String.format("%s %s", value.toString(), list.toString());
    }
}

class Wall {
    private int x, y, r;
    private int xMin, xMax, yMin, yMax;
    public Wall(String x1, String y1, String r1) {
        x = Integer.parseInt(x1);
        y = Integer.parseInt(y1);
        r = Integer.parseInt(r1);
        xMin = x-r;
        xMax = x+r;
        yMin = y-r;
        yMax = y+r;
    }
    public boolean contains(Wall target) {
        return xMin < target.xMin && xMax > target.xMax
            && yMin < target.yMin && yMax > target.yMax;
    }
    public String toString() {
        return String.format("x:%d, y:%d, r:%d", x, y, r);
    }
}
