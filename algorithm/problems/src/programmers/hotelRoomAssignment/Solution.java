package programmers.hotelRoomAssignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args){
        long k = 10;
        long[] arr = new long[]{1, 3, 4, 1, 3, 1};
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.solution(k, arr)));
    }
    public long[] solution(long k, long[] roomNumber) {
        HashMap<Long, Long> hotel = new HashMap<>();
        long[] answer = new long[roomNumber.length];

        for(int i=0; i<roomNumber.length; i++) {
            answer[i] = this.recursive(roomNumber[i], hotel);
        }
        return answer;
    }
    public long recursive(long index, Map<Long, Long> hotel) {
        // 만약 해당 키가 없다면, 해당 index를 보내고, 그다음 인덱스를 저장해준다.
        if(!hotel.containsKey(index)) {
            hotel.put(index, index+1);
            return index;
        }

        // 만약 해당 키가 있다면 해당 키의 값으로 재귀호출을 한다.
        long jumpIndex = hotel.get(index);
        long result = recursive(jumpIndex, hotel);
        hotel.put(index, result+1);
        return result;
    }

    public long[] solution3(long k, long[] roomNumber) {
        Map<Long, Integer> hotel = new HashMap<>();
        long[] answer = new long[roomNumber.length];
        for(int i=0; i<roomNumber.length; i++) {
            long target = roomNumber[i];
            while(hotel.containsKey(target)) target++;
            hotel.put(target, 1);
            answer[i] = target;
        }
        return answer;
    }

    public long[] solution2(long k, long[] roomNumber) {
        long[] hotel = new long[(int) (k+1)]; // 0
        long[] answer = new long[roomNumber.length];

        for(int i=0; i<roomNumber.length; i++) {
            long target = roomNumber[i];
            while(hotel[(int) target] > 0) target++;

            hotel[(int) target] = 1;
            answer[i] = target;
        }
        return answer;
    }
}
