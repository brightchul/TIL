package baekjoon.p1753;

/*
https://www.acmicpc.net/problem/1753


 */


import java.io.*;
import java.util.*;

public class Main {
    final static int INF = Integer.MAX_VALUE;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int nodeCount = Integer.parseInt(st.nextToken());
        int edgeCount = Integer.parseInt(st.nextToken());
        int startNode = Integer.parseInt(br.readLine());

        List<List<Pair>> nodeList = new ArrayList<>();
        for (int i = 0; i <= nodeCount; i++) {
            nodeList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            nodeList.get(u).add(new Pair(v, w));
        }

        int[] dist = new int[nodeCount + 1];
        boolean[] visied = new boolean[nodeCount + 1];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startNode] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(startNode, 0));

        while (!pq.isEmpty()) {
            Pair target = pq.poll();
            int node = target.node;
            int distance = target.distance;

            if (visied[node]) continue;
            visied[node] = true;

            for (Pair otherTarget : nodeList.get(node)) {
                int otherNode = otherTarget.node;
                int otherDistance = distance + otherTarget.distance;

                if (otherDistance < dist[otherNode]) {
                    dist[otherNode] = otherDistance;
                    pq.add(new Pair(otherNode, otherDistance));
                }
            }
        }

        for (int i = 1; i <= nodeCount; i++) {
            if (dist[i] == INF) {
                bw.write("INF");
            } else {
                bw.write(String.valueOf(dist[i]));
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }
}

class Pair implements Comparable<Pair> {
    int node;
    int distance;

    Pair(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Pair o) {
        return this.distance - o.distance;
    }
}
