package baekjoon.p1916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
https://www.acmicpc.net/problem/1916
5
8
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
1 5

return 4
 */

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int cityCount;
    private static int busCount;
    private static ArrayList<ArrayList<Pair>> list;
    private static PriorityQueue<Pair> pq;
    private static int[] costs;
    private static boolean[] visited;
    private static int start;
    private static int end;

    public static void main(String[] args) throws IOException {
        init();
        run();
        System.out.println(costs[end]);
    }

    public static void init() throws IOException {
        cityCount = Integer.parseInt(br.readLine());
        busCount = Integer.parseInt(br.readLine());

        visited = new boolean[cityCount + 1];
        costs = new int[cityCount + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);

        pq = new PriorityQueue<>();

        list = new ArrayList<>(cityCount + 1);
        for (int i = 0; i <= cityCount; i++) list.add(new ArrayList<>());

        for (int i = 0; i < busCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            list.get(from).add(new Pair(to, cost));
        }
        st = new StringTokenizer(br.readLine(), " ");

        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
    }

    public static void run() {
        costs[start] = 0;
        pq.add(new Pair(start, 0));

        while (!pq.isEmpty()) {
            Pair fromPoint = pq.poll();
            int from = fromPoint.city;

            if (visited[from]) continue;
            visited[from] = true;

            ArrayList<Pair> oneList = list.get(from);

            for (Pair one : oneList) {

                int tempCost = costs[from] + one.cost;

                if (costs[one.city] > costs[from] + one.cost) {
                    costs[one.city] = tempCost;
                    pq.add(new Pair(one.city, tempCost));
                }
            }
        }
    }


    static class Pair implements Comparable<Pair> {
        int city;
        int cost;

        Pair(int city, int cost) {
            this.city = city;
            this.cost = cost;

        }

        @Override
        public int compareTo(Pair o) {
            return this.cost - o.cost;
        }
    }
}

