package programmers.wayFindingGames;

import java.util.Arrays;

/*
코딩테스트 연습 > 2019 KAKAO BLIND RECRUITMENT > 길 찾기 게임
https://programmers.co.kr/learn/courses/30/lessons/17678
 */
public class Solution {
    public static void main(String [] args) {
        int[][] one1 = {{5,3},{11,5},{13,3},{3,5},{6,1},{1,3},{8,6},{7,2},{2,2}};
        Solution s = new Solution();
        s.solution(one1);
    }
    public int[][] solution(int[][] nodeinfo) {
        Node[] nodeArr = new Node[nodeinfo.length];

        for(int i=0; i<nodeinfo.length;)
            nodeArr[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], ++i);

        Arrays.sort(nodeArr);
        BinaryTree bt = new BinaryTree();

        for(Node one : nodeArr)
            bt.add(one);

        Stack preStack = new Stack(nodeinfo.length);
        Stack postStack = new Stack(nodeinfo.length);

        bt.preOrder(bt.NONE.right, preStack);
        bt.postOrder(bt.NONE.right, postStack);
        return new int[][]{preStack.toArray(), postStack.toArray()};
    }
}

class Stack {
    int[] arr;
    int idx = 0;
    Stack(int size) {
        arr = new int[size];
    }
    void push(int v) {
        arr[idx++] = v;
    }
    int[] toArray() {
        return arr;
    }
}

class BinaryTree {
    Node NONE = new Node(-1, -1, Integer.MAX_VALUE);
    public void add(Node node) {
        if(NONE.left == null) {
            NONE.left = NONE.right = node;
            return;
        }
        Node curRoot = NONE.left;
        while(true) {
            if(node.x < curRoot.x) {
                if(curRoot.left != null) curRoot = curRoot.left;
                else {
                    curRoot.left = node;
                    break;
                }
            } else {
                if(curRoot.right != null) curRoot = curRoot.right;
                else {
                    curRoot.right = node;
                    break;
                }
            }
        }
    }
    public void preOrder(Node node, Stack stack) {
        if(node == null) return;
        stack.push(node.value);
        preOrder(node.left, stack);
        preOrder(node.right, stack);
    }
    public void postOrder(Node node, Stack stack) {
        if(node == null) return;
        postOrder(node.left, stack);
        postOrder(node.right, stack);
        stack.push(node.value);
    }
}

class Node implements Comparable<Node>{
    Node left, right;
    int x, y, value;
    Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        if(y == node.y) return x-node.x;
        return node.y-y;
    }
}