package algospot.FESTIVAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
https://algospot.com/judge/problem/read/FESTIVAL
록 페스티벌

2
6 3
1 2 3 1 2 3
6 2
1 2 3 1 2 3

1.75000000000
1.50000000000
 */

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException{
        int totalCaseCount = Integer.parseInt(br.readLine());
        while(totalCaseCount-- > 0) run();
    }
    public static void run() throws IOException{
        // days, teams 입력 받아오기
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int days = Integer.parseInt(st.nextToken());
        int teams = Integer.parseInt(st.nextToken());

        // 요금 배열 입력 받아오기
        st = new StringTokenizer(br.readLine(), " ");
        double[] fees = new double[days];
        for(int i=0; i<days; i++) fees[i] = Double.parseDouble(st.nextToken());

        // 순회하면서 최소값을 찾는다.
        int limit = days - teams;
        double ret = Double.MAX_VALUE;

        for(int start=0; start<=limit; start++) {
            double tempTotalFees=0.0;
            double len = (double)teams;

            // start ~ start+teams 까지의 평균을 먼저 구한다.
            for(int i=start; i<start+teams; i++) tempTotalFees += fees[i];
            ret = Double.min(ret, tempTotalFees / len);

            // 그 이후 값들을 하나씩 더해서 평균을 구해서 비교한 다음 최소값을 적용한다.
            for(int i=start+teams; i<fees.length; i++) {
                tempTotalFees += fees[i];
                ret = Double.min(ret, tempTotalFees/++len);
            }
        }
        System.out.printf("%.8f\n", ret);
    }
}