package programmers.delivery;
/*
코딩테스트 연습 < 서머코딩/윈터코딩(~2018) < 배달
https://programmers.co.kr/learn/courses/30/lessons/12978
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    public int solution(int N, int[][] road, int K) {
        AdjList adjList = new AdjList(N);
        for(int[] one : road)
            adjList.add(one[0], one[1], one[2]);

        int count = 0;
        for(int one : adjList.dijkstra(N, 1)) {
            if(one <= K) count++;
        }
        return count;
    }
}

class AdjList {
    List<List<MyPair>> list;
    AdjList(int len) {
        list = new ArrayList<>(++len);  // 인덱스 1부터 사용
        while(len-- > 0) list.add(new ArrayList<>());
    }
    public void add(int idx1, int idx2, int cost) {
        list.get(idx1).add(new MyPair(idx2, cost));
        list.get(idx2).add(new MyPair(idx1, cost));
    }
    public int[] dijkstra (int totalCount, int src) {
        PriorityQueue<MyPair> pq = new PriorityQueue<>();
        int[] dist = new int[totalCount+1];     // 인덱스 1부터 사용
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        pq.add(new MyPair(src, 0));

        while(pq.size() > 0) {
            MyPair curPair = pq.poll();
            int curIdx = curPair.idx;
            int curCost = curPair.cost;
            List<MyPair> curList = list.get(curIdx);

            for(int i=0; i<curList.size(); i++) {
                MyPair one = curList.get(i);
                int nextIdx = one.idx;
                int nextCost = curCost + one.cost;

                if(dist[nextIdx] <= nextCost) continue;

                if(dist[nextIdx] > nextCost) {
                    dist[nextIdx] = nextCost;
                    pq.add(new MyPair(nextIdx, nextCost));
                }
            }
        }
        return arrayCopy(dist, 1, totalCount);
    }
    public static int[] arrayCopy(int[] oriArr, int oriIdx, int copyLength) {
        int[] retArr = new int[copyLength];
        System.arraycopy(oriArr, oriIdx, retArr, 0, copyLength);
        return retArr;
    }
}

class MyPair implements Comparable<MyPair>{
    int idx, cost;
    MyPair(int idx, int cost) {
        this.idx = idx;
        this.cost = cost;
    }
    @Override
    public int compareTo(MyPair myPair) {
        return cost - myPair.cost;
    }
    @Override
    public String toString() {
        return String.format("[idx:%s, cost:%s]", idx, cost);
    }
}
