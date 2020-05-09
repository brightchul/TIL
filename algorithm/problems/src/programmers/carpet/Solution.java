package programmers.carpet;

public class Solution {
    public int[] solution(int brown, int red) {
        int temp = (brown - 4 ) / 2;
        int limit = temp/2;
        int[] result = new int[2];

        for(int i=1; i<=limit; i++) {
            if((temp-i) * i == red) {
                result[0] = temp-i+2;
                result[1] = i+2;
                break;
            }
        }
        return result;
    }
}