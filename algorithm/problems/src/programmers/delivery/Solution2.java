package programmers.delivery;

import java.util.*;

/*
코딩테스트 연습 < 서머코딩/윈터코딩(~2018) < 배달
https://programmers.co.kr/learn/courses/30/lessons/12978
 */

public class Solution2 {
    public int solution(int N, int[][] road, int K) {
        MyGraph mg = new MyGraph(N);
        for(int[] one : road)
            mg.add(one[0], one[1], one[2]);
        int result = mg.countDelivery(K);
        return result;
    }
}


class MyGraph {
    private PriorityQueue<AccNode> heap;
    private List<HashMap<Integer, Integer>> list;
    private int len;
    private int timeLimit;
    private int[] passed;
    MyGraph(int len) {
        this.len = len;
        list = new ArrayList<>();
        for(int i=0; i<=len; i++)
            list.add(new HashMap<>());
    }
    public void add(int i, int j, int time) {
        HashMap<Integer, Integer> iMap = list.get(i);
        if(iMap.containsKey(j) && iMap.get(j) <= time) return;

        iMap.put(j, time);
        list.get(j).put(i, time);
    }
    public int countDelivery(int time) {
        timeLimit = time;
        passed = new int[len+1];
        heap = new PriorityQueue();
        heap.add(new AccNode(1,0));
        int result = bfs();
        return result;    // 자기자신
    }
    private int bfs() {
        if(heap.size() == 0) return 0;
        int count = 0;
        PriorityQueue<AccNode> nextHeap = new PriorityQueue<>();
        while(heap.size() > 0) {
            AccNode one = heap.poll();
            int idx = one.idx, curTime = one.accTime;
            if(curTime > timeLimit) continue;
            if(passed[idx] == 1) continue;
            passed[idx] = 1;
            count += 1;
            Map<Integer, Integer> idxMap = list.get(idx);
            for(int nextIdx : idxMap.keySet()) {
                nextHeap.add(new AccNode(nextIdx, curTime+idxMap.get(nextIdx)));
            }
        }

        heap.clear();
        heap = nextHeap;
        count += bfs();
        return count;
    }
}

class AccNode implements Comparable<AccNode> {
    int idx, accTime;
    AccNode(int i, int time) {
        idx = i;
        accTime = time;
    }
    @Override
    public String toString() {
        return String.format("[idx:%d, accTime:%d]", idx, accTime);
    }
    @Override
    public int compareTo(AccNode accNode) {
        return accTime - accNode.accTime;
    }

}