# 백준 온라인 저지 : 가장 긴 감소하는 부분 수열

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/11722)

수열 A가 주어졌을 때, 가장 긴 감소하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 30, 10, 20, 20, 10} 인 경우에 가장 긴 감소하는 부분 수열은 A = {10, **30**, 10, **20**, 20, **10**} 이고, 길이는 3이다.




```
[입력]
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.
둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

[출력]
첫째 줄에 수열 A의 가장 긴 감소하는 부분 수열의 길이를 출력한다.

[예시]
6
10 30 10 20 20 10

return
3
```

​       

## 문제풀이

DP 문제이다. 수열의 크기가 1,000개 이므로 O(n^2) 정도로 접근해도 된다.

최대값이 1000이므로 캐시배열을 만든다음에 각각의 값에 대해서 순회하면서 현재의 값과 순회하는 이전값을 비교한다. 

그 다음에 이전값이 크다면 그 이전값의 캐시값 +1로 현재 값의 캐시값을 업데이트 하고 그 와중에 가장 큰 값을 업데이트 했다가 마지막에 반환하면 된다.

```
10                 cache[10] = 1  (10)

10 30              cache[30] = 1  (30)

10 30 10           cache[10] = 2  (30 10) ::: cache[30] + 1 -> 2

10 30 10 20        cache[20] = 2  (30 20) ::: cache[30] + 1 -> 2

...

10 30 10 20 20 10  cache[10] = 3  (30 20 10) ::: cache[20] + 1 -> 3
```

​      

## 코드 구현 [[전체코드]](./Main.java)

```java
// solution
int max = -1;
for (int i = 0; i < len; i++) {
    int current = inputArr[i], count = 1;

    for (int j = i; j >= 0; j--) {
        int pre = inputArr[j];
        if (pre > current) {
            count = Math.max(count, cache[pre] + 1);
        }
    }
    max = Math.max(max, (cache[current] = count));
}
```


