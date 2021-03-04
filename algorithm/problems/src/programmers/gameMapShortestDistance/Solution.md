# 프로그래머스 : 게임 맵 최단거리

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/1844)

ROR 게임은 두 팀으로 나누어서 진행하며, 상대 팀 진영을 먼저 파괴하면 이기는 게임입니다. 따라서, 각 팀은 상대 팀 진영에 최대한 빨리 도착하는 것이 유리합니다. \[자세한 내용은 링크 참조\]

```
[제한사항]
- maps는 n x m 크기의 게임 맵의 상태가 들어있는 2차원 배열로, n과 m은 각각 1 이상 100 이하의 자연수입니다.
- n과 m은 서로 같을 수도, 다를 수도 있지만, n과 m이 모두 1인 경우는 입력으로 주어지지 않습니다.
- maps는 0과 1로만 이루어져 있으며, 0은 벽이 있는 자리, 1은 벽이 없는 자리를 나타냅니다.
- 처음에 캐릭터는 게임 맵의 좌측 상단인 (1, 1) 위치에 있으며, 상대방 진영은 게임 맵의 우측 하단인 (n, m) 위치에 있습니다.

[입출력]
[[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,1],[0,0,0,0,1]]	return 11
[[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,0],[0,0,0,0,1]]	return -1
```

​                  

## 문제 풀이

BFS 문제이다. BFS로 풀면 된다는 것을 알고 BFS로직으로 그냥 그대로 하기만 해도 바로 풀리는편인데, 처음에 이것을 보고 괜히 어렵게 접근해서 DP로 풀려고 하다가 엄청 돌아갔다. 그러니 유형별로 적절한 알고리즘 매칭을 잘 기억하자.



## 코드 구현

BFS대로 죽 풀면된다. 첫번쨰 지점인 0,0 부터 시작해서 BFS로 계속 순회를 하면서 거리를 계속 없데이트 해준다.  이게 만약 각 칸마다의 거리가 제각각 달랐다면 최소 거리에 대한 다른 로직을 고려해야 했을테지만 전부다 칸이 1칸씩이기 때문에 그럴 필요가 없다. 

```javascript
function solution(maps) {
    const [rowLen, colLen] = [maps.length, maps[0].length];
    const INF = 100_000_003;
    const queue = [new Pair(0, 0, 1)];
    const dirs = [[-1, 0], [1, 0], [0, -1], [0, 1]];	// 4방향

    let dist = array(rowLen, colLen, INF);		// 최소값을 구하는 것이기에 INF로 초기화
    let visited = array(rowLen, colLen, false);

    while (queue.length > 0) {
        const { row, col, count } = queue.shift();

        if (visited[row][col]) continue;	// 4방향으로 가기 때문에 왔던 길을 되돌아가지 않게 한다
        visited[row][col] = true;

        for (const [moveRow, moveCol] of dirs) {
            const [newRow, newCol, newCount] = [row + moveRow, col + moveCol, count + 1];

            // 0인 곳, 영역너머의 공간으로 이동하려할 경우를 막는다.
            if (isOut(newRow, newCol, maps)) continue;

            if (dist[newRow][newCol] > newCount) {
                dist[newRow][newCol] = newCount;
                queue.push(new Pair(newRow, newCol, newCount));
            }
        }
    }

    if (dist[rowLen - 1][colLen - 1] === INF) return -1;
    else return dist[rowLen - 1][colLen - 1];
}

function array(row, col, initValue) {
    return Array(row).fill(0).map(() => Array(col).fill(initValue));
}

function isOut(row, col, maps) {
    if (row < 0 || col < 0) return true;
    if (row >= maps.length || col >= maps[0].length) return true;
    if (maps[row][col] === 0) return true;

    return false;
}

class Pair {
    constructor(row, col, count) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
}

```

