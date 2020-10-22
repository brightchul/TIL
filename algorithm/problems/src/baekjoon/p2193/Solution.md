# 백준 온라인 저지 : 이친수

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2193)

0과 1로만 이루어진 수를 이진수라 한다. 이러한 이진수 중 특별한 성질을 갖는 것들이 있는데, 이들을 이친수(pinary number)라 한다. 이친수는 다음의 성질을 만족한다.

1. 이친수는 0으로 시작하지 않는다.
2. 이친수에서는 1이 두 번 연속으로 나타나지 않는다. 즉, 11을 부분 문자열로 갖지 않는다.

예를 들면 1, 10, 100, 101, 1000, 1001 등이 이친수가 된다. 하지만 0010101이나 101101은 각각 1, 2번 규칙에 위배되므로 이친수가 아니다.

N(1 ≤ N ≤ 90)이 주어졌을 때, N자리 이친수의 개수를 구하는 프로그램을 작성하시오.




```
[입력]
첫째 줄에 N이 주어진다.

[출력]
첫째 줄에 N자리 이친수의 개수를 출력한다.

[예시]
3

return
2
```





## 문제풀이

DP문제이다. 

처음에는 Combination으로 풀수 있지 않을까 하는 생각을 했다. 하지만 그렇게 하면 총 경우의 수에서 Combination을 제외해야 하는데 총 경우의 수가 2의 (n-2)승이다. 문제에서 90이 최대수이니 2의 88승이다. JAVA의 long 타입으로 해도 커버가 안된다. 따라서 이렇게 접근하지 않고 DP로 풀기 시작했다.

```
n=2
10

n=3
100
101

n=4
1000
1001
1010

n=5
10000
10001
10010
10100
10101
```

이렇게 보면 solution(n) = solution(n-1) + solution(n-2) 라는 것을 알 수 있다. 따라서 이것 그대로 식을 만들면 된다.



## 코드 구현 [[전체코드]](./Main.java)

```java
public static void main(String[] args) throws IOException {
    int num = Integer.parseInt(br.readLine());
    cache[1] = cache[2] = 1;
    cache[3] = 2;

    System.out.println(solution(num));
}

// 값이 크므로 long으로 해준다.
public static long solution(int num) {
    if (cache[num] == 0) 
        cache[num] = solution(num - 1) + solution(num - 2);
    return cache[num];
}
```

