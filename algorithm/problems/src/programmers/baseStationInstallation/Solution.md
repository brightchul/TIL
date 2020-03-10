# 프로그래머스 : 기지국 설치

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/12979)

우리는 기지국을 **최소로 설치**하면서 모든 아파트에 전파를 전달하려고 합니다. 아파트의 개수 N, 현재 기지국이 설치된 아파트의 번호가 담긴 1차원 배열 stations, 전파의 도달 거리 W가 매개변수로 주어질 때, 모든 아파트에 전파를 전달하기 위해 증설해야 할 기지국 개수의 최솟값을 리턴하는 solution 함수를 완성해주세요

```
[제한 사항]
- N: 200,000,000 이하의 자연수
- stations의 크기: 10,000 이하의 자연수
- stations는 오름차순으로, 배열에 담긴 수는 N보다 같거나 작은 자연수입니다.
- W: 10,000 이하의 자연수

[예]
N=11, stations=[4,11], w=1, return 3
N=16, stations=[9], w=2, return 3
```



## 문제 풀이

처음에는 배열을 만들고 거기에 신호가 미치는 범위를 1로 채운뒤 카운트 하는 방법으로 접근했다. 하지만 N의 제한사항이 10의 8승이다. 또한 위의 접근방식으로 할 경우 개별 stations마다 w x 2 만큼을 순회하므로 마찬가지로 200,000,000 정도를 예상할 수 있다. 이것은 O(N)으로 하더라도 시간초과가 될수 있는데 역시나 코드로 구현한 결과 시간 초과가 나왔다.

그다음에는 시작 위치와 끝위치를 먼저 구한다음 그 구간에 필요한 기지국 수를 더하는 것으로 변경하였다. 시작위치는 1부터 시작하고, 끝위치는 stations의 개별 좌표에서 w를 빼서 구하였다. 그리고 필요한 기지국 수는 거기에 구간 길이인 2  x w + 1를 나눈다음 올림을 해서 구하였다. 나눈 몫에 소수점이 발생하면 1칸이라도 킨 영역이 발생한다는 의미이기 때문에 기지국을 1개 더 세워야 하기 때문이다.



## 코드 구현 [[전체코드](./Solution.java)]

- Math.ceil은 double을 받기 때문에 INTERVAL을 double로 해서 자동 형변환을 했다.
- start, end가 둘다 1일 때 사실상 -w / (2 * w + 1) 이기 때문에 이것을 올림하면 0이 나온다.
- start가 마지막 기지국 너머의 지점이면서 n을 초과하지 않는 경우를 따로 처리해줘야 한다. stations 순회는 맨 마지막 기지국까지만 순회하기 때문에 그 마지막 기지국 이후의 영역은 알 수 없기 때문이다.

```java
public int solution(int n, int[] stations, int w) {
    int start = 1, end;
    final double INTERVAL = w+w+1;
    double result = 0;

    // stations 순회
    for(int station : stations) {
        end = station - w;
        result += Math.ceil((end - start) / INTERVAL);
        start = station + w + 1;
    }
    
    // 마지막 station 이후 영역을 체크
    if(start <= n)
        result += Math.ceil((n+1 - start) / INTERVAL);

    return (int)result;
}
```

