# 4장 다이나믹 프로그래밍 적용 전략

## 4.1 세 방법을 차례대로 적용하며 문제 풀기

### 예제 : 행렬에서 최소 이동 비용 구하기

M x N  크기 2차원의 비용 행렬 cost의 각 셀(cost\[i\]\[j\]) 은 해당 셀을 통과하는 데 드는 비용을 나타낸다. 어떤 셀에서 출발해 다른 셀에 도달하는데 드는 총 비용은 경로상의 출발 셀과 도착 셀을 포함한 모든 셀 비용의 합이다.

비용 행렬이 주어졌을 때 행렬의 가장 좌상단 셀에서 가장 우하단 셀로 이동하는데 드는 최소 비용을 반환하는 함수를 작성해본다. 단, 아래쪽과 오른쪽으로 한셀씩만 이동할 수 있다. 즉 셀 (i, j)에서는 셀 (i, j+1) 또는 셀 (i+1, j) 로만 이동할 수 있다.

```
[1, 3, 5, 8]
[4, 2, 1, 7]
[4, 3, 2, 3]

최소 비용 경로
1->3
 ->2->1
    ->2->3
```

​          

#### 재귀 호출

재귀 호출을 사용해 문제를 풀고자 하면 큰 문제를 작은 하위 문제를 사용해 정의한 후, 하위 문제의 해결은 재귀 호출에 맡기면 된다. 이 재귀 호출은 가장 큰 문제 즉 최종 목적지에 도달하는 데서 시작하여 종료 조건에 도달할 때까지 이루어진다.

- 큰 문제 : 셀 (2,3)에 도달하는 최소 이동 비용을 구하기
- 작은 문제 1 : 셀 (2,2)에 도달하는 최소 이동 비용을 구하기
- 작은 문제 2 : 셀 (1,3)에 도달하는 최소 이동 비용을 구하기

작은 문제들은 큰 문제와 완전히 동일하다. 큰문제와 작은 문제들을 같은 함수로 풀수 있으니 재귀 호출을 사용한다.

x와 y가 각각 셀 (2,2) 와 셀(1,3)까지의 최소 이동 비용이라면 셀 (2,3) 까지의 최소 이동 비용은 다음과 같다.

```
MIN(x, y) + cost[2][3]
```

####       

#### 종료조건

1. **m과 n 모두 0인 경우** : 출발지 == 목적지. 셀 (0, 0)으로 가는 방법은 단 한가지 뿐이며 cost\[0\]\[0\]을 반환한다.(종료조건O)
2. **m은 0이고 n은 0이 아닌 경우** : 이 경우는 목적지가 제일 위 행에 있지만 셀 \(0,0\) 은 아닌 경우이다. 이 목적지로는 오른쪽으로만 이동해야 도달할 수 잇다. 그러므로 바로 왼쪽 셀의 최소 이동 비용에 현재 셀의 비용을 더해서 반환한다. (종료조건X)
3. **m은 0은 아니고 n은 0인 경우** : 이 경우는 목적지가 제일 왼쪽 열에 있지만 셀 \(0,0\) 은 아닌 경우이다. 이 목적지로는 아래로만 이동해야 도달할 수 있다. 그러므로 바로 위 셀의 최소 이동 비용에 현재 셀의 비용을 더해서 반환한다. (종료조건X)

​        

#### 코드 구현

```typescript
const cost = [[1, 3, 5, 8],[4, 2, 1, 7],[4, 3, 2, 3]];

// 행렬의 왼쪽 위가 (0,0), 오른쪽 아래가 (m,n) 셀로 0부터 시작하므로
// MxN 크기의 행렬이 주어졌을 때 m = M-1,n = N-1 이 된다.
// cost 행렬을 배열로 선언하거나 함수를 호출할 때 주의한다.
function minPathCost(cost: number[][], m: number, n: number) : number {
  if(m ==0 && n == 0) return cost[0][0];
 	if(m === 0)
    return minPathCost(cost, 0, n-1) + cost[0][n];
  if(n === 0)
    return minPathCost(cost, m-1, 0) + cost[m][0];
  
  const x: number = minPathCost(cost, m-1, n);
  const y: number = minPathCost(cost, m, n-1);
  
  return Math.min(x, y) + cost[m][n];
}


// 살짝 바꿔보자
function myMinPathCost(cost: number[][], m: number, n:number) : number {
  if(m < 0 || n < 0) return Number.POSITIVE_INFINITY;
  if(m == 0 && n == 0) return cost[0][0];

  const x: number = myMinPathCost(cost, m-1, n);
  const y: number = myMinPathCost(cost, m, n-1);
  
  return Math.min(x, y) + cost[m][n];
}
```



이 코드의 시간 복잡도는 O(2^n) 이다. 또한 재귀 호출을 사용하므로 메모리도 추가적으로 많이 사용한다. 아래와 같이 반복되는 하위 계산들이 많다는 것을 알수 있다.

```
minPathCost(arr, 2, 3) 	 
minPathCost(arr, 1, 3) 	 
minPathCost(arr, 0, 3) 	 
minPathCost(arr, 0, 2) 	 
minPathCost(arr, 0, 1) 	 
minPathCost(arr, 0, 0) 	 
minPathCost(arr, 1, 2) 	 
minPathCost(arr, 0, 2) 2 
minPathCost(arr, 0, 1) 2 
minPathCost(arr, 0, 0) 2 
minPathCost(arr, 1, 1) 	 
minPathCost(arr, 0, 1) 3 
minPathCost(arr, 0, 0) 3 
minPathCost(arr, 1, 0) 	 
minPathCost(arr, 0, 0) 4 
minPathCost(arr, 2, 2) 	 
minPathCost(arr, 1, 2) 2 
minPathCost(arr, 0, 2) 3 
minPathCost(arr, 0, 1) 4 
minPathCost(arr, 0, 0) 5 
minPathCost(arr, 1, 1) 2 
minPathCost(arr, 0, 1) 5 
minPathCost(arr, 0, 0) 6 
minPathCost(arr, 1, 0) 2 
minPathCost(arr, 0, 0) 7 
minPathCost(arr, 2, 1) 	 
minPathCost(arr, 1, 1) 3 
minPathCost(arr, 0, 1) 6 
minPathCost(arr, 0, 0) 8 
minPathCost(arr, 1, 0) 3 
minPathCost(arr, 0, 0) 9 
minPathCost(arr, 2, 0) 	 
minPathCost(arr, 1, 0) 4 
minPathCost(arr, 0, 0) 10 
```

​       

#### 메모 전략

메모 전략을 사용해서 재귀 코드를 개선해 보자. 각 셀 \(i, j\) 에서 minPathCost를 처음 계산할 때 이것을 캐시로 저장한 뒤 그 후에는 바로 캐시에서 가져오는 방법이다. 각 셀의 최소 이동 비용을 저장해야 하므로 2차원 배열을 사용한다. 이 코드에는 여전히 재귀 호출이 있다. 하지만 한번 계산한 하위 문제는 다시 계산하지 않는다.

이 코드의 시간 복잡도는 O(n^2) 이다. 

```typescript
//결과를 저장할 전역 배열
const cache:number[][] = Array(3).fill(0).map(_=>[]);

// 캐시 배열
const cost = [[1, 3, 5, 8],[4, 2, 1, 7],[4, 3, 2, 3]];

function minPathCost(cost: number[][], m: number, n: number) : number {

  // 미리 계산되어 있다면 계산하지 않는다.
  if(cache[m][n] !== undefined && cache[m][n] != 0) return cache[m][n];
  
  // 계산된 결과는 cache[m][n]에 저장한 다음,이 값을 반환한다.
  if(m === 0 && n === 0) 
    cache[m][n] = cost[m][n];
  else if(m === 0)
    cache[m][n] = minPathCost(cost, m, n-1) + cost[m][n];
  else if(n === 0)
    cache[m][n] = minPathCost(cost, m-1, n) + cost[m][n];
  else {
    const x = minPathCost(cost, m-1, n);
    const y = minPathCost(cost, m, n-1);
    cache[m][n] = Math.min(x,y) + cost[m][n];
  }
  return cache[m][n];
}


// 살짝 바꿔보자
function myMinPathCost(cost: number[][], m: number, n: number) : number {
  if(m < 0 || n < 0) return Number.POSITIVE_INFINITY;
	if(cache[m][n] !== undefined && cache[m][n] != 0) return cache[m][n]; 
  
  cache[m][n] = cost[m][n];
  
  if(m !== 0 || n !== 0) 
    cache[m][n] += Math.min(myMinPathCost(cost, m-1, n), myMinPathCost(cost, m, n-1));
  
	return cache[m][n];
}
```

​       

#### 상향식 다이나믹 프로그래밍

이 문제의 최적 풀이법은 다이나믹 프로그래밍으로 출발 셀에서 목적셀 까지 상향 이동하면서 경로상의 모든 셀의 비용을 계산하는 방식이다.

```typescript
// 재귀 호출이나 메모 전략을 사용할 떄와 마찬가지로 셀 0,0 에서 셀 2,3 까지의 최소 이동 비용을 구할 때의
// M과 N의 값은 3(m + 1) 과 4(n + 1) 이다. 

const M = 3, N = 4;
const cache:number[][] = Array(M).fill(0).map(_=>[]);

function minPathCost(cost: number[][]) {
	cache[0][0] = cost[0][0];
  
  // 제일 위 행
  for(let j=1; j<N; j++)
    cache[0][j] = cache[0][j-1] + cost[0][j];
  
  // 제일 왼쪽 열
  for(let i=1; i<m; i++)
    cache[i][0] = cache[i-1][0] + cost[i][0];
  
  // 나머지 셀을 채워나간다
  for(let i=1; i<M; i++)
    for(let j=1; j<N; j++)
      cache[i][j] = Math.min(cache[i-1][j], cache[i][j-1]) + cost[i][j];
  
  return cache[M-1][N-1];
}

// 살짝 바꿔보자

function myMinPathCost(cost: number[][]) {
  const M = cost.length, N = cost[0].length;
  const myCache:number[][] = Array(M+1).fill(0).map(_=>Array(N+1).fill(Infinity));
  myCache[0][1] = myCache[1][0] = 0; // 둘다 Infinity라서 이부분만 보정을 해준다.
  
  for(let i=1; i<=M; i++) {
    for(let j=1; j<=N; j++) {
      myCache[i][j] = Math.min(myCache[i-1][j], myCache[i][j-1]) + cost[i-1][j-1];
    }
  }
	return myCache[M][N];
}

```



#### 연습문제

```typescript
// 오른쪽, 아래쪽, 우하향 대각선 방향까지 3방향으로 이동할수 있다면 풀이법을 어떻게 수정해야 할까?
// 살짝 바꿔보자
function myMinPathCost(cost: number[][]) {
  const M = cost.length, N = cost[0].length;
  const myCache:number[][] = Array(M+1).fill(0).map(_=>Array(N+1).fill(Infinity));
  myCache[0][0] = myCache[0][1] = myCache[1][0] = 0; // 이부분만 보정해 준다.
  
  for(let i=1; i<=M; i++) {
    for(let j=1; j<=N; j++) {
      myCache[i][j] = Math.min(myCache[i-1][j], myCache[i][j-1],  myCache[i-1][j-1]) + cost[i-1][j-1];
    }
  }
	return myCache[M][N];
}


```

​              



## 4.2 다이나믹 프로그래밍을 사용한 문제 해결

### 다이나믹 프로그래밍을 적용할 수 있을까요?

> 다이나믹 프로그래밍은 **하위 문제의 계산이 반복**되며 **최적의 하위 구조**가 있는 문제들을 해결하는데 가장 적합하다.

#### 체크리스트

1. 문제를 같은 형태의 하위 문제로 나눌 수 있습니까?
2. 하위 문제의 계산으로 반복되나요?
3. 최적화, 최대화 또는 최소화나 어떤 작업의 경우의 수를 구하는 유형의 문제입니까? (보너스)





### 다이나믹 프로그래밍으로 문제 풀기

1. **다이나믹 프로그래밍을 적용할 수 있는 경우인지 확인** : 위의 3가지 체크리스트를 적용
2. **점화식 또는 재귀 과정을 정의** : 같은 종류의 하위 문제가 있다면 재귀호출을 사용가능
   1. **문제를 하위 문제를 사용해 하향식으로 정의** : 이 시점에 시간 복잡도 고민하지 않는다.
   2. **맨 아래에 해당하는 '기본 경우'에 대한 답을 정의** : 나머지는 재귀호출에 맡긴다.
   3. **종료 조건 추가** : 대부분 기본 경우가 종료 조건에 해당된다.
3. **(선택적) 메모 전략을 시도** : 같은 하위 문제를 반복해서 계산하는 경우라면 하위 문제의 해답을 캐시에 저장한 후 같은 문제를 풀어야 할 때 캐시에 저장된 값을 사용한다.
4. **상향식으로 문제 풀이에 도전** : 재귀 호출을 제거하고 기본 경우에서 출발하는 진행 방향으로 풀이법을 재정의 한다. 문제를 풀어나가는 동안 전체 문제를 푸는데 필요한 결과들만 캐시에 저장





### 예제 :타일로 공터 채우기

2 x n 크리의 공터가 있다. 이 공터 전체를 타일로 덮고자 한다. 각 타일의 크기는 2 x 1 인데, 이 타일은 가로 혹은 세로로 배치할 수 있다.

#### 풀이법

1. n=1이면 타일을 놓는 방법은 타일 한 개를 세로로 배치하는 한 가지 밖에 없다
2. n=2이면 타일을 놓는 방법은 타일 2개를 세로로 배치하거나 타일 2개를 가로로 배치하는 2가지 밖에 없다.



### 예제 : 특정 점수에 도달하는 경우의 수 구하기



### 예제 : 연속된 부분 배열의 최댓값 구하기



