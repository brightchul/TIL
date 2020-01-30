# 프로그래머스 : 등굣길

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42898)

계속되는 폭우로 일부 지역이 물에 잠겼습니다. 물에 잠기지 않은 지역을 통해 학교를 가려고 합니다. 집에서 학교까지 가는 길은 m x n 크기의 격자모양으로 나타낼 수 있습니다.

가장 왼쪽 위, 즉 집이 있는 곳의 좌표는 (1, 1)로 나타내고 가장 오른쪽 아래, 즉 학교가 있는 곳의 좌표는 (m, n)으로 나타냅니다.

격자의 크기 m, n과 물이 잠긴 지역의 좌표를 담은 2차원 배열 puddles이 매개변수로 주어집니다. 집에서 학교까지 갈 수 있는 최단경로의 개수를 1,000,000,007로 나눈 나머지를 return 하도록 solution 함수를 작성해주세요.

```
[제한 사항]
- 격자의 크기 m, n은 1 이상 100 이하인 자연수입니다.
- m과 n이 모두 1인 경우는 입력으로 주어지지 않습니다.
- 물에 잠긴 지역은 0개 이상 10개 이하입니다.
- 집과 학교가 물에 잠긴 경우는 입력으로 주어지지 않습니다.

[입출력 예]
m=4, n=3, puddles=[[2, 2]], return 4
```



## 문제 풀이 

처음에 접근을 잘못해서 재귀호출로 각 좌표로 이동하면서 마지막 m,n좌표 까지 닿은 것에 대해서만 카운트를 했다. 즉 웅덩이가 없는 좌표로만 죽 가게 했으니 완전탐색이었다. 따라서 효율성 테스트를 통과하지 못했다. 

다시 방식을 달리했다. 

1,1부터 시작해서 거기서부터 각 행을 죽 타고 내려오면서 누적해서 더하는 식으로 푸는 방법을 바꾸었다. 아래와 같은 방식이다.

```
1 0 0 0    1 1 1 1    1 1 1 1    1 1 1 1    1 1 1 1
0 0 0 0 => 0 0 0 0 => 1 0 0 0 => 1 2 3 4 -> 1 2 3 4
0 0 0 0    0 0 0 0    0 0 0 0    0 0 0 0    1 3 6 10
```



## 코드 구현 [[전체코드]](./Solution.java)

처음에 완전 탐색으로 접근했던 코드이다.

```java
public boolean recursive(int row, int col) {
    if(isOutOfBounder(row, col)) return false;
    if(!map[row][col]) return false;
    if(row == destRow && col == destCol) {
        count  = (count + 1) % 1000000007;
        return true;
    }

    boolean row_1 = isOutOfBounder(row+1, col);
    if(!row_1) {
        row_1 = map[row + 1][col] && recursive(row + 1, col);
    }
    boolean col_1 = isOutOfBounder(row, col+1);
    if(!col_1) {
        col_1 = map[row][col+1] && recursive(row, col + 1);
    }
    return map[row][col] = row_1 || col_1;
}
```



다시 다르게 접근해서 푼 코드이다. boolean 배열과 int 배열을 하나씩 사용해서 웅덩이를 boolean 배열에 넣고 int배열에서 순회하면서 값을 누적시키다가 boolean 배열에서 웅덩이에 다다르면 continue했다. int 배열 생성시 초기값이 0이기 때문에 이후 웅덩이 근처 다른 지점에서 웅덩이를 더해도 0이 된다.

일부러 +1만큼 더 배열 크기를 만들어서 m,n이 그대로 값을 적용할수 있게 했다 또한 1번째 열과 행에서 -1만큼 열과 행을 더할때 0번째 행과 열을 참고하게 해서 OutOfBound가 일어나지 않도록 했다.


```java
public int solution2(int m, int n, int[][] puddles) {
    lastRow = n;
    lastCol = m;
    boolean[][] map = new boolean[lastRow+1][lastCol+1];
    for(int[] puddle : puddles) {
        map[puddle[1]][puddle[0]] = true;
    }

    int[][] arr = new int[lastRow+1][lastCol+1];
    arr[1][1] = 1;

    for(int r=1; r<=lastRow; r++) {
        for(int c=1; c<=lastCol; c++) {
            if(map[r][c]) continue;

            if(!map[r-1][c]) {
                arr[r][c] += arr[r-1][c] % LIMIT;
            }
            if(!map[r][c-1]) {
                arr[r][c] += arr[r][c-1] % LIMIT;
            }
        }
    }
    return arr[lastRow][lastCol] % LIMIT;
}

```



풀고 나서 다른 사람 코드를 봤더니 더 깔끔하게 푼 코드가 보였다. 그래서 참고해서 다시 만들었다. 웅덩이를 위해 굳이 배열을 만들 필요없이 int배열에서 해당 지점은 -1로 해놓았다. 순회할 때 -1값일 경우에는 0으로 처리하고 넘어가면 된다.

또한 1,1에 1을 넣었던 이전 방식과 다르게 1,0에 값 1을 넣어서 1,1부터 순회할때 1,1 또한 1이 될수 있게 만들었다. 이렇게 안하면 위, 앞, 그리고 자신에 대한 값을 모두 더해야 하는데 자기자신이 0인 배열에서 이렇게 하는건 비효율적이라 생각했기 때문이다. 

```java
public int solution(int m, int n, int[][] puddles) {
    int[][] arr = new int[n+1][m+1];
    arr[1][0] = 1;

    for(int[] puddle : puddles)
        arr[puddle[1]][puddle[0]] = -1;

    for(int row=1; row<=n; row++) {
        for(int col=1; col<=m; col++) {
            if(arr[row][col] == -1) arr[row][col] = 0;
            else {
                arr[row][col] = (arr[row-1][col] + arr[row][col-1]) % LIMIT;
            }
        }
    }
    return arr[n][m] % LIMIT;
}
```

