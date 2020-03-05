# 프로그래머스 : 배달

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/12978)

N개의 마을로 이루어진 나라가 있습니다. 이 나라의 각 마을에는 1부터 N까지의 번호가 각각 하나씩 부여되어 있습니다. 각 마을은 양방향으로 통행할 수 있는 도로로 연결되어 있는데, 서로 다른 마을 간에 이동할 때는 이 도로를 지나야 합니다. 도로를 지날 때 걸리는 시간은 도로별로 다릅니다. 현재 1번 마을에 있는 음식점에서 각 마을로 음식 배달을 하려고 합니다. 각 마을로부터 음식 주문을 받으려고 하는데, N개의 마을 중에서 K 시간 이하로 배달이 가능한 마을에서만 주문을 받으려고 합니다. 

마을의 개수 N, 각 마을을 연결하는 도로의 정보 road, 음식 배달이 가능한 시간 K가 매개변수로 주어질 때, 음식 주문을 받을 수 있는 마을의 개수를 return 하도록 solution 함수를 완성해주세요.

```
[제한사항]
- 마을의 개수 N은 1 이상 50 이하의 자연수입니다.
- road의 길이(도로 정보의 개수)는 1 이상 2,000 이하입니다.
- road의 각 원소는 마을을 연결하고 있는 각 도로의 정보를 나타냅니다.
- road는 길이가 3인 배열이며, 순서대로 (a, b, c)를 나타냅니다.
    - a, b(1 ≤ a, b ≤ N, a != b)는 도로가 연결하는 두 마을의 번호이며, 
    - c(1 ≤ c ≤ 10,000, c는 자연수)는 도로를 지나는데 걸리는 시간입니다.
    - 두 마을 a, b를 연결하는 도로는 여러 개가 있을 수 있습니다.
    - 한 도로의 정보가 여러 번 중복해서 주어지지 않습니다.
- K는 음식 배달이 가능한 시간을 나타내며, 1 이상 500,000 이하입니다.
- 임의의 두 마을간에 항상 이동 가능한 경로가 존재합니다.
- 1번 마을에 있는 음식점이 K 이하의 시간에 배달이 가능한 마을의 개수를 return 합니다.

[예시]
N=5, road=[[1,2,1],[2,3,3],[5,2,2],[1,4,2],[5,3,1],[5,4,2]], K=3, return 4
N=6, road=[[1,2,1],[1,3,2],[2,3,2],[3,4,3],[3,5,2],[3,5,3],[5,6,1]], K=3, return 4
N=4, road=[[1,2,3],[1,3,1],[2,3,1],[2,4,1]], K=3, return 4
```





## 문제 풀이

결론부터 말하자면 다익스트라 알고리즘을 활용하면 잘 풀린다. 

처음에는 그래프와 BFS를 이용해서 풀려고 해보았다. 하지만 통과하지 못했다. 그래서 BFS를 할 때 큐를 이용하지 않고 힙을 이용해서 시도해 보았지만 마찬가지 였다.

무엇이 문제일지 고민해봤더니 `N=4, road=[[1,2,3],[1,3,1],[2,3,1],[2,4,1]], K=3` 이러한 케이스일때 문제가 생기는 것이었다. 이 케이스일 때 1->2는 3이 들지만 1->3->2로 갈 경우 2만큼 든다. BFS를 할때 (1), (2,3), (4) 이런식으로 가게 되는데 1->2로 가는 3을 가지고 그 이후의 경로의 cost를 계산하는 문제가 생긴다. 따라서 1->2로도 갈수있지만 1->3->2로 갈 때 가장 비용이 적게 드는 것을 파악하고 최소 cost이 이 경로를 반영해서 해야 한다.

마을 개수가 50개, road 갯수가 2000이하 이므로 이런 경우들을 기존의 방법으로는 커버를 할 수 없었다. 그래서 찾아보니 다익스트라 알고리즘을 활용하면 된다고 나와 있었고 활용하니 잘 풀렸다.

기존의 BFS 순회에서는 한번 간 곳은 더이상 가지않는데 다익스트라 알고리즘에서는 이미 한번 거친 곳 또한 누적 cost가 더 낮을 경우에는 다시 순회하는 특성을 가진다. 이 과정을 통해서 누적 cost가 낮아지면 그 이후 cost 또한 다 반영된다.





## 코드 구현 [[전체코드]](Solution.java)

다익스트라 알고리즘을 이용해서 1번 마을에서 각 마을에 얼마나 걸리는지 int[]로 결과를 받는다. 그 다음 기준 비용은 K값 이하만 카운트 하면 답이 나온다.

```java
public int[] dijkstra (int totalCount, int src) {
    PriorityQueue<MyPair> pq = new PriorityQueue<>();
    int[] dist = new int[totalCount+1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;

    pq.add(new MyPair(src, 0));

    while(pq.size() > 0) {
        MyPair curPair = pq.poll();
        int idx = curPair.idx;
        int cost = curPair.cost;
        List<MyPair> curList = list.get(idx);

        for(int i=0; i<curList.size(); i++) {
            MyPair one = curList.get(i);
            int nextIdx = one.idx;
            int nextCost = cost + one.cost;

            if(dist[nextIdx] <= nextCost) continue;

            if(dist[nextIdx] > nextCost) {
                dist[nextIdx] = nextCost;
                pq.add(new MyPair(nextIdx, nextCost));
            }
        }
    }
    // 인덱스0은 사용안했으므로 제외하고 반환
    return arrayCopy(dist, 1, totalCount);
}
```
