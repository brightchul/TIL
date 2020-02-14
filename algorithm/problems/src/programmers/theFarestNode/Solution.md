# 프로그래머스 문제 : 가장 먼 노드

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/49189)

n개의 노드가 있는 그래프가 있습니다. 각 노드는 1부터 n까지 번호가 적혀있습니다. 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다. 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.

노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때, 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.

```
[제한사항]
- 노드의 개수 n은 2이상 20,000 이하
- 간선은 양방향, 1개 이상 50,000개 이하
- vertex 배열 각 행 [a,b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미

[입출력 예]
n = 6, vertex = [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]
return 3
```



## 문제풀이

너비 우선 탐색을 이용하면 된다. 1번 노드를 시작으로 너비 우선 탐색을 이용해서 마지막으로 탐색하는 노드들의 갯수를 카운트한다.



## 코드 구현 [[전체 코드]](./Solution.java)

1\. 그래프로 노드와 각 노드들에 연결되어 있는 다른 노드들을 저장했다.

2\.  hashset을 이용해서 순회할 노드들을 저장하고 그 다음번 호출에서 해당 hashset에 있는 노드들을 순회하면서, 순회한 노드들의 연결된 노드들을 새로운 hashset에 저장했다.

2-1\. 이 과정에서 순회하는 노드들을 passed에 저장한 다음, 순회중에 hashset에 저장하기 전 passed에 있는지를 확인하는 과정을 거쳤다.

3\. 새로운 hashset에 하나라도 들어가있으면 다음 순회를 하고 아닐경우 1번 노드에서 가장 먼 노드라는 의미이므로 bfs를 마친다.

4\. 마지막 순회때 사용했던 hashset의 사이즈를 반환하면 끝이 난다.



```java
public int findDeepestCount() {
    set.clear();
    Arrays.fill(passed, 0);
    set.add(1);
    bfs(0);
    return set.size();
}

public void bfs(int depth) {
    HashSet<Integer> newSet = new HashSet<>();

    for(int one:set) {
        passed[one]++;
    }
    for(int one : set) {
        for(int other: data[one]) {
            if(passed[other] > 0) continue;
            newSet.add(other);
        }
    }
    if(newSet.size() > 0) {
        set = newSet;
        bfs(depth+1);
    }
}
```

