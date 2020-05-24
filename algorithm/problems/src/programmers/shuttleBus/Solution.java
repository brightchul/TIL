package programmers.shuttleBus;

import java.util.*;

public class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0; i<timetable.length; i++) {
            pq.add(convertMin(timetable[i]));
        }

        int arrivedMin = 9 * 60;
        for(int i=1; i<n; i++) {
            int count = 0;
            while(count < m) {
                if(pq.peek() <= arrivedMin) {
                    count++;
                    pq.poll();
                } else {
                    break;
                }
            }
            arrivedMin += t;
        }

        Map<Integer, Integer> map = new HashMap<>();
        int passengersCount = 0;
        while(passengersCount < m) {
            if(pq.size() == 0) break;
            if(pq.peek() <= arrivedMin) {
                int one = pq.poll();
                map.put(one, Optional.ofNullable(map.get(one)).orElse(0) + 1);
                passengersCount++;
            } else {
                break;
            }
        }

        int result = 0;
        if(passengersCount == m) {
            Integer[] arr = map.keySet().toArray(new Integer[map.size()]);
            result = arr[arr.length-1] -1;
        } else {
            result = arrivedMin;
        }

        String answer = String.format("%02d:%02d", result/60, result%60);
        return answer;
    }

    public int convertMin(String time) {
        String[] arr = time.split(":");
        return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
    }
}