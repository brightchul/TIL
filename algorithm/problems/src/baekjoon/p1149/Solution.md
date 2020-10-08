# 백준 온라인 저지 : RGB거리

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1149)

RGB거리에는 집이 N개 있다. 거리는 선분으로 나타낼 수 있고, 1번 집부터 N번 집이 순서대로 있다.

집은 빨강, 초록, 파랑 중 하나의 색으로 칠해야 한다. 각각의 집을 빨강, 초록, 파랑으로 칠하는 비용이 주어졌을 때, 아래 규칙을 만족하면서 모든 집을 칠하는 비용의 최솟값을 구해보자.

- 1번 집의 색은 2번 집의 색과 같지 않아야 한다.
- N번 집의 색은 N-1번 집의 색과 같지 않아야 한다.
- i(2 ≤ i ≤ N-1)번 집의 색은 i-1번, i+1번 집의 색과 같지 않아야 한다.

```
[입력]
첫째 줄에 집의 수 N(2 ≤ N ≤ 1,000)이 주어진다. 
둘째 줄부터 N개의 줄에는 각 집을 빨강, 초록, 파랑으로 칠하는 비용이 1번 집부터 
한 줄에 하나씩 주어진다. 집을 칠하는 비용은 1,000보다 작거나 같은 자연수이다.

[출력]
첫째 줄에 연산을 하는 횟수의 최솟값을 출력한다.

[예시]
3
26 40 83
49 60 57
13 89 99

return 96
```





## 문제풀이

DP 문제이다. 먼저 간단하게 써보자 3집이 있다고 가정하고 각 집의 빨강, 초록, 파랑을 번호로 매겨본다.

```
1 2 3
4 5 6
7 8 9
```
이제 1집부터 각 경우를 간단히 써본다.
```
1 5 7
1 5 9
1 6 7
1 6 8

2 4 8
2 4 9
2 6 7
2 6 8

3 4 8
3 4 9
3 5 7
3 5 9
```

패턴을 간단히 찾아본다.

```
1 5 7
3 5 7
1 5 9
3 5 9

1 6 7
2 6 7
1 6 8
2 6 8

2 4 8
3 4 8
3 4 9
2 4 9
```

즉 다음의 값들에 대해서 다음과 같은 매핑이 되는 것을 볼 수 있다. 
```
4 -> [8,9]
5 -> [7,9]
6 -> [7,8]
```

위의 매핑되는 값들에 대해서 두 값을 비교한 다음에 최소 값들만 캐시를 하면 된다. 그 이전 집들 입장에서는 자신의 다음 집 페인트 색만 고려하면 되고, 다다음 집 페인트 색은 고려할 필요가 없다. 





## 코드 구현 [[전체코드]](./Main.java)

배열에서 이전 집의 색, 색을 칠하는 비용을 캐시하고 이후 집에서 이전 집의 비용에 자신의 색상 비용을 더하는 식으로 계속 배열을 채워넣은 다음 마지막에 최소값을 반환하면 된다.

```java

public static int solution() throws IOException {
    int len = Integer.parseInt(br.readLine());

    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    if (len == 1) {
        return getMin(prsInt(st), prsInt(st), prsInt(st));
    }

    int[][] arr = new int[len][3];
    arr[0][0] = prsInt(st);
    arr[0][1] = prsInt(st);
    arr[0][2] = prsInt(st);

    int idx = 0;
    while (++idx < len) {
        st = new StringTokenizer(br.readLine(), " ");

        arr[idx][0] = prsInt(st) + getMin(arr[idx - 1][1], arr[idx - 1][2]);
        arr[idx][1] = prsInt(st) + getMin(arr[idx - 1][0], arr[idx - 1][2]);
        arr[idx][2] = prsInt(st) + getMin(arr[idx - 1][0], arr[idx - 1][1]);
    }
    return getMin(arr[idx - 1]);
}

public static int prsInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
}

public static int getMin(int... arr) {
    int result = Integer.MAX_VALUE;
    for (int one : arr) {
        result = Math.min(result, one);
    }
    return result;
}
```



