package algospot.FESTIVAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
2
6 3
1 2 3 1 2 3
6 2
1 2 3 1 2 3
 */
public class Main2 {
    final private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int caseCount = pint(br.readLine());
        while(caseCount-- > 0) run();
    }
    public static int pint(String str) {
        return Integer.parseInt(str);
    }
    public static void run() throws IOException{

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int days = pint(st.nextToken());
        int teams = pint(st.nextToken());

        // fees : int형으로 받고, 평균낼때에만 double 형변환
        st = new StringTokenizer(br.readLine(), " ");
        int[] fees = new int[days];
        for(int i=0; i<days; i++) fees[i] = pint(st.nextToken());

        // 처음에 teams 갯수대로 합을 해서 캐시하고, 그다음엔 인덱스+1씩 하면 된다.
        // len-2만큼 먼저 다 더해주고 안에서 len-1 인덱스를 더해준다.
        double result = Double.MAX_VALUE;
        int cache = 0;
        for(int i=0; i< teams-1; i++) cache += fees[i];

        for(int len = teams; len <= days; len++) {
            cache += fees[len-1];
            int intervalMin = cache;
            int intervalCache = cache;
            for(int end = len; end < days; end++) {
                intervalCache = intervalCache - fees[end-len]+fees[end];
                intervalMin = Math.min(intervalMin, intervalCache);
            }
            result = Math.min(result, (double)intervalMin / len);
        }
        System.out.printf("%.8f\n", result);
    }

}
