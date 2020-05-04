package ch22;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Node root = null;
        for(int i=1; i<=7; i++)
            root = Node.insert(root, new Node(i));

        System.out.println(Node.kth(root, 3));
        System.out.println(Node.kth(root, 1));
        System.out.println(Node.kth(root, 7));
        System.out.println(Node.countLessThan(root, 4));
        System.out.println(Node.countLessThan(root, 6));

        Node.erase(root, 5);
        Node.erase(root, 2);
        System.out.println(Node.kth(root, 3));
        System.out.println(Node.kth(root, 1));
        System.out.println(Node.kth(root, 7));
        System.out.println(Node.countLessThan(root, 4));
        System.out.println(Node.countLessThan(root, 6));

    }
}
class Node {
    static final Random r = new Random(1);
    int key, priority, size;
    Node left, right;
    Node(int key) {
        this.key = key;
        priority = r.nextInt();
        size =1;
        left = right = null;
    }
    void setLeft(Node newLeft) {
        left = newLeft;
        calcSize();
    }
    void setRight(Node newRight) {
        right = newRight;
        calcSize();
    }
    void calcSize() {
        size = 1;
        if(left != null) size += left.size;
        if(right != null) size += right.size;
    }
    void reset() {
        left = right = null;
    }
    static NodePair split(Node root, int key) {
        if(root == null) return new NodePair(null, null);
        if(root.key < key) {
            NodePair rs = split(root.right, key);
            root.setRight(rs.first());
            return new NodePair(root, rs.second());
        }
        NodePair ls = split(root.left, key);
        root.setLeft(ls.second());
        return new NodePair(ls.first(), root);
    }
    static Node insert(Node root, Node node) {
        if(root == null) return node;

        if(root.priority < node.priority) {
            NodePair splitted = split(root, node.key);
            node.setLeft(splitted.first());
            node.setRight(splitted.second());
            return node;

        } else if(node.key < root.key) {
            root.setLeft(insert(root.left, node));
        } else {
            root.setRight(insert(root.right, node));
        }
        return root;
    }
    static Node merge(Node a, Node b) {
        if(a == null) return b;
        if(b == null) return a;
        if(a.priority < b.priority) {
            b.setLeft(merge(a, b.left));
            return b;
        }
        a.setRight(merge(a.right, b));
        return a;
    }
    static Node erase(Node root, int key) {
        if(root == null) return root;
        if(root.key == key){
            Node ret = merge(root.left, root.right);
            root.reset();   // c++에서는 delete이지만, 자바는 없으니 left, right 참조를 null로 바꾼다.
            return ret;
        }
        if(key < root.key)
            root.setLeft(erase(root.left, key));
        else
            root.setRight(erase(root.right, key));

        return root;
    }
    static Node kth(Node root, int k) {
        if(root == null) return null;
        int leftSize = 0;
        if(root.left != null) leftSize = root.left.size;
        if(k<=leftSize) return kth(root.left, k);
        if(k==leftSize + 1) return root;

        return kth(root.right, k-leftSize-1);
    }
    static int countLessThan(Node root, int key) {
        if(root == null) return 0;
        if(root.key >= key) return countLessThan(root.left, key);

        int ls = root.left != null ? root.left.size : 0;
        return ls + 1 + countLessThan(root.right, key);
    }
    public String toString() {
        return "key : "+ key
                + ", size : " + size
                + ", priority : "+ priority;
    }

}

class NodePair extends AbstractMap.SimpleEntry<Node, Node> {
    NodePair(Node key, Node value) { super(key, value); }
    Node first() {return getKey();}
    Node second() {return getValue();}
}