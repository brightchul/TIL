package programmers.numberGame;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
    public int solution(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);

        int idxA = 0, idxB = 0;
        while(idxB < B.length){
            if(A[idxA] < B[idxB++]) idxA++;
        }
        return idxA;
    }
    public int solution2(int[] A, int[] B) {
        PriorityQueue<Integer> pqA = new PriorityQueue<>();
        PriorityQueue<Integer> pqB = new PriorityQueue<>();

        for(int i=0; i<A.length; i++) {
            pqA.add(A[i]);
            pqB.add(B[i]);
        }
        while(pqB.size() > 0) {
            if(pqA.peek() < pqB.peek())
                pqA.poll();
            pqB.poll();
        }
        return A.length - pqA.size();
    }

}
