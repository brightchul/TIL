package programmers.delivery;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    private final PriorityQueue<Node> pq = new PriorityQueue<>();
    private int[] costs;
    private int[][] arr;

    public int solution(int N, int[][] road, int K) {
        init(N, road);
        run(1, 0);

        int answer = 0;
        for (int cost : costs) {
            if (cost <= K) answer++;
        }
        return answer;
    }

    public void init(int N, int[][] road) {
        pq.clear();

        arr = new int[N + 1][N + 1];
        for (int[] one : road) {
            if (arr[one[0]][one[1]] != 0 && arr[one[0]][one[1]] <= one[2]) continue;
            arr[one[0]][one[1]] = arr[one[1]][one[0]] = one[2];
        }

        costs = new int[N + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[1] = 0;
    }

    public void run(int idx, int minCost) {
        int[] oneArr = arr[idx];

        for (int i = 1; i < oneArr.length; i++) {
            if (oneArr[i] == 0) continue;
            if (costs[i] <= minCost + oneArr[i]) continue;
            costs[i] = minCost + oneArr[i];
            pq.add(new Node(i, costs[i]));
        }

        if (pq.size() > 0) {
            Node one = pq.poll();
            run(one.idx, one.cost);
        }
    }
}

class Node implements Comparable<Node> {
    public int idx;
    public int cost;

    public Node(int idx, int cost) {
        this.idx = idx;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return this.cost - o.cost;
    }
}

