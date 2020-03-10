package programmers.baseStationInstallation;

public class Solution {
    public int solution(int n, int[] stations, int w) {
        int start = 1, end;
        final double INTERVAL = w+w+1;
        double result = 0;

        for(int station : stations) {
            end = station - w;
            result += Math.ceil((end - start) / INTERVAL);
            start = station + w + 1;
        }
        if(start <= n)
            result += Math.ceil((n+1 - start) / INTERVAL);

        return (int)result;
    }
}
