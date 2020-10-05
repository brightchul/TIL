# 프로그래머스 : 순위

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/49191)

###### 문제 설명

n명의 권투선수가 권투 대회에 참여했고 각각 1번부터 n번까지 번호를 받았습니다. 권투 경기는 1대1 방식으로 진행이 되고, 만약 A 선수가 B 선수보다 실력이 좋다면 A 선수는 B 선수를 항상 이깁니다. 심판은 주어진 경기 결과를 가지고 선수들의 순위를 매기려 합니다. 하지만 몇몇 경기 결과를 분실하여 정확하게 순위를 매길 수 없습니다.

선수의 수 n, 경기 결과를 담은 2차원 배열 results가 매개변수로 주어질 때 정확하게 순위를 매길 수 있는 선수의 수를 return 하도록 solution 함수를 작성해주세요.

```
[제한사항]
- 선수의 수는 1명 이상 100명 이하입니다.
- 경기 결과는 1개 이상 4,500개 이하입니다.
- results 배열 각 행 [A, B]는 A 선수가 B 선수를 이겼다는 의미입니다.
- 모든 경기 결과에는 모순이 없습니다.

[입출력 예]
n	results	                                    return
5	[[4, 3], [4, 2], [3, 2], [1, 2], [2, 5]]	2
```

​    


## 문제 풀이

그래프를 생성하고 DFS로 순회하면서 카운트 하면 된다. 

선수 A에 대해 이긴 선수들과 진 선수들을 DFS로 순회하면서 카운트 한뒤에 그 합이 `전체 선수 - 1` 이면 해당 선수의 순위는 알 수 있다.

문제에서 모순이 없다고 했으니, 이기는 경우는 다 이긴다고 보고, 지는 경우는 다 지는 경우로 봤다. 즉 A가 B를 이기고 B가 C를 이겼는데 C가 A를 이기는 경우는 없는 것이다.

​    

## 코드 구현 [[전체코드]](./Solution.java)

모순이 없다고 했기 때문에 하나의 visited 배열을 이용해서 순회를 했다. 원래는 winner, loser에 대해서 전부 개별적으로 dfs 메서드를 만들었는데, 통과되고 나서 다른 분 코드를 보니 람다 함수를 이용해서 훨씬 깔끔하게 구현을 해놓아서 그것을 참고해서 수정했다.

```java
public int countMatch(int player) {
    boolean[] visited = new boolean[arr.length];
    
    // 람다 함수를 이용해서 dfs메서드 하나만 이용해서 가능하게 만들수 있다.
    dfs(player, visited, node -> node.winnerList);
    visited[player] = false; // 자기 자신은 위에서 한번 visited되었기 때문에 초기화
    dfs(player, visited, node -> node.loserList);

    int count = 0;
    for (boolean one : visited) {
        if (one) count++;
    }
    return count - 1;
}

private void dfs(int player, boolean[] visited, Function<MyNode, List<Integer>> func) {
    if (visited[player]) return;
    visited[player] = true;

    for (int one : func.apply(arr[player])) {
        if (!visited[one]) dfs(one, visited, func);
    }
}
```

