# 프로그래머스 : 섬 연결하기

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42861)

n개의 섬 사이에 다리를 건설하는 비용(costs)이 주어질 때, 최소의 비용으로 모든 섬이 서로 통행 가능하도록 만들 때 필요한 최소 비용을 return 하도록 solution을 완성하세요.

다리를 여러 번 건너더라도, 도달할 수만 있으면 통행 가능하다고 봅니다. 예를 들어 A 섬과 B 섬 사이에 다리가 있고, B 섬과 C 섬 사이에 다리가 있으면 A 섬과 C 섬은 서로 통행 가능합니다.



```
[제한 사항]
- 섬의 개수 n은 1 이상 100 이하입니다.
- costs의 길이는 ((n-1) * n) / 2이하입니다.
- 임의의 i에 대해, costs[i][0] 와 costs[i] [1]에는 다리가 연결되는 두 섬의 번호가 들어있습니다.
- costs[i] [2]에는 이 두 섬을 연결하는 다리를 건설할 때 드는 비용입니다.
- 같은 연결은 두 번 주어지지 않습니다. 또한 순서가 바뀌더라도 같은 연결로 봅니다. 
- 0과 1 사이를 연결하는 비용이 주어졌을 때, 1과 0의 비용이 주어지지 않습니다.
- 모든 섬 사이의 다리 건설 비용이 주어지지 않습니다. 이 경우, 두 섬 사이의 건설이 불가능한 것으로 봅니다.
- 연결할 수 없는 섬은 주어지지 않습니다.

[입출력 예시]
4	[[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]	return 4
```



## 문제 풀이

>  참고할 알고리즘 : 최소 신장 트리, Kruskal 알고리즘, Prime 알고리즘



이 문제에서는 최소비용으로 모든 섬들을 연결하는 다리를 건설할고 하는데 가장 적은 비용을 구하는 문제이다.

- 가장 적은 비용
- 모든 섬들이 직접적으로 연결되어 있거나 다른 섬을 타고 가는 간접적으로 연결되어야 한다.



#### 가장 적은 비용

먼저 가장 적은 비용을 구하려면 비용 크기를 기준으로 정렬이 필요하다. 가장 적은 비용부터 오름차순으로 정렬을 한 다음에 앞에서 부터 하나씩 순회하면 된다. 그러면 자연스럽게 가장 적은 비용으로 적용이 될 것이다.



#### 모든 섬을 연결

정렬한 섬들을 통해 하나씩 순회할 때 두섬이 연결되어 있는지를 깊이 우선 탐색을 통해 확인하면 된다. 물론 이렇게 하기 위해서는 서로 완전히 끊어져 있는 섬들을 가진 자료구조가 필요하다. 이것을 위해 그래프를 사용했다. 그리고 오름차순으로 되어 있는 각 값들을 순회하면서 연결이 되어 있으면 넘어가고, 연결이 안되어 있으면 연결한 뒤에 해당 비용을 저장하는 식으로 진행했다. 



## 코드 구현 [[전체코드]](./Solution.java)

```java
public int solution(int n, int[][] costs) {
    Graph graph = new Graph(n);
    Arrays.sort(costs, (a,b) -> a[2] - b[2]);
    for(int[] one : costs) {
        if(!graph.isConnect(one[0], one[1], new int[n])) {
            graph.addBridge(one[0], one[1], one[2]);
        }
    }
    return graph.getTotalCost();
}
```



깊이 우선 탐색을 적용한 isConnect이다. 그래프 클래스의 메서드로 들어가 있다. (전체코드 참고)

```java
public boolean isConnect(int start, int destination, int[] isPassed) {
    isPassed[start] = 1;
    ArrayList<Node> location = data.get(start);
    for(Node one : location) {
        if(one.isDest(destination)) return true;
        if(isPassed[one.dest] == 1) continue;
        if(isConnect(one.dest, destination, isPassed)) return true;
    }
    return false;
}
```



그렇게 그래프를 구한 다음에 합을 더해 반환했다. 하지만 그래프 자체를 서로가 연결되어 있는 식으로 만들었기 때문에 그냥 합을 더하면 2배가 되었다. 따라서 2를 나눠준 값을 반환한다.

```java
public int getTotalCost() {
    int totalCost = 0;
    for(ArrayList<Node> list : data) {
        for(Node one : list) totalCost += one.cost;
    }
    return totalCost/2;
}
```



