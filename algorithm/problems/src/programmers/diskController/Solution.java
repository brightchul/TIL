package programmers.diskController;

import java.util.ArrayList;
import java.util.Arrays;
public class Solution {
    public int solution(int[][] jobs) {
        // 정렬
        Arrays.sort(jobs, (a,b)->a[0]==b[0]?a[1]-b[1]:a[0]-b[0]);

        int acc = 0, time = 0;
        int cnt = 0, cursor = 0, len = jobs.length;
        MinHeap heap = new MinHeap();

        while(cnt < len) {
            // time 이하 요청을 힙에 넣는다.
            while(cursor < len) {
                if(jobs[cursor][0] <= time) heap.add(jobs[cursor++]);
                else break;
            }
            // time 이하 요청을 다 처리한 상태인지 체크
            if(heap.length() == 0) heap.add(jobs[cursor++]);

            int[] one = heap.popMin();

            // time 이후 요청만 있다면 time을 옮긴다.
            if(time < one[0]) time = one[0];

            time += one[1];
            acc += (time - one[0]);
            cnt++;
        }

        return acc / jobs.length;
    }
}

class MinHeap {
    ArrayList<int[]> list = new ArrayList<>();

    public void add(int[] one) {
        list.add(one);
        int idx = list.size() -1;
        while(true) {
            if(idx == 0) return;
            int parentIdx = (idx-1) / 2;
            if(list.get(parentIdx)[1] <= list.get(idx)[1] ) break;
            swap(parentIdx, idx);
            idx = parentIdx;
        }
    }
    public int[] popMin() {
        if(length() == 0) return null;

        int[] result = list.get(0);
        int lastIdx = list.size()-1, idx = 0;
        list.set(0, list.get(lastIdx));
        list.remove(lastIdx);

        lastIdx--;
        if(length() <= 1) return result;

        while(true) {
            int tempIdx = idx;
            int leftIdx = tempIdx * 2 + 1;
            int rightIdx = leftIdx + 1;

            if(leftIdx > lastIdx) break;
            if(list.get(tempIdx)[1] > list.get(leftIdx)[1]) tempIdx = leftIdx;
            if(rightIdx <= lastIdx
                    && list.get(tempIdx)[1] > list.get(rightIdx)[1]) tempIdx = rightIdx;
            if(idx == tempIdx) break;
            swap(idx, tempIdx);
            idx = tempIdx;
        }
        return result;
    }
    public int length() {
        return list.size();
    }
    private void swap(int idx1, int idx2) {
        int[] temp = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, temp);
    }
}