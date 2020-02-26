package programmers.howToLineUp;

import java.util.ArrayList;

public class Solution {
    public int[] solution(int n, long k) {
        ArrayList<Integer> list = new ArrayList<>(n+1);
        int[] result = new int[n];
        int rIdx = 0;
        long pacValue = 1;

        list.add(0);
        for(int i=1; i<=n; i++) {
            pacValue *= i;
            list.add(i);
        }

        while(list.size() > 1) {
            pacValue /= list.size()-1;
            int idx = (int)Math.ceil((double)k/pacValue);
            result[rIdx++] = list.get(idx);
            list.remove(idx);

            if(k % pacValue == 0) {
                for(int i=list.size()-1; i>0; i--)
                    result[rIdx++] = list.get(i);
                break;
            }
            k = k % pacValue;
        }
        return result;
    }
}
