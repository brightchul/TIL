package programmers.moreSpicy;
/*
* https://programmers.co.kr/learn/courses/30/lessons/42626
*/

public class Solution {
    public int solution(int[] scoville, int K) {
        MinHeap heap = new MinHeap(scoville.length);

        for(int one : scoville)
            heap.add(one);

        int count = 0;
        while(heap.peekMin() < K) {
            if(heap.length() == 1) return -1;
            heap.add(heap.popMin() + heap.popMin() * 2);
            count++;
        }

        return count;
    }
}

class MinHeap {
    private int[] arr;
    private int lastIdx = -1;
    MinHeap(int len) {
        arr = new int[len];
    }
    public void add(int newValue) {
        if(length() == arr.length) return;

        arr[++lastIdx] = newValue;
        int parentIdx, idx = lastIdx;

        while(true) {
            if(idx == 0) return;
            if(arr[(parentIdx = (idx-1)/2)] <= arr[idx]) return;
            swap(idx, parentIdx);
            idx = parentIdx;
        }
    }
    public int popMin() {
        if(length() == 0) return -1;

        int returnValue = arr[0];
        arr[0] = arr[lastIdx];
        arr[lastIdx] = -1;
        lastIdx--;

        int idx = 0, left, right, tempIdx;
        while(true) {
            tempIdx = idx;
            left = idx * 2 + 1;
            right = left+1;

            if(left > lastIdx) break;
            if(arr[idx] > arr[left]) { tempIdx = left; }

            // 이부분 arr[tempIdx].. 즉 부모 자식2가지 모두다 비교해서 그중 최소를 부모로 만들어야됨.
            if(right <= lastIdx && arr[tempIdx] > arr[right]) {
                tempIdx = right;
            }

            if(idx == tempIdx) break;

            swap(idx, tempIdx);
            idx = tempIdx;
        }
        return returnValue;
    }
    public int peekMin() {
        if(length() == 0) return -1;
        return arr[0];
    }

    private void swap(int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
    public int length() {return lastIdx+1;}
}