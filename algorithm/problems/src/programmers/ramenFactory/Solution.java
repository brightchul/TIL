package programmers.ramenFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] dates = {4,10,15};
        int[] supplies = {20, 5, 10};
        s.solution(4, dates, supplies, 30); // 2나와야 함.
        // dates에서 돌아오지 않는 커서 인덱스를 만들고
        // 큐에 해당 서플라이즈를 해당 날짜에 맞게 다 넣으면 되지 않을까?
    }
    public int solution(int stock, int[] dates, int[] supplies, int k) {
        int count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        TreeMap<Integer, Integer> map = new ConcurrentHashMap<>();
        for(int i=0; i<dates.length; i++) {
            map.put(dates[i], supplies[i]);
        }

        int now = 1;
        now += stock;
        for(Integer date : map.keySet()) {
            if(date <= now) {
                pq.add(map.get(date));
                map.remove(date);
            }
        }

        while(now < k) {
            now += pq.poll();
            count++;
            for(Integer date : map.keySet()) {
                if(date <= now) {
                    pq.add(map.get(date));
                    map.remove(date);
                }
            }
        }


        return count;
    }
}

class Node implements Comparable<Node> {
    int date;
    int supply;
    Node(int date, int supply) {
        this.date = date;
        this.supply = supply;
    }

    @Override
    public int compareTo(Node o) {
        return this.supply - o.supply;
    }
}