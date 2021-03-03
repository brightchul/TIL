# 백준 온라인 저지 : 최단경로

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1753)

방향그래프가 주어지면 주어진 시작점에서 다른 모든 정점으로의 최단 경로를 구하는 프로그램을 작성하시오. 단, 모든 간선의 가중치는 10 이하의 자연수이다.



```
[입력]
첫째 줄에 정점의 개수 V와 간선의 개수 E가 주어진다. (1≤V≤20,000, 1≤E≤300,000) 모든 정점에는 1부터 V까지 번호가 매겨져 있다고 가정한다. 

둘째 줄에는 시작 정점의 번호 K(1≤K≤V)가 주어진다. 

셋째 줄부터 E개의 줄에 걸쳐 각 간선을 나타내는 세 개의 정수 (u, v, w)가 순서대로 주어진다. 이는 u에서 v로 가는 가중치 w인 간선이 존재한다는 뜻이다. u와 v는 서로 다르며 w는 10 이하의 자연수이다. 서로 다른 두 정점 사이에 여러 개의 간선이 존재할 수도 있음에 유의한다.

[출력]
첫째 줄부터 V개의 줄에 걸쳐, i번째 줄에 i번 정점으로의 최단 경로의 경로값을 출력한다. 시작점 자신은 0으로 출력하고, 경로가 존재하지 않는 경우에는 INF를 출력하면 된다.

[예시]
5 6
1
5 1 1
1 2 2
1 3 3
2 3 4
2 4 5
3 4 6

return
0
2
3
7
INF
```





## 문제풀이

기본적인 다익스트라 문제이다. 그냥 다익스트라를 봐서 이해가 안되면 이 문제를 풀어보면 다 이렇게 쓰는거구나 이해를 할 수 있다.

몇가지 짚어보자면 그래프, 우선순위큐를 이용해서 출발지점과 연결된 각 노드들의 거리까지의 거리를 가능한 최소 거리부터 누적해서 더해가는 것이다. 



## 코드 구현

```java

import java.io.*;
import java.util.*;

public class Main {
    final static int INF = Integer.MAX_VALUE;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        
        // 입력값 받기 & 초기 세팅 =====================================
        
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
        PriorityQueue<Pair> pq = new PriorityQueue<>();        
        
        // 출발지점부터 시작 =================================================
        
        dist[startNode] = 0;
        pq.add(new Pair(startNode, 0));

        while (!pq.isEmpty()) {
            Pair target = pq.poll();
            int node = target.node;
            int distance = target.distance;

            // 이미 방문한 지점이면 건너 뛴다 ==================================
            if (visied[node]) continue;
            visied[node] = true;

            // 선택된 node에 연결된 다른 node들의 리스트를 가져온다 ==============
            for (Pair otherTarget : nodeList.get(node)) {
                int otherNode = otherTarget.node;
                
                // 선택된  node가 가지고 있는 거리를 더해서 선택지점부터 총거리를 구한다
                int otherDistance = distance + otherTarget.distance;

                // 출발지점부터 otherNode까지의 거리와 dist에 저장된 otherNode까지의 거리를 비교한다
                // 만약 더 작은 거리면 큐에 넣고 순회하도록 한다
                if (otherDistance < dist[otherNode]) {
                    dist[otherNode] = otherDistance;
                    pq.add(new Pair(otherNode, otherDistance));
                }
            }
        }

        // 결과 출력 ========================================================
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

```

