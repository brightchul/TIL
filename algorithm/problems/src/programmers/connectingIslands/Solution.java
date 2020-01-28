package programmers.connectingIslands;

/*
코딩테스트 연습>  탐욕법(Greedy) > 섬 연결하기
https://programmers.co.kr/learn/courses/30/lessons/42861
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public int solution(int n, int[][] costs) {
        Graph graph = new Graph(n);
        Arrays.sort(costs, (a,b) -> a[2] - b[2]);
        for(int[] one : costs) {
            if(!graph.isConnect(one[0], one[1], new int[n])) {
                graph.addBridge(one[0], one[1], one[2]);
            }
        }
        return graph.getTotalCost();
    }
}


class Graph {
    ArrayList<ArrayList<Node>> data;
    public Graph(int n) {
        data = new ArrayList<>(n);
        for(int i=0; i<n; i++)
            data.add(new ArrayList<Node>());
    }
    // 단순 체크가 아니라 재귀호출로 훑어야 한다.
    public boolean isConnect(int start, int destination, int[] isPassed) {
        isPassed[start] = 1;
        ArrayList<Node> location = data.get(start);
        for(Node one : location) {
            if(one.isDest(destination)) return true;
            if(isPassed[one.dest] == 1) continue;
            if(isConnect(one.dest, destination, isPassed)) return true;
        }
        return false;
    }
    public void addBridge(int a, int b, int cost) {
        data.get(a).add(new Node(b, cost));
        data.get(b).add(new Node(a, cost));
    }
    public int getTotalCost() {
        int totalCost = 0;
        for(ArrayList<Node> list : data) {
            for(Node one : list) totalCost += one.cost;
        }
        return totalCost/2;
    }
}

class Node {
    int dest, cost;
    public Node(int dest, int cost) {
        this.dest = dest;
        this.cost = cost;
    }
    public boolean isDest(int n) {
        return this.dest == n;
    }
}