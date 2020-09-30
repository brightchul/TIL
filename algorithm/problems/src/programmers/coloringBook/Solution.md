# 프로그래머스 문제 : 카카오 프렌즈 컬러링북

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/1829)

그림에 몇 개의 영역이 있는지와 가장 큰 영역의 넓이는 얼마인지 계산하는 프로그램을 작성해보자.

​    

### 입력 형식

입력은 그림의 크기를 나타내는 `m`과 `n`, 그리고 그림을 나타내는 `m × n` 크기의 2차원 배열 `picture`로 주어진다. 제한조건은 아래와 같다.

- `1 <= m, n <= 100`
- `picture`의 원소는 `0` 이상 `2^31 - 1` 이하의 임의의 값이다.
- `picture`의 원소 중 값이 `0`인 경우는 색칠하지 않는 영역을 뜻한다.

​    

### 출력 형식

리턴 타입은 원소가 두 개인 정수 배열이다. 그림에 몇 개의 영역이 있는지와 가장 큰 영역은 몇 칸으로 이루어져 있는지를 리턴한다.

```
[입출력 예]
m n
6 4

picture
[[1, 1, 1, 0], 
 [1, 2, 2, 0], 
 [1, 0, 0, 1], 
 [0, 0, 0, 1], 
 [0, 0, 0, 3], 
 [0, 0, 0, 3]]

answer
[4,5]

// 자세한 예시 설명은 홈페이지 참조
```

​    

## 문제 풀이

BFS문제 이다. 현재 좌표의 값을 확인하고, 인접한 영역으로 탐색을 계속한다. 그리고 방문했던 좌표는 캐시해 놓는다. 이것을 반복하면서 카운트 하면 된다.

​    


## 코드 구현 [[전체코드]](./Solution.java)

```java
public int[] solution(int m, int n, int[][] picture) {
    rowLen = m;
    colLen = n;
    visited = new int[rowLen][colLen];

    int comp = 0;
    int maxSize = 0;

    for (int row = 0; row < rowLen; row++) {
        for (int col = 0; col < colLen; col++) {
            if (picture[row][col] == 0) continue;

            // 이미 방문했으면 넘어간다.
            if (visited[row][col] != 0) continue;

            // 방문 하지 않은 영역일 경우 카운트 한다.
            ++comp;

            // 큐를 만들고 동일 컬라를 설정한다.
            Queue<Pair> q = new LinkedList<>();
            q.offer(pair(row, col));

            int color = picture[row][col];
            int size = 0;

            // 큐가 다 떨어지기 전까지 계속 무한으로 돈다.
            while (q.size() > 0) {
                Pair target = q.poll();
                if (visited[target.row][target.col] != 0) continue;

                size++;
                visited[target.row][target.col] = comp;

                for (int[] direction : directions) {
                    int newRow = target.row + direction[0];
                    int newCol = target.col + direction[1];

                    if (isOutRange(newRow, newCol)) continue;
                    if (picture[newRow][newCol] == color 
                        && visited[newRow][newCol] == 0) {
                        q.offer(pair(newRow, newCol));
                    }
                }
            }

            maxSize = Math.max(maxSize, size);
        }
    }
    return new int[] {comp, maxSize};
}
```




