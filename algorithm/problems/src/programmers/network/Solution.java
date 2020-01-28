package programmers.network;
/*
코딩테스트 연습>  깊이/너비 우선 탐색(DFS/BFS) > 네트워크
https://programmers.co.kr/learn/courses/30/lessons/43162
 */
import java.util.ArrayList;

public class Solution {
    public int solution(int n, int[][] computers) {
        ArrayList<Integer> passedList = new ArrayList<>();
        int count = 0;
        for(int i=0; i<n; i++) {
            if(passedList.contains(i)) continue;
            recursive(i, computers, passedList);
            count++;
        }
        System.out.println();
        return count;
    }
    public void recursive(int idx, int[][] computers, ArrayList<Integer> passedList) {
        int[] target = computers[idx];
        for(int i=0; i<target.length; i++) {
            if(target[i] == 0) continue;
            if(passedList.contains(i)) continue;
            passedList.add(i);
            recursive(i, computers, passedList);
        }
    }
}
