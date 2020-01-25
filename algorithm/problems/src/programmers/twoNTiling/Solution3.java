package programmers.twoNTiling;

public class Solution3 {
    private long CASE_NUM =  1_000_000_007L;
    public int solution(int n) {
        long result = 1, preNum = 1, temp = 0;
        for(int i=2; i<=n; i++) {
            temp = result;
            result += (preNum % CASE_NUM);
            preNum = temp;
        }
        return (int)(result % CASE_NUM);
    }
}
